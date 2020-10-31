package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        //Initialise variables
        db = new UserDBHelper(this);
        username = (EditText)findViewById(R.id.UsernameLogIn);
        email = (EditText)findViewById(R.id.emailLogin);
        password = (EditText)findViewById(R.id.PasswordLogIn);
        create = (Button)findViewById(R.id.CreateBtn);
    }

    public void createAccount(View v) {
        //check if the username entered by the user matches a username in the database
        Cursor cursor = db.checkUsername(username.getText().toString());
        cursor.moveToFirst();

        //If username is not found in the db
        if(cursor.getCount() == 0) {
            cursor = db.checkEmail(email.getText().toString()); //Check if entered email is in the db
            cursor.moveToFirst();

            //if email not found in db
            if(cursor.getCount() == 0){
                //check if password field is empty
                if(password.getText().toString().isEmpty()) {
                    Toast.makeText(this,"Password Required", Toast.LENGTH_SHORT).show();
                }
                else { //if password field is not empty
                    //Insert the new users detail in the user database
                    db.insertData(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    Toast.makeText(this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
            else { //if email found in db
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            }

        }
        else { //If username is found in db
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }
}