package com.example.a101d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FoodItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

       int ID = getIntent().getIntExtra("FoodID", 99999999);

        TextView IDView = findViewById(R.id.textView2);
        IDView.setText(String.valueOf(ID));
    }
}