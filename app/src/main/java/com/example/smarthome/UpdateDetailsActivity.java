package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UpdateDetailsActivity extends AppCompatActivity {

    private UpdateDetailsAdapter adapter;
    private ViewPager viewPager;
    private int currentFrag, loggedIn;
    private Button submit;
    private ChangeEmailFragment frag1 = new ChangeEmailFragment();
    private ChangeUsernameFragment frag2 = new ChangeUsernameFragment();
    private ChangePasswordFragment frag3 = new ChangePasswordFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        adapter = new UpdateDetailsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.update_container);
        submit = (Button)findViewById(R.id.submit) ;
        currentFrag = getIntent().getIntExtra("pos", 0);
        loggedIn = getIntent().getIntExtra("loggedIn",0);

        setup(viewPager);
        viewPager.setCurrentItem(currentFrag);
    }

    private void setup(ViewPager viewPager) {
        UpdateDetailsAdapter adapter = new UpdateDetailsAdapter(getSupportFragmentManager());
        adapter.addFragment(frag1, "changeEmailFragment");
        adapter.addFragment(frag2, "changeUsernameFragment");
        adapter.addFragment(frag3, "changePasswordFragment");

        viewPager.setAdapter(adapter);
    }

    public int getLoggedIn() {
        return loggedIn;
    }

    public void update(View v) {

        if(currentFrag == 0) {
            boolean updated = frag1.updateEmail();

            if(updated)
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
        }

        if(currentFrag == 1) {
            boolean updated = frag2.updateUsername();

            if(updated)
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
        }

        if(currentFrag == 2) {
            boolean updated = frag3.updatePassword();

            if(updated)
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
        }

    }
}