package com.project.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.project.adminapp.Fragments.AdminPostFragment;

import java.util.HashMap;
import java.util.Map;


public class AddUpdate extends AppCompatActivity {

    private EditText update ;
    private Button add ;
    private Button back;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        update =    (EditText) findViewById(R.id.add_new_update);
        add =       (Button) findViewById(R.id.add_button);
        back =      (Button) findViewById(R.id.back_button);

        // back to dashboard when click back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        // add new update to firebase when  click add

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUpdate();
            }
        });
    }

    private void addNewUpdate() {
        Map<String , Object> map = new HashMap<>();
        map.put("update",update.getText().toString());

        if(user != null) {
            FirebaseDatabase.getInstance().getReference().child("updates").push()
                    .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                       // setting blank text to the edittext
                       update.setText("");
                       Toast.makeText(getApplicationContext(),"Added new update Successfully",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(),"could not insert",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddUpdate.this,AdminDashBoard.class);

        intent.putExtra("Check",1);
        startActivity(intent);

    }
}