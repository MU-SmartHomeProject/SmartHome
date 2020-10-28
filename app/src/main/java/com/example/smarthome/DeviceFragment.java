package com.example.smarthome;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapters.DeviceAdapter;
import com.example.smarthome.Database.Constants;
import com.example.smarthome.Model.Device;

import java.util.ArrayList;

public class DeviceFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Device> list;
    DeviceAdapter adapter;
    ProgressBar progressBar;
    TextView textViewNoDevice;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            getDevices();
        }catch (Exception e){
            Log.i("ERROR**", e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_device, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        textViewNoDevice = view.findViewById(R.id.textViewNoDevice);


        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
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

        getDevices();

        return view;

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
}
