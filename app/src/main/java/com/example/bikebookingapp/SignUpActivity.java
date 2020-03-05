package com.example.bikebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText e1_name, e2_email, e3_password;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        e1_name = findViewById(R.id.editText3);
        e2_email = findViewById(R.id.editText4);
        e3_password = findViewById(R.id.editText5);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }

    public void signUpUser(View v) {
        dialog.setMessage("Registering. Please waiting");
        dialog.show();

        String name = e1_name.getText().toString();
        String email = e2_email.getText().toString();
        String password = e3_password.getText().toString();

        if(name.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(), "Fields  cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();


                            } else {
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User could not be registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
