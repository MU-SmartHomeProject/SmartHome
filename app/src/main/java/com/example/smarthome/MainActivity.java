package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = (Button)findViewById(R.id.profile_page_btn);
    }

    //profileBtn has android:onclick for this function
    public void runProfilePage(View v) {
        Intent intent = new Intent(this, ProfilePageActivity.class);
        startActivity(intent);
    }
}