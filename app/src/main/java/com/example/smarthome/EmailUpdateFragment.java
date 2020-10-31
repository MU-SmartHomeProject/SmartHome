package com.example.smarthome;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmailUpdateFragment extends Fragment implements View.OnClickListener {

    private int loggedIn;
    private EditText emailField;
    private UserDBHelper db;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_email, container, false);

        //User MainActivity getLoggedIn method to set loggedIn
        loggedIn = ((MainActivity)getActivity()).getLoggedIn();

        //Initialise variables
        ((Button) v.findViewById(R.id.updateBtn1)).setOnClickListener(this);

        emailField = (EditText) v.findViewById(R.id.user_email_txt);

        db = new UserDBHelper(getContext());
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set username text
        emailField.setText(cursor.getString(1));

        return v;
    }

    @Override
    public void onClick(View view) {
        cursor.moveToFirst();

        Cursor check = db.checkEmail(emailField.getText().toString());
        int icheck = check.getCount(); //Get count of number of emails matching users input for email

        //If entered username not same as current username
        //And number of usernames same as entered username are greater than 0
        if(!emailField.getText().toString().equals(cursor.getString(1)) && icheck > 0) {
            Toast.makeText(getContext(), "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            db.updateData(Integer.parseInt(cursor.getString(0)), cursor.getString(2), emailField.getText().toString(), cursor.getString(3));

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
    }
}
