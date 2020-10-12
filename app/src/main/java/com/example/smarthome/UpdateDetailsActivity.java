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
    private Button submit;
    private ChangeUsernameFragment frag1 = new ChangeUsernameFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        adapter = new UpdateDetailsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.update_container);
        submit = (Button)findViewById(R.id.submit) ;

        setup(viewPager);
        viewPager.setCurrentItem(getIntent().getIntExtra("pos", 0));
    }

    private void setup(ViewPager viewPager) {
        UpdateDetailsAdapter adapter = new UpdateDetailsAdapter(getSupportFragmentManager());
        adapter.addFragment(frag1, "changeUsernameFragment");
        adapter.addFragment(new ChangeEmailFragment(), "changeEmailFragment");
        adapter.addFragment(new ChangePasswordFragment(), "changePasswordFragment");

        viewPager.setAdapter(adapter);
    }

    public String getLoggedIn() {
        return getIntent().getStringExtra("loggedIn");
    }

    public void update(View v) {
        boolean updated = frag1.updateUsername();

        if(updated)
            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
    }
}