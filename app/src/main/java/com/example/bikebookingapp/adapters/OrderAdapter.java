package com.example.bikebookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bikebookingapp.R;
import com.example.bikebookingapp.models.OrderModel;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderModel> list = new ArrayList<>();

    public OrderAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    public OrderAdapter() {
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.history_item, parent, false);
            OrderModel orderModel = list.get(position);
            TextView txtEmail = convertView.findViewById(R.id.txtEmail);
            TextView txtTime = convertView.findViewById(R.id.txtTime);
            txtEmail.setText(orderModel.getEmail());
            txtTime.setText(orderModel.getStartTime() + "-" + orderModel.getEndTime());
        }
        return convertView;

    }
}
