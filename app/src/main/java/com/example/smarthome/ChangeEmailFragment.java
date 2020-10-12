package com.example.smarthome;

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

public class ChangeEmailFragment extends Fragment {

    private EditText emailField;
    private UserDBHelper db;
    private int loggedIn;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);

        emailField = (EditText) view.findViewById(R.id.user_email_txt);

        db = new UserDBHelper(getContext());
        //loggedIn = ((UpdateDetailsActivity)getActivity()).getLoggedIn();
        loggedIn = 1;
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set email text
        emailField.setText(cursor.getString(1));

        return view;
    }

    public boolean updateEmail(){
        cursor.moveToFirst();
        boolean updated = db.updateData(Integer.parseInt(cursor.getString(0)),cursor.getString(2), emailField.getText().toString(), cursor.getString(3));
        return updated;
    }

}
