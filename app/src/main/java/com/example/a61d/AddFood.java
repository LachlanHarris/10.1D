package com.example.a61d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.a61d.data.DatabaseHelper;
import com.example.a61d.data.FoodDatabaseHelper;
import com.example.a61d.model.Food;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFood extends AppCompatActivity {
    ImageButton foodImage;
    EditText titleField;
    EditText descriptionField;
    CalendarView calender;
    EditText timeField;
    EditText quantityField;
    EditText locationField;
    Button saveButton;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        FoodDatabaseHelper db = new FoodDatabaseHelper(this);

        foodImage = findViewById(R.id.newFoodImage);
        titleField = findViewById(R.id.newFoodTitleField);
        descriptionField = findViewById(R.id.newFoodDescriptionField);
        calender = findViewById(R.id.newFoodCalender);
        timeField = findViewById(R.id.newFoodTimeField);
        quantityField = findViewById(R.id.newFoodQuantityField);
        locationField = findViewById(R.id.newFoodLocationField);
        saveButton = findViewById(R.id.newFoodSaveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleField.getText().toString();
                String description = descriptionField.getText().toString();
                String time = timeField.getText().toString();
                int quantity = Integer.parseInt(quantityField.getText().toString());
                String location = locationField.getText().toString();
                String date = DateFormat.format("MM/dd/yyyy", new Date(calender.getDate())).toString();

                Food newFood = new Food(title,description,date,time,quantity,location,1);
                newFood.setImage(imageUri);
                db.insertFood(newFood);
                finish();
            }
        });

        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CameraOptionsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, fragment).commit();

            }
        });



    }
    public void SetImageButton(Uri imageUri){
        foodImage.setImageURI(imageUri);
    }
    @Override
    public void onActivityResult(int i, int i1, Intent aData) {
        super.onActivityResult(i, i1, aData);
        Uri _imageUri = aData.getData();
        SetImageButton(_imageUri);
        imageUri = _imageUri;
        getApplicationContext().grantUriPermission("com.example.a61d", _imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
}