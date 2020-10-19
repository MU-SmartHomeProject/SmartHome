package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInPageActivity extends AppCompatActivity {

    private Button login;
    private int loggedIn;
    private UserDBHelper db;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        login = (Button)findViewById(R.id.LogInBtn);
        username = (EditText)findViewById(R.id.UsernameLogIn);
        password = (EditText)findViewById(R.id.PasswordLogIn);
        loggedIn = -1;
        db = new UserDBHelper(this);
    }

    public void runProfilePage() {
            Intent intent = new Intent(this, ProfilePageActivity.class);
            intent.putExtra("loggedInUser", loggedIn);
            startActivity(intent);
    }

    public void logIn(View v) {
        Cursor cursor = db.checkUsername(username.getText().toString());
        cursor.moveToFirst();

        //If username found in database
        if(cursor.getCount() > 0) {
            Cursor cursor1 = db.getUser(Integer.parseInt(cursor.getString(0))); //get all user info from username table position
            cursor1.moveToFirst();

            //if password matches stored password
            if(cursor1.getString(3).equals(password.getText().toString())) {
                Toast.makeText(this, "Successful Log In", Toast.LENGTH_SHORT).show();
                loggedIn = Integer.parseInt(cursor.getString(0));
                runProfilePage();
            }
            else { //password doesn't match
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
        }
        else { //username not found
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
        }
    }
}