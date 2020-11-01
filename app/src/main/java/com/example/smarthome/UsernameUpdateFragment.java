package com.example.smarthome;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UsernameUpdateFragment extends Fragment implements View.OnClickListener {

    private int loggedIn;
    private EditText usernameField;
    private UserDBHelper db;
    Cursor cursor;

    /**
     * on create view method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return v (view)
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_username, container, false);

        //get logged in
        loggedIn = ((MainActivity)getActivity()).getLoggedIn();

        //Initialise variables
        ((Button) v.findViewById(R.id.updateBtn)).setOnClickListener(this);

        usernameField = (EditText) v.findViewById(R.id.username_txt_update);

        db = new UserDBHelper(getContext());
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set username text
        usernameField.setText(cursor.getString(2));

        return v;
    }

    /**
     * haldle onclick event
     * If entered username not same as current username
     * And number of usernames same as entered username are greater than 0
     * Else Start profile page fragment
     * @param view
     */
    @Override
    public void onClick(View view) {
        cursor.moveToFirst();

        Cursor check = db.checkUsername(usernameField.getText().toString());
        int icheck = check.getCount(); //Check how many usernames matched users input

        if(!usernameField.getText().toString().equals(cursor.getString(2)) && icheck > 0) {
            Toast.makeText(getContext(), "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            db.updateData(Integer.parseInt(cursor.getString(0)), usernameField.getText().toString(), cursor.getString(1), cursor.getString(3));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
    }
}
