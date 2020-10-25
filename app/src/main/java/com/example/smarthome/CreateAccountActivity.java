package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    public void createAccount(View v) {
        Cursor cursor = db.checkUsername(username.getText().toString());
        cursor.moveToFirst();

        if(cursor.getCount() == 0) {
            cursor = db.checkEmail(email.getText().toString());
            cursor.moveToFirst();

            if(cursor.getCount() == 0){
                if(password.getText().toString().isEmpty()) {
                    Toast.makeText(this,"Password Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.insertData(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    Toast.makeText(this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }
}