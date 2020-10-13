package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeUsernameFragment extends Fragment {

    private EditText usernameField;
    private UserDBHelper db;
    private int loggedIn;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_username, container, false);

        usernameField = (EditText) view.findViewById(R.id.username_txt_update);

        db = new UserDBHelper(getContext());
        loggedIn = ((UpdateDetailsActivity)getActivity()).getLoggedIn();
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set username text
        usernameField.setText(cursor.getString(2));

        return view;
    }

    public boolean updateUsername(){
        cursor.moveToFirst();

        Cursor check = db.checkUsername(usernameField.getText().toString());
        int icheck = check.getCount();

        //If entered username not same as current username
        //And number of usernames same as entered username are greater than 0
        if(!usernameField.getText().toString().equals(cursor.getString(2)) && icheck > 0) {
            Log.d("Error", Integer.toString(icheck));
            return false;
        }
        else {
            Log.d("Updated ", "No errors");
            db.updateData(Integer.parseInt(cursor.getString(0)), usernameField.getText().toString(), cursor.getString(1), cursor.getString(3));
            return true;
        }
    }
}
