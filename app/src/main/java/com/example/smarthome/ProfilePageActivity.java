package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilePageActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private Button changeUsername;
    private Button changeEmail;
    private Button changePassword;
    private Button deleteAccount;
    private Button logOut;

    private UserDBHelper db;
    private int loggedIn;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        username = (TextView)findViewById(R.id.username_txt);
        email = (TextView)findViewById(R.id.user_email_txt);
        changeUsername = (Button)findViewById(R.id.change_username_btn);
        changeEmail = (Button)findViewById(R.id.change_email_btn);
        changePassword = (Button)findViewById(R.id.change_password_btn);
        deleteAccount = (Button)findViewById(R.id.delete_account_btn);
        logOut = (Button)findViewById(R.id.log_out_btn);

        //Get db and loggedIn from MainActivity
        db = new UserDBHelper(getApplicationContext());
        loggedIn = getIntent().getIntExtra("loggedInUser", 0);

        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set email text
        email.setText(cursor.getString(1));
        //Set username text
        username.setText(cursor.getString(2));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Refresh your stuff here
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set email text
        email.setText(cursor.getString(1));
        //Set username text
        username.setText(cursor.getString(2));
    }

    public void changeEmail(View v) {
        Intent intent = new Intent(this, UpdateDetailsActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        intent.putExtra("pos", 0);
        startActivity(intent);
    }

    public void changeUsername(View v) {
        Intent intent = new Intent(this, UpdateDetailsActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        intent.putExtra("pos", 1);
        startActivity(intent);
    }

    public void changePassword(View v) {
        Intent intent = new Intent(this, UpdateDetailsActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        intent.putExtra("pos", 2);
        startActivity(intent);
    }

    public void deleteUser(View v) {
        int deleted = db.deleteData(loggedIn);
        if(deleted > 0) {
            Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show();

            //Return to main page temporary - Logged out activity when created
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Account Not Deleted", Toast.LENGTH_SHORT).show();
    }

    public void logOut(View v) {
        Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}