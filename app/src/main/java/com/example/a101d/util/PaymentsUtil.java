package com.example.a101d.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

//import com.google.android.gms.samples.wallet.Constants;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//importing constants for wallet samples appears to not contain payments_enviroments

//THIS CLASS IS AN IMPLEMENTATION OF THE TUTORIAL ON GOOGLE PAY API
// THIS CAN BE FOUND HERE --> https://developers.google.com/pay/api/android/guides/tutorial#paymentsutil.java-java

public class PaymentsUtil {
    public static final BigDecimal CENTS_IN_A_UNIT = new BigDecimal(100d);


    //needed
    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }

    //needed
    //leave everything as example as we are treating this like a test enviroment
    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {{
            put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject() {{
                put("gateway", "example");
                put("gatewayMerchantId", "exampleGatewayMerchantId");
            }});
        }};
    }
    //needed
    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("INTERAC")
                .put("JCB")
                .put("MASTERCARD")
                .put("VISA");
    }
    //needed
    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    //I THINK NEEDED?
    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods());
        parameters.put("allowedCardNetworks", getAllowedCardNetworks());
        // Optionally, you can add billing address/phone number associated with a CARD payment method.
        parameters.put("billingAddressRequired", true);
        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "FULL");
        parameters.put("billingAddressParameters", billingAddressParameters);
        cardPaymentMethod.put("parameters", parameters);

        return cardPaymentMethod;
    }
    //needed
    private static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getIsReadyToPayRequest() {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));

            return Optional.of(isReadyToPayRequest);

        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    private static JSONObject getTransactionInfo() throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", 1000);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("countryCode", "AUS");
        transactionInfo.put("currencyCode", "AUD");
        transactionInfo.put("checkoutOption", "COMPLETE_IMMEDIATE_PURCHASE");

        return transactionInfo;
    }

    private static JSONObject getMerchantInfo() throws JSONException {
        return new JSONObject().put("merchantName", "Example Merchant");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getPaymentDataRequest(long priceCents) {

        final String price = PaymentsUtil.centsToString(priceCents);

        try {
            JSONObject paymentDataRequest = PaymentsUtil.getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(PaymentsUtil.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", PaymentsUtil.getTransactionInfo());
            paymentDataRequest.put("merchantInfo", PaymentsUtil.getMerchantInfo());
            return Optional.of(paymentDataRequest);

      /* An optional shipping address requirement is a top-level property of the PaymentDataRequest
      JSON object. */

        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    public static String centsToString(long cents) {
        return new BigDecimal(cents)
                .divide(CENTS_IN_A_UNIT, RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN)
                .toString();
    }
}
