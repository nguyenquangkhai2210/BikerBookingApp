package com.example.bikebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikebookingapp.models.UserModel;
import com.example.bikebookingapp.services.UserService;

public class ProfileActivity extends AppCompatActivity {
    EditText txtEmail, txtFullname, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtEmail = findViewById(R.id.txtEmail);
        txtFullname = findViewById(R.id.txtFullname);
        txtPassword = findViewById(R.id.txtPassword);
        UserService userService = new UserService(this);
        try {
            UserModel userModel = userService.getById("");
            txtEmail.setText(userModel.getEmail());
            txtFullname.setText(userModel.getFullname());
            txtPassword.setText(userModel.getPassword());
        } catch (Exception e) {
            Log.d("Profile Activity", "Error at onCreate " + e.getMessage());
        }


    }

    public void clickToUpdateProfile(View view) {
        String email = txtEmail.getText().toString();
        String fullname = txtFullname.getText().toString();
        String password = txtPassword.getText().toString();
        UserService userService = new UserService(this);
        UserModel userModel = new UserModel(fullname, email, password);
        try {
            boolean check = userService.update(userModel);
            if (check) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("Profile Activity", "Error at ClickToUpdateProfile " + e.getMessage());
        }

    }
}
