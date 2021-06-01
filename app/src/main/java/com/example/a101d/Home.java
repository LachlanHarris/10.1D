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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity implements RecyclerViewAdapter.OnRowClickListener{
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FoodDatabaseHelper db = new FoodDatabaseHelper(Home.this);

        Long testLongForTime = Long.valueOf(89732485);
        String testDate = new Date().toString();
        String testTime = new Time(testLongForTime).toString();

        foodList = new ArrayList<Food>();
        foodList = db.fetchAllFood();

        //these are the lines to display the demo item using a drawable int image referance
        //Food testFood1 = new Food( "Spaghetti", "some spaghetti bolognese", testDate, testTime , 3, "Melbourne" , 0 );
        //testFood1.setImage(R.drawable.spag);
        //foodList.add(testFood1);

        recyclerView = findViewById(R.id.foodRecylcer);
        recyclerViewAdapter = new RecyclerViewAdapter(foodList, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddFood.class);
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
                //we are already at home
            case R.id.Account:
            case R.id.MyList:
                Intent intentMyList = new Intent(Home.this, MyList.class);
                finish();
                startActivity(intentMyList);
                return false;
            case R.id.Cart:
                Intent intentCart = new Intent(Home.this, Cart.class);
                startActivity(intentCart);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent IntentFoodItem = new Intent(Home.this, FoodItem.class);
        int foodID = foodList.get(position).getFoodID();
        IntentFoodItem.putExtra("FoodID", foodID);
        startActivity(IntentFoodItem);
    }
}