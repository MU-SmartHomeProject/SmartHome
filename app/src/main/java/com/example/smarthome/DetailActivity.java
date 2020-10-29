package com.example.smarthome;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Database.Constants;
import com.example.smarthome.Database.DatabaseHelper;
import com.example.smarthome.Model.Device;


public class DetailActivity extends AppCompatActivity {

    TextView textViewDeviceName;
    TextView textViewDeviceType;
    TextView textViewSlider1;
    TextView textViewSlider2;
    Switch switchStatus;
    SeekBar seekBar1;
    SeekBar seekBar2;
    LinearLayout linearLayout;

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    boolean hasDataChanged = false;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;


    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        device = (Device) getIntent().getSerializableExtra("device");

        dbHelper = new DatabaseHelper(this);

        textViewDeviceName = findViewById(R.id.textViewDeviceName);
        textViewDeviceType = findViewById(R.id.textViewDeviceType);
        switchStatus = findViewById(R.id.switchDeviceStatus);
        seekBar1 = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        textViewSlider1 = findViewById(R.id.textViewSlider1);
        textViewSlider2 = findViewById(R.id.textViewSlider2);
        linearLayout = findViewById(R.id.linearLayoutSaving);
        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);


        setSlider(device);
        // Set values from db to views
        textViewDeviceName.setText(device.getName());
        textViewDeviceType.setText(device.getType());

        if (device.getStatus() == 1)
            switchStatus.setChecked(true);
        else
            switchStatus.setChecked(false);

        seekBar1.setProgress(device.getIntensity());
        seekBar2.setProgress(device.getColor());


        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasDataChanged = true;
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                hasDataChanged = true;

            }
        });

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                hasDataChanged = true;

            }
        });

    }

    private void setSlider(Device device) {

        switch (device.getType()){
            case "Air Conditioner":
                textViewSlider1.setText("Fan speed");
                textViewSlider2.setText("Temperature");
                break;

            case "Blinds":
               linearLayout1.setVisibility(View.GONE);
               linearLayout2.setVisibility(View.GONE);

                break;

            case "Fan":
                textViewSlider1.setText("speed");
                linearLayout2.setVisibility(View.GONE);

                break;

            case "Lightings":
                textViewSlider1.setText("Intensity");
                textViewSlider2.setText("Color");
                break;

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_delete) {
            deleteDevice();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteDevice() {

        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this device?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        database = dbHelper.getWritableDatabase();
                        long result = database.delete(Constants.DEVICE_TABLE, "id = " + device.getId(), null);

                        if (result == -1) {
                            Toast.makeText(DetailActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Device deleted successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        database.close();
                        onBackPressed();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    void updateValues() {
        linearLayout.setVisibility(View.VISIBLE);
        database = dbHelper.getWritableDatabase();

        int deviceStatus;
        if (switchStatus.isChecked()) {
            deviceStatus = 1;
        } else {
            deviceStatus = 0;
        }


        ContentValues values = new ContentValues();
        values.put(Constants.DEVICE_NAME, textViewDeviceName.getText().toString());
        values.put(Constants.DEVICE_TYPE, textViewDeviceType.getText().toString());
        values.put(Constants.DEVICE_STATUS, deviceStatus);
        values.put(Constants.DEVICE_INTENSITY, seekBar1.getProgress());
        values.put(Constants.DEVICE_COLOR, seekBar2.getProgress());

        String where = Constants.DEVICE_ID + " = " + device.getId();


        database.update(Constants.DEVICE_TABLE, values, where, null);
        linearLayout.setVisibility(View.GONE);
        finish();

    }

    @Override
    public void onBackPressed() {

        if (hasDataChanged) {
            updateValues();
        } else {
            super.onBackPressed();
        }

    }
}