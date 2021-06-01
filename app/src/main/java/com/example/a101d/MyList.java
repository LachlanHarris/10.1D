package com.example.a101d;

import android.content.Intent;
import android.os.Bundle;

import com.example.a101d.data.FoodDatabaseHelper;
import com.example.a101d.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MyList extends AppCompatActivity implements RecyclerViewAdapter.OnRowClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FoodDatabaseHelper db = new FoodDatabaseHelper(MyList.this);
        foodList = db.fetchAllFoodInMyList();

        recyclerView = findViewById(R.id.foodRecylcer);
        recyclerViewAdapter = new RecyclerViewAdapter(foodList, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyList.this, AddFood.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Home:
                Intent intentHome = new Intent(MyList.this, Home.class);
                finish();
                startActivity(intentHome);
                return false;
            case R.id.Account:
            case R.id.MyList:
            case R.id.Cart:
                Intent intentCart = new Intent(MyList.this, Cart.class);
                startActivity(intentCart);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        //currently no on click interaction for this recycler
    }
}