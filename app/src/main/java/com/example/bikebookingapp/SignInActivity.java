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

public class SignInActivity extends AppCompatActivity {
    EditText e1_email, e2_password;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        e1_email = findViewById(R.id.editText1);
        e2_password = findViewById(R.id.editText2);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        auth.updateCurrentUser()
    }

    public void signinUser(View v) {
        dialog.setMessage("Signing in, Please wait");
        dialog.show();

        if(e1_email.getText().toString().equals("") || e2_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Fields  cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(e1_email.getText().toString(), e2_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User successfully signed in", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
