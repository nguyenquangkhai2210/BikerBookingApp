package com.example.bikebookingapp.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bikebookingapp.helpers.OrderHelper;
import com.example.bikebookingapp.models.OrderModel;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderService implements Serializable {
    private SQLiteDatabase db;
    private OrderHelper helper;

    public OrderService(Context context) {
        helper = new OrderHelper(context, "BikeBooking", null, 1);
    }

    private void open() {
        db = helper.getWritableDatabase();
    }

    private void close() {
        if (db == null) {
            db.close();
        }
    }

    public ArrayList<OrderModel> getAll() throws Exception {
        ArrayList<OrderModel> result = new ArrayList<>();
        try {
            this.open();
            Cursor cursor = db.query("Orders", new String[]{"id", "email", "startTime", "endTime"}, null, null, null,
                    null, null);
            // Select id, email, startTime, endTime from Orders
            while (cursor.moveToNext()) {
                OrderModel orderModel = new OrderModel(cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("startTime")),
                        cursor.getString(cursor.getColumnIndex("endTime")));
                result.add(orderModel);
            }

        } finally {
            this.close();

        }
        return result;

    }

    public OrderModel getById(String id) throws Exception {
        OrderModel result = new OrderModel();
        try {
            this.open();
            Cursor cursor = db.query("Orders", new String[]{"id", "email", "startTime", "endTime"}, "id = ?", new String[]{id},
                    null, null, null);
            if (cursor.moveToFirst()) {
                result = new OrderModel(cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("startTime")),
                        cursor.getString(cursor.getColumnIndex("endTime")));

            }
        } finally {
            this.close();
        }
        return result;
    }

    public boolean insert(OrderModel orderModel) throws Exception {
        boolean check = false;
        try {
            this.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", orderModel.getId());
            contentValues.put("email", orderModel.getEmail());
            contentValues.put("startTime", orderModel.getStartTime());
            contentValues.put("endTime", orderModel.getEndTime());
            check = db.insert("Orders", null, contentValues) > 0;
        } finally {
            this.close();
        }
        return check;
    }

    public boolean update(OrderModel orderModel) throws Exception {
        boolean check = false;
        try {
            this.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", orderModel.getEmail());
            contentValues.put("startTime", orderModel.getStartTime());
            contentValues.put("endTime", orderModel.getEndTime());
            check = db.update("Orders", contentValues, "id = ?", new String[]{orderModel.getId()}) > 0;
        } finally {
            this.close();
        }
        return check;
    }

    public boolean delete(String id) throws Exception {
        boolean check = false;
        try {
            this.open();
            check = db.delete("Orders", "id = ?", new String[]{id}) > 0;
        } finally {
            this.close();
        }
        return check;
    }

}
