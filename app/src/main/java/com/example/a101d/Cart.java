package com.example.a101d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a101d.RecyclerAdapters.CartRecyclerViewAdapter;
import com.example.a101d.RecyclerAdapters.RecyclerViewAdapter;
import com.example.a101d.data.FoodDatabaseHelper;
import com.example.a101d.model.Food;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements CartRecyclerViewAdapter.OnRowClickListener{
    RecyclerView recyclerView;
    CartRecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FoodDatabaseHelper db = new FoodDatabaseHelper(Cart.this);

        foodList = new ArrayList<Food>();
        foodList = db.fetchAllFoodInMyCart();

        recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerViewAdapter = new CartRecyclerViewAdapter(foodList, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        //no click actions for this recycler
    }
}