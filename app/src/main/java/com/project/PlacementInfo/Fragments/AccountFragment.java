package com.project.PlacementInfo.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.PlacementInfo.DashBoard;
import com.project.PlacementInfo.R;
import com.project.PlacementInfo.models.UserDataStore;


import java.util.HashMap;
import java.util.Objects;


public class AccountFragment extends Fragment implements View.OnClickListener {
    private String NAME, EMAIL, PHONE, ENROLLMENT, BRANCH, CGPA, PASSWORD;

    private TextInputEditText fullname, email, phone, enrollment, branch, cgpa, password;
    private TextView profileText;
    private HashMap<String , Object>  update = new HashMap<>();


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser").child(user.getUid());


    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Profile");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_account, container, false);

        fullname = view.findViewById(R.id.update_text);
        email = view.findViewById(R.id.update_email);
        phone = view.findViewById(R.id.update_phone);
        enrollment = view.findViewById(R.id.update_enrollment);
        branch = view.findViewById(R.id.update_branch);
        cgpa = view.findViewById(R.id.update_cgpa);
        password = view.findViewById(R.id.update_password);
        profileText = view.findViewById(R.id.user_profile_text);

        Button button = view.findViewById(R.id.update_button);
        button.setOnClickListener(this);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDataStore userDataStore = snapshot.getValue(UserDataStore.class);
                if (userDataStore != null) {
                    fullname.setText(userDataStore.getName());
                    NAME = userDataStore.getName();
                    profileText.setText(userDataStore.getName());
                    email.setText(userDataStore.getEmail());
                    EMAIL = userDataStore.getEmail();
                    phone.setText(userDataStore.getPhone_number());
                    PHONE = userDataStore.getPhone_number();
                    enrollment.setText(userDataStore.getEnrollment());
                    ENROLLMENT = userDataStore.getEnrollment();
                    branch.setText(userDataStore.getBranch());
                    BRANCH = userDataStore.getBranch();
                    cgpa.setText(userDataStore.getCGPA());
                    CGPA = userDataStore.getCGPA();
                    password.setText(userDataStore.getPassword());
                    PASSWORD = userDataStore.getPassword();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;

    }

    public  void onClickUpdateButton(View view)
    {


        if(isFullNameChanged() || isEmailChanged() || isPhoneNumberChanged() || isEnrollmentChanged() || isBranchChanged() || isCGPAChanged() || isPassWordChanged() )
            {
                Toast.makeText(getActivity(), "Data has been updated", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Data is same and can not be updated", Toast.LENGTH_LONG).show();
            }
    }


    private boolean isPassWordChanged() {
        if(password.getText().toString().length() > 0   && !PASSWORD.equals(password.getText().toString()))
        {
            update.put("password",password.getText().toString());
            reference.updateChildren(update);
            PASSWORD = password.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isCGPAChanged() {
        if(cgpa.getText().toString().length() > 0   && !CGPA.equals(cgpa.getText().toString()))
        {
            update.put("cgpa" ,cgpa.getText().toString());
            reference.updateChildren(update);
            CGPA = cgpa.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isBranchChanged() {
        if(branch.getText().toString().length() > 0   && !BRANCH.equals(branch.getText().toString()))
        {
            update.put("branch" ,branch.getText().toString());
            reference.updateChildren(update);
            BRANCH = branch.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isEnrollmentChanged() {
        if(enrollment.getText().toString().length() > 0   && !ENROLLMENT.equals(enrollment.getText().toString()))
        {
            update.put("enrollment",enrollment.getText().toString());
            reference.updateChildren(update);
            ENROLLMENT = enrollment.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if(phone.getText().toString().length() > 0   && !PHONE.equals(phone.getText().toString()))
        {
            update.put("phone_number",phone.getText().toString());
            reference.updateChildren(update);
            PHONE  =  phone.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(email.getText().toString().length() > 0   && !EMAIL.equals(email.getText().toString()))
        {
            update.put("email", email.getText().toString().trim());
            reference.updateChildren(update);
            EMAIL = email.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isFullNameChanged() {
            if(fullname.getText().toString().length() > 0   && !NAME.equals(fullname.getText().toString()))
            {
//
                update.put("name", fullname.getText().toString());
                reference.updateChildren(update);
                NAME = fullname.getText().toString();
                return true;
            }
            else
            {
                return false;
            }
    }

    @Override
    public void onClick(View view) {
            onClickUpdateButton(view);
    }
}
