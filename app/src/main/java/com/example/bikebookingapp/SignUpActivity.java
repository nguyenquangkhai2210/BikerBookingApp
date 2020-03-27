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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText e1_name, e2_email, e3_password;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;

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

        if(name.equals("") || email.equals("") && password.equals("")){
            Toast.makeText(getApplicationContext(), "Fields  cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();

                                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                Users users_obj = new Users( e1_name.getText().toString(), e2_email.getText().toString(), e3_password.getText().toString());
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                databaseReference.child(firebaseUser.getUid()).setValue(users_obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "User data saved", Toast.LENGTH_LONG).show();
                                            Intent myIntent = new Intent(SignUpActivity.this, MainPageActivity.class);
                                            startActivity(myIntent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "User data could not be saved", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User could not be registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
