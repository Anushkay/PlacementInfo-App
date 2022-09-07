package com.project.PlacementInfo.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.PlacementInfo.DashBoard;
import com.project.PlacementInfo.R;
import com.project.PlacementInfo.models.UploadResumeFile;
import com.project.PlacementInfo.models.UserDataStore;

import java.util.Objects;


public class ResumeUploadFragment extends Fragment {

    EditText editPDFName;
    Button uploadButton;
    private String user_name;
    private String enrollment;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("datauser").child(user.getUid());
    ActivityResultLauncher<Intent> resultLauncher;
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Upload your File");
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resume_upload, container, false);
        editPDFName = view.findViewById(R.id.edit_text_design);
        uploadButton = view.findViewById(R.id.upload_button);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("resume-uploads");
        setUsernameAndEnrollment();


        //initialize resultLauncher
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // permission granted
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            uploadPDFFile(data.getData());

                        } else {
                            // permision denied
                        }
                    }
                }
        );

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for permission
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // when permission is not granted
                    // result permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    // when permission is granted
                    if (editPDFName.getText().toString().matches("")) {
                        Toast.makeText(getActivity(), "file name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        openFileDialog(view);
                    }
                }
            }
        });


        return view;
    }

    private void uploadPDFFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("uploading...");
        progressDialog.show();
        StorageReference reference = storageReference.child("resumeUploads/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete()) ;
                Uri url = uri.getResult();

                // getting name and enrollment of current user


                UploadResumeFile uploadResumeFile = new UploadResumeFile(editPDFName.getText().toString(), url.toString(), user_name, enrollment);
                if (FirebaseAuth.getInstance().getCurrentUser() != null)
                    databaseReference.child(databaseReference.push().getKey()).setValue(uploadResumeFile);
                Toast.makeText(getActivity(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploading...");
            }
        });
    }

    private void setUsernameAndEnrollment() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDataStore userDataStore = snapshot.getValue(UserDataStore.class);
                {
                    if (user != null && userDataStore != null) {
                        user_name = userDataStore.getName();
                        enrollment = userDataStore.getEnrollment();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void openFileDialog(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent = Intent.createChooser(intent, "Choose a file");
        resultLauncher.launch(intent);
    }


}



