package com.example.smarthome;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapters.DeviceAdapter;
import com.example.smarthome.Database.Constants;
import com.example.smarthome.Database.DatabaseHelper;
import com.example.smarthome.Model.Device;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeviceFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Device> list;
    DeviceAdapter adapter;
    ProgressBar progressBar;
    TextView textViewNoDevice;
    FloatingActionButton floatingActionButton;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            getDataFromDatabase();
        }catch (Exception e){
            Log.i("ERROR**", e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_device, container, false);

        dbHelper = new DatabaseHelper(getContext());
        progressBar = view.findViewById(R.id.progressBar);
        textViewNoDevice = view.findViewById(R.id.textViewNoDevice);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDeviceDialog();
            }
        });
        list = new ArrayList<>();
        adapter = new DeviceAdapter(list);
        adapter.setOnItemClickListener(new DeviceAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("device", list.get(position));
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        getDataFromDatabase();
        Log.i("abcde", "flow");

        return view;

    }

    void getDataFromDatabase() {
        dbHelper = new DatabaseHelper(getContext());
        progressBar.setVisibility(View.VISIBLE);
        database = dbHelper.getWritableDatabase();
        Cursor cursor;

        if (database != null) {
            cursor = database.rawQuery("SELECT * FROM " + Constants.DEVICE_TABLE, null);
            list.clear();

            if (cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_ID));
                    String deviceName = cursor.getString(cursor.getColumnIndex(Constants.DEVICE_NAME));
                    String deviceType = cursor.getString(cursor.getColumnIndex(Constants.DEVICE_TYPE));
                    int status = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_STATUS));
                    int intensity = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_INTENSITY));
                    int color = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_COLOR));

                    list.add(new Device(id, deviceName, deviceType, status, intensity, color));

                } while (cursor.moveToNext());
            }
            progressBar.setVisibility(View.GONE);
            if (list.size() == 0) {
                textViewNoDevice.setVisibility(View.VISIBLE);
            } else {
                textViewNoDevice.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();
            cursor.close();
            database.close();
        }
        Log.i("abcde", "fun"+ " "+list.size());

    }

    void getDevices(){
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(Constants.CONTENT_URI, null,null,null,null);
        list.clear();
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_ID));
                    String deviceName = cursor.getString(cursor.getColumnIndex(Constants.DEVICE_NAME));
                    String deviceType = cursor.getString(cursor.getColumnIndex(Constants.DEVICE_TYPE));
                    int status = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_STATUS));
                    int intensity = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_INTENSITY));
                    int color = cursor.getInt(cursor.getColumnIndex(Constants.DEVICE_COLOR));

                    list.add(new Device(id, deviceName, deviceType, status, intensity, color));


                } while (cursor.moveToNext());
            }
            cursor.close();
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            if (list.size() == 0) {
                textViewNoDevice.setVisibility(View.VISIBLE);
            } else {
                textViewNoDevice.setVisibility(View.GONE);
            }
        }
        else {
            progressBar.setVisibility(View.GONE);
            if (list.size() == 0) {
                textViewNoDevice.setVisibility(View.VISIBLE);
            } else {
                textViewNoDevice.setVisibility(View.GONE);
            }
        }
    }


    private void showAddDeviceDialog() {
        final AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_add_device, null);

        final EditText editTextName = dialogView.findViewById(R.id.editTextDeviceName);
        final Spinner spinnerType = dialogView.findViewById(R.id.spinnerDeviceType);
        final Switch switchStatus = dialogView.findViewById(R.id.switchDeviceStatus);
        final SeekBar seekBarIntensity = dialogView.findViewById(R.id.seekBar1);
        final SeekBar seekBarColor  = dialogView.findViewById(R.id.seekBar2);

        switchStatus.setEnabled(false);
        seekBarIntensity.setEnabled(false);
        seekBarColor.setEnabled(false);

        Button buttonAdd = dialogView.findViewById(R.id.buttonAddDevice);
        ImageView buttonCancel = dialogView.findViewById(R.id.imageViewCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean shouldProceed = true;


                if (TextUtils.isEmpty(editTextName.getText())) {
                    editTextName.setError("Device name is required!");
                    shouldProceed = false;
                }
                if (spinnerType.getSelectedItem().toString().equals("Select")) {
                    TextView textView = (TextView) spinnerType.getSelectedView();
                    textView.setError("Select device Type");
                    shouldProceed = false;
                }



                if (shouldProceed) {
                    String name = editTextName.getText().toString().trim();
                    String type = spinnerType.getSelectedItem().toString();

                    int deviceStatus = 0; // off
                    if(switchStatus.isChecked()){
                        deviceStatus = 1;   //ON
                    }

                    Device device = new Device(1, name, type, deviceStatus, seekBarIntensity.getProgress(), seekBarColor.getProgress());
                    addDeviceToDatabase(device);

                    builder.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();

            }
        });


        builder.setView(dialogView);
        builder.show();
    }

    private void addDeviceToDatabase(Device device) {

        database = dbHelper.getWritableDatabase();

        if (database != null) {

            //prepare the transaction information that will be saved to the database
            ContentValues values = new ContentValues();
            values.put(Constants.DEVICE_NAME, device.getName());
            values.put(Constants.DEVICE_TYPE, device.getType());
            values.put(Constants.DEVICE_STATUS, device.getStatus());
            values.put(Constants.DEVICE_INTENSITY, device.getIntensity());
            values.put(Constants.DEVICE_COLOR, device.getColor());

            long result = database.insert(Constants.DEVICE_TABLE, null, values);

            if (result == -1) {
                Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Device added successfully", Toast.LENGTH_SHORT).show();
                databaseUpdated();
                //onBackPressed();
            }
        }
        if (database != null) {
            database.close();
        }
    }


    private void databaseUpdated() {
        getDataFromDatabase();
    }
}
