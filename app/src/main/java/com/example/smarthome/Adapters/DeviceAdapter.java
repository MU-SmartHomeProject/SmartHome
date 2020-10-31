package com.example.smarthome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Model.Device;
import com.example.smarthome.R;

import java.util.ArrayList;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    private static ClickListener clickListener;
    private ArrayList<Device> list;

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewType;
        TextView textViewStatus;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            textViewName = view.findViewById(R.id.textViewItemName);
            textViewType = view.findViewById(R.id.textViewItemType);
            textViewStatus = view.findViewById(R.id.textViewItemStatus);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        DeviceAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public DeviceAdapter(ArrayList<Device> arrayList) {
        list = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Device item = list.get(position);

        myViewHolder.textViewName.setText(item.getName());
        myViewHolder.textViewType.setText(item.getType());

        if(item.getStatus() == 1){
            myViewHolder.textViewStatus.setText("ON");
        }
        else {
            myViewHolder.textViewStatus.setText("OFF");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
