package com.example.smarthome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String currentUser = "";
    private int loggedIn;
    private UserDBHelper db;
    private TextView navUsername, navEmail;
    private DrawerLayout drawer;

    /**
     * oncreate method
     *
     * @param savedInstanceState
     */
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

        //Initialise database
        db = new UserDBHelper(this);
        //Get the logged in user via intent passed from log in activity
        loggedIn = getIntent().getIntExtra("loggedInUser", 0);

        Cursor cursor = db.getUser(loggedIn); //Get logged in user from database
        cursor.moveToFirst();

        navUsername = navigationView.getHeaderView(0).findViewById(R.id.navUsername);
        currentUser = cursor.getString(2); //get logged in users username from database
        navUsername.setText(currentUser); //Set username in nav bar

        navEmail = navigationView.getHeaderView(0).findViewById(R.id.navEmail); //get logged in users email from database
        navEmail.setText(cursor.getString(1)); //Set email in nav bar

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }
    }

    /**
     * handle nav item select event
     *
     * @param menuItem
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeatherFragment()).commit();
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
                Toast.makeText(this, "Share function is comming soon.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog d = null;
                builder.setMessage(R.string.nav_log_out)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Display log out message
                                Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                                //Create a log in page activity variable
                                LogInPageActivity lg = new LogInPageActivity();
                                lg.loggedOut(); //call the LogInPageActivity log out method

                                //Start a new LogInPageActivity
                                Intent intent = new Intent(getApplicationContext(), LogInPageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                d = builder.create();
                d.setTitle("Are you sure");
                d.show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * handle back press event
     * Specifying a listener allows you to take an action before dismissing the dialog.
     * The dialog is automatically dismissed when a dialog button is clicked.
     * A null listener allows the button to dismiss the dialog and take no further action.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit from smart home?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
        }
    }

    /**
     * @return loggedIn
     */
    public int getLoggedIn() {
        return loggedIn;
    }
}