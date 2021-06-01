package com.example.a101d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a101d.data.DatabaseHelper;
import com.example.a101d.model.User;

public class SignUp extends AppCompatActivity {

    DatabaseHelper db;
    EditText nameField;
    EditText emailField;
    EditText phoneField;
    EditText addressField;
    EditText passwordField;
    EditText confirmPasswordField;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper(this);
        nameField = findViewById(R.id.fullNameField);
        emailField = findViewById(R.id.emailField);
        phoneField = findViewById(R.id.phoneField);
        addressField = findViewById(R.id.addressField);
        passwordField = findViewById(R.id.signupPasswordField);
        confirmPasswordField = findViewById(R.id.signupPasswordConfirmField);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameField.getText().toString();
                String password = passwordField.getText().toString();
                //database doesn't store any other information as its not used anywhere in the app

                if (password.equals(confirmPasswordField.getText().toString()))
                {
                    long result = db.insertUser(new User(username, password));
                    if (result > 0)
                    {
                        Toast.makeText(SignUp.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(SignUp.this, LogIn.class);
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Registration error!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Two passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}