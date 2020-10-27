package com.example.smarthome;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView username;
    private TextView email;
    private TextView click;
    private UserDBHelper db;
    private int loggedIn;
    Cursor cursor;

    public ProfileFragment(int log)
    {
        loggedIn = log;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        click = (TextView) v.findViewById(R.id.pf);
        click.setOnClickListener(this);
        username = (TextView) v.findViewById(R.id.username_txt);
        email = (TextView) v.findViewById(R.id.user_email_txt);

        //Get db and loggedIn from MainActivity
        db = new UserDBHelper(getContext());

        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set email text
        email.setText(cursor.getString(1));
        //Set username text
        username.setText(cursor.getString(2));

        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new DeviceFragment()).commit();
    }
}
