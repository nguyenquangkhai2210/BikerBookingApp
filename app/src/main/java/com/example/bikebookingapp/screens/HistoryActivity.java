package com.example.bikebookingapp.screens;

import com.example.bikebookingapp.R;
import com.example.bikebookingapp.adapters.OrderAdapter;
import com.example.bikebookingapp.models.OrderModel;
import com.example.bikebookingapp.services.OrderService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

public class HistoryActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<OrderModel> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.historylist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderModel order = orders.get(position);
            }
        });
        loadOrder();
    }

    private void loadOrder() {
        OrderService orderService = new OrderService(this);
        try {
            orders = orderService.getAll();
            if (orders.size() == 0) {
                orderService.insert(new OrderModel(UUID.randomUUID().toString(), "AnhPNCMSE63138@fpt.edu.vn", "10:00", "11:00"));
                orderService.insert(new OrderModel(UUID.randomUUID().toString(), "chanhchanh9999@gmail.com", "09:00", "09:30"));
                orderService.insert(new OrderModel(UUID.randomUUID().toString(), "minhanh9999@gmail.com", "17:00", "17:20"));
                orderService.insert(new OrderModel(UUID.randomUUID().toString(), "ngocchau19101997@gmail.com", "13:00", "14:00"));
                orderService.insert(new OrderModel(UUID.randomUUID().toString(), "chauminh0610199abc@gmail.com", "18:00", "18:10"));

                orders = orderService.getAll();


            }
            OrderAdapter orderAdapter = new OrderAdapter(this, orders);
            listView.setAdapter(orderAdapter);

        } catch (Exception e) {
            Log.d("HistoryActivity", "Error at LoadOrder: " + e.getMessage());

        }

    }
}
