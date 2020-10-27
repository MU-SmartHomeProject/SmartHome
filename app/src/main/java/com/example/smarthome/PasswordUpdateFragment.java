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

public class PasswordUpdateFragment extends Fragment implements View.OnClickListener {

    private int loggedIn;
    private EditText passwordField;
    private UserDBHelper db;
    Cursor cursor;

    public PasswordUpdateFragment(int log)
    {
        loggedIn = log;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);

        ((Button) v.findViewById(R.id.updateBtn2)).setOnClickListener(this);

        passwordField = (EditText) v.findViewById(R.id.password_txt);

        db = new UserDBHelper(getContext());
        cursor = db.getUser(loggedIn);
        cursor.moveToFirst();
        //Set username text
        passwordField.setText(cursor.getString(3));

        return v;
    }

    @Override
    public void onClick(View view) {
        cursor.moveToFirst();
        boolean updated = db.updateData(Integer.parseInt(cursor.getString(0)),cursor.getString(2), cursor.getString(1), passwordField.getText().toString());

        if(updated){
            Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment(loggedIn)).commit();
        }
        else
            Toast.makeText(getContext(), "Updated Unsuccessful", Toast.LENGTH_SHORT).show();
    }
}
