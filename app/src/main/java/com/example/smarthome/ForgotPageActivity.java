package com.example.smarthome;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPageActivity extends AppCompatActivity {

    private EditText username, email, password;
    private Button reset;
    private UserDBHelper db;

    /**
     * oncreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_page);

        //Initialise variables
        db = new UserDBHelper(this);
        username = (EditText)findViewById(R.id.UsernameLogIn);
        email = (EditText)findViewById(R.id.emailLogin);
        password = (EditText)findViewById(R.id.PasswordLogIn);
        reset = (Button)findViewById(R.id.ResetBtn);
    }

    /**
     * handle reset password event
     * @param v
     */
    public void resetPassword(View v) {
        Cursor cursor = db.checkUsername(username.getText().toString()); //check database for matching username to users input
        cursor.moveToFirst();

        //If username found in database
        if(cursor.getCount() > 0) {
            Cursor cursor1 = db.getUser(Integer.parseInt(cursor.getString(0))); //get all user info from username table position
            cursor1.moveToFirst();

            //if email matches stored email
            if(cursor1.getString(1).equals(email.getText().toString())) {
                boolean update = db.updateData(Integer.parseInt(cursor.getString(0)), cursor1.getString(2), cursor1.getString(1), password.getText().toString());

                if(update) {
                    Toast.makeText(this, "Password Reset", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, LogInPageActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this,"Password Not Reset", Toast.LENGTH_SHORT).show();
            }
            else { //password doesn't match
                Toast.makeText(this, "Incorrect Email", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
        }
    }
}