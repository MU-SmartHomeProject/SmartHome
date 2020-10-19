package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    private int loggedIn;
    private Button profileBtn;
    private UserDBHelper db;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        profileBtn = (Button)findViewById(R.id.profile_page_btn);
        db = new UserDBHelper(this);
        loggedIn = -1;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DeviceFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_device);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_device:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DeviceFragment()).commit();
                break;

            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LocationFragment()).commit();
                break;

            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).commit();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //profileBtn has android:onclick for this function
    public void runProfilePage(View v) {
        if(loggedIn == -1)
            Toast.makeText(this, "Not logged in!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(this, ProfilePageActivity.class);
            intent.putExtra("loggedInUser", loggedIn);
            startActivity(intent);
        }
    }

    public void logIn(View v) {
        Toast.makeText(this, "Successful Log In", Toast.LENGTH_SHORT).show();

        loggedIn = 1; //User 1
    }
}