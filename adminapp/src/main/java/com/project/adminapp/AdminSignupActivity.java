package com.project.adminapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.adminapp.model.AdminDataStore;


import java.util.Objects;

public class AdminSignupActivity extends AppCompatActivity {

    private TextInputLayout fullname_var, email_var, createPassword_var;
    private FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        Objects.requireNonNull(getSupportActionBar()).hide();

        fullname_var = findViewById(R.id.full_name_textfield_design);
        email_var = findViewById(R.id.email_textfield_design);

        createPassword_var = findViewById(R.id.signup_password_textfield_design);
        auth = FirebaseAuth.getInstance();

    }

    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void onRegisterButtonClick(View view) {
        String fullname = fullname_var.getEditText().getText().toString();
        String email = email_var.getEditText().getText().toString();
        String createPassword = createPassword_var.getEditText().getText().toString();

        // if any single value is empty  signup cannot be done toast will be shown
        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email)  || TextUtils.isEmpty(createPassword)) {
            Toast.makeText(this, "Please Enter All The Required Field", Toast.LENGTH_SHORT).show();
        } else if (createPassword.length() < 6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        } else {
            register(fullname, email, createPassword);
        }


    }



    private void register(String name, String email, String password) {


        String fullname_s = fullname_var.getEditText().getText().toString();
        String email_s = email_var.getEditText().getText().toString();
        String createPassword_s = createPassword_var.getEditText().getText().toString();


        // creating user
        auth.createUserWithEmailAndPassword(email_s, createPassword_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AdminDataStore adminDataStore = new AdminDataStore(fullname_s, email_s, createPassword_s);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference("admin-data").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(adminDataStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.registration_successful), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        Intent intent = new Intent(getApplicationContext(), AdminDashBoard.class);
        startActivity(intent);
        finish();


    }

}