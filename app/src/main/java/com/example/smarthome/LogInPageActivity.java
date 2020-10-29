package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LogInPageActivity extends AppCompatActivity {

    private Button login;
    static int loggedIn;
    private UserDBHelper db;
    private EditText username;
    private EditText password;
    private TextView forgot;

    static SharedPreferences sp;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        login = (Button)findViewById(R.id.LogInBtn);
        username = (EditText)findViewById(R.id.UsernameLogIn);
        password = (EditText)findViewById(R.id.PasswordLogIn);
        forgot = (TextView)findViewById(R.id.ForgotPasswordLbl);

        db = new UserDBHelper(this);

        sp = getSharedPreferences("preferences", MODE_PRIVATE);
        editor = sp.edit();

        if(sp.getInt("User", 0) != 0)
            runMainPage();
    }

    public void runMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedInUser", sp.getInt("User", 0));
        startActivity(intent);
        finish();
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

                editor.putInt("User", loggedIn).commit();
                runMainPage();
            }
            else { //password doesn't match
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
        }
        else { //username not found
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgot(View v) {
        Intent intent = new Intent(this, ForgotPageActivity.class);
        startActivity(intent);
    }

    public void makeAccount(View v) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void loggedOut() {
        editor.clear().commit();
    }
}