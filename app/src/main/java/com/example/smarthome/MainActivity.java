package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    private String loggedIn = "testaccount@email.com"; //Test user 1
    private Button profileBtn;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = (Button)findViewById(R.id.profile_page_btn);
        db = new UserDBHelper(this);
    }

    //profileBtn has android:onclick for this function
    public void runProfilePage(View v) {
        Intent intent = new Intent(this, ProfilePageActivity.class);
        intent.putExtra("loggedInUser", loggedIn);
        startActivity(intent);
    }
}