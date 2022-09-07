package com.project.PlacementInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.Queue;

public class LoginActivity extends AppCompatActivity {

    Button signupButton , loginButton;
    TextInputLayout username_var , password_var ;
    private FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        signupButton = (Button) findViewById(R.id.signup_button);
        loginButton =  (Button) findViewById(R.id.login_button);
        username_var = (TextInputLayout) findViewById(R.id.username_textfield_design);
        password_var = (TextInputLayout)findViewById(R.id.password_textfield_design);

        auth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_var.getEditText().getText().toString();
                String password = password_var.getEditText().getText().toString();

                if(!username.isEmpty())
                {
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if (!password.isEmpty()){
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);
                         String username_data = username_var.getEditText().getText().toString().trim();
                         String password_data = password_var.getEditText().getText().toString();



                        // checking the username and password
                        loginUser(username_data,password_data);

                    }
                    else
                    {
                        password_var.setError("please enter the password");
                    }
                }
                else
                {
                    username_var.setError("please enter the email-id");
                }
            }
        });

    }

    // login method
    private void loginUser(String email , String password)
    {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



}