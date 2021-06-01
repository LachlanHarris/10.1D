package com.example.a101d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a101d.data.DatabaseHelper;

public class LogIn extends AppCompatActivity {

    TextView userName;
    TextView passWord;
    Button logIn;
    Button signUp;
    EditText userNameField;
    EditText passWordField;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        db = new DatabaseHelper(this);

        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        logIn = findViewById(R.id.logIn);
        signUp = findViewById(R.id.signUp);
        userNameField = findViewById(R.id.usernameField);
        passWordField = findViewById(R.id.passwordField);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.fetchUser(userNameField.getText().toString(), passWordField.getText().toString());
                if (result == true)
                {
                    Intent homeIntent = new Intent(LogIn.this, Home.class);
                    startActivity(homeIntent);
                }
                else
                {
                    Toast.makeText(LogIn.this, "Incorrect Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LogIn.this, SignUp.class);
                startActivity(signupIntent);
            }
        });

    }
}