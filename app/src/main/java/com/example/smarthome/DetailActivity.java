package com.example.smarthome;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Database.Constants;
import com.example.smarthome.Model.Device;


public class DetailActivity extends AppCompatActivity {

    TextView textViewDeviceName;
    TextView textViewDeviceType;
    Switch switchStatus;
    SeekBar seekBarIntensity;
    SeekBar seekBarColor;
    LinearLayout linearLayout;

    boolean hasDataChanged = false;

    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        device = (Device) getIntent().getSerializableExtra("device");

        textViewDeviceName = findViewById(R.id.textViewDeviceName);
        textViewDeviceType = findViewById(R.id.textViewDeviceType);
        switchStatus = findViewById(R.id.switchDeviceStatus);
        seekBarIntensity = findViewById(R.id.seekBarDeviceIntensity);
        seekBarColor = findViewById(R.id.seekBarDeviceColor);

        linearLayout = findViewById(R.id.linearLayoutSaving);


        // Set values from db to views
        textViewDeviceName.setText(device.getName());
        textViewDeviceType.setText(device.getType());

        if(device.getStatus() == 1)
            switchStatus.setChecked(true);
        else
            switchStatus.setChecked(false);

        seekBarIntensity.setProgress(device.getIntensity());
        seekBarColor.setProgress(device.getColor());


        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasDataChanged = true;
            }
        });

        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        seekBarIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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


    void updateValues(){
        linearLayout.setVisibility(View.VISIBLE);
        ContentResolver contentResolver = getContentResolver();

        int deviceStatus;

        if(switchStatus.isChecked()){
            deviceStatus = 1;
        }else {
            deviceStatus = 0;
        }



        ContentValues values = new ContentValues();
        values.put(Constants.DEVICE_NAME, textViewDeviceName.getText().toString());
        values.put(Constants.DEVICE_TYPE, textViewDeviceType.getText().toString());
        values.put(Constants.DEVICE_STATUS, deviceStatus);
        values.put(Constants.DEVICE_INTENSITY, seekBarIntensity.getProgress());
        values.put(Constants.DEVICE_COLOR, seekBarColor.getProgress());

        String where = Constants.DEVICE_ID+" = "+device.getId();


        contentResolver.update(Constants.CONTENT_URI,values,where,null);
        linearLayout.setVisibility(View.GONE);
        finish();


    }

    @Override
    public void onBackPressed() {

        if(hasDataChanged){
            updateValues();
        }
        else {
            super.onBackPressed();
        }

    }
}