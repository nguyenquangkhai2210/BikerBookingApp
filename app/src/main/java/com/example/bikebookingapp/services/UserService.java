package com.example.bikebookingapp.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bikebookingapp.helpers.OrderHelper;
import com.example.bikebookingapp.helpers.UserHelper;
import com.example.bikebookingapp.models.OrderModel;
import com.example.bikebookingapp.models.UserModel;

import java.io.Serializable;

public class UserService implements Serializable {
    private SQLiteDatabase db;
    private UserHelper helper;

    public UserService(Context context) {
        helper = new UserHelper(context, "BikeBooking", null, 1);
    }

    private void open() {
        db = helper.getWritableDatabase();
    }

    private void close() {
        if (db == null) {
            db.close();
        }
    }

    public UserModel getById(String email) throws Exception {
        UserModel result = new UserModel();
        try {
            this.open();
            Cursor cursor = db.query("Orders", new String[]{"fullname", "email", "password"}, "email = ?", new String[]{email},
                    null, null, null);
            if (cursor.moveToFirst()) {
                result = new UserModel(
                        cursor.getString(cursor.getColumnIndex("fullname")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("password")));

            }
        } finally {
            this.close();
        }
        return result;
    }

    public boolean update(UserModel userModel) throws Exception {
        boolean check = false;
        try {
            this.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put("fullname", userModel.getFullname());

            contentValues.put("password", userModel.getPassword());
            check = db.update("User", contentValues, "email = ?", new String[]{userModel.getEmail()}) > 0;
        } finally {
            this.close();
        }
        return check;
    }

    public boolean insert(UserModel userModel) throws Exception {
        boolean check = false;
        try {
            this.open();
            ContentValues contentValues = new ContentValues();

            contentValues.put("fullname", userModel.getFullname());
            contentValues.put("email", userModel.getEmail());
            contentValues.put("password", userModel.getPassword());
            check = db.insert("Users", null, contentValues) > 0;
        } finally {
            this.close();
        }
        return check;
    }
}
