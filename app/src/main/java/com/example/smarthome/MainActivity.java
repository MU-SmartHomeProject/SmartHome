package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        Test User 1
        Email: testaccount@email.com
        Username: testUser
        Password: abc123

        Test User 2
        Email: testaccount2@email.com
        Username: testUser2
        Password: abc123
     */

    private String loggedIn;
    private Button profileBtn;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = (Button)findViewById(R.id.profile_page_btn);
        db = new UserDBHelper(this);
        loggedIn = "";
    }

    //profileBtn has android:onclick for this function
    public void runProfilePage(View v) {
        if(loggedIn.isEmpty())
            Toast.makeText(this, "Not logged in!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(this, ProfilePageActivity.class);
            intent.putExtra("loggedInUser", loggedIn);
            startActivity(intent);
        }
    }

    public void logIn(View v) {
        Toast.makeText(this, "Successful Log In", Toast.LENGTH_SHORT).show();

        loggedIn = "testaccount@email.com"; //User 1
    }
}