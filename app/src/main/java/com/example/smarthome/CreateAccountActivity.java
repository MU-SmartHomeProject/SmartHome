package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText username, email, password;
    private Button create;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = new UserDBHelper(this);
        username = (EditText)findViewById(R.id.UsernameLogIn);
        email = (EditText)findViewById(R.id.emailLogin);
        password = (EditText)findViewById(R.id.PasswordLogIn);
        create = (Button)findViewById(R.id.CreateBtn);
    }
}