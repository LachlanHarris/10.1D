package com.example.a101d;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a101d.RecyclerAdapters.CartRecyclerViewAdapter;
import com.example.a101d.RecyclerAdapters.RecyclerViewAdapter;
import com.example.a101d.data.FoodDatabaseHelper;
import com.example.a101d.model.Food;
import com.example.a101d.util.PaymentsUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart extends AppCompatActivity implements CartRecyclerViewAdapter.OnRowClickListener{
    private View googlePayButton;
    private PaymentsClient paymentsClient;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 1;



    RecyclerView recyclerView;
    CartRecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList;
    TextView cartTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        googlePayButton = findViewById(R.id.buyWithGooglePay);
        initializeUi();
        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        possiblyShowGooglePayButton();

        FoodDatabaseHelper db = new FoodDatabaseHelper(Cart.this);

        foodList = new ArrayList<Food>();
        foodList = db.fetchAllFoodInMyCart();

        recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerViewAdapter = new CartRecyclerViewAdapter(foodList, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartTotal = findViewById(R.id.cartTotalValue);
        //for the total of the cart we are going to simply times the number of items by 10 as there arent any prices for these items
        cartTotal.setText(String.valueOf(foodList.size()*10));
    }

    @Override
    public void onItemClick(int position) {
        //no click actions for this recycler
    }


    private void initializeUi() {
        googlePayButton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        requestPayment(view);

                    }
                });
    }

    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        //all food costs $10 for this basic implementation
        double foodPrice = 10 * foodList.size();
        long priceCents = Math.round(foodPrice * PaymentsUtil.CENTS_IN_A_UNIT.longValue());

        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        //handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.setClickable(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "GOOGLE PAY IS UNAVAILABLE", Toast.LENGTH_LONG).show();
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }
        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            Toast.makeText(
                    this, "Success!",
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("something whent wrong confirming the payment success");
        }
    }
}