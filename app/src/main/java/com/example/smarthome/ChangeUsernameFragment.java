package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeUsernameFragment extends Fragment {

    private EditText usernameField;
    private UserDBHelper db;
    private String loggedIn;
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
        //Set email text
        usernameField.setText(cursor.getString(1));

        return view;
    }

    public boolean updateUsername(){
        cursor.moveToFirst();
        boolean updated = db.updateData(usernameField.getText().toString(),cursor.getString(0),cursor.getString(2));
        return updated;
    }
}
