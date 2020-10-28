package com.example.smarthome;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {

    private TextView username;
    private TextView email;
    private Button chngUsername, chngeEmail, chngePassword, delAccount;
    private UserDBHelper db;
    private int loggedIn;
    Cursor cursor;

    /*public ProfileFragment(int log)
    {
        loggedIn = log;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        loggedIn = ((MainActivity)getActivity()).getLoggedIn();
        chngUsername = (Button) v.findViewById(R.id.chngUsr);
        chngUsername.setOnClickListener(handle1);

        chngeEmail = (Button) v.findViewById(R.id.chngEm);
        chngeEmail.setOnClickListener(handle2);

        chngePassword = (Button) v.findViewById(R.id.chngPass);
        chngePassword.setOnClickListener(handle3);

        delAccount = (Button) v.findViewById(R.id.delAcc);
        delAccount.setOnClickListener(handle4);

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

    View.OnClickListener handle1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new UsernameUpdateFragment()).commit();
        }
    };

    View.OnClickListener handle2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new EmailUpdateFragment()).commit();
        }
    };

    View.OnClickListener handle3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new PasswordUpdateFragment()).commit();
        }
    };

    View.OnClickListener handle4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setCancelable(false);
            alert.setTitle("Delete Account");
            alert.setMessage("Are you sure you want to delete?");

            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int deleted = db.deleteData(loggedIn);

                    if(deleted > 0) {
                        Toast.makeText(getContext(), "Account Deleted", Toast.LENGTH_SHORT).show();

                        LogInPageActivity lg = new LogInPageActivity();
                        lg.loggedOut();

                        Intent intent = new Intent(getContext(), LogInPageActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(),"Account Not Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }
    };

}
