package com.project.adminapp.Fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.adminapp.AdminDashBoard;
import com.project.adminapp.R;
import com.project.adminapp.adapter.StudentDetailAdapter;
import com.project.adminapp.model.StudentDataStore;
import com.project.adminapp.model.Updates;


public class StudentDetailFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imageView;
    StudentDetailAdapter studentDetailAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminDashBoard) getActivity()).getSupportActionBar().setTitle("Students");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_detail, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.student_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseRecyclerOptions<StudentDataStore> options =
                    new FirebaseRecyclerOptions.Builder<StudentDataStore>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("datauser"), StudentDataStore.class)
                            .build();

            studentDetailAdapter = new StudentDetailAdapter(options);
            recyclerView.setAdapter(studentDetailAdapter);
            studentDetailAdapter.notifyDataSetChanged();



        }

        return  view ;
    }

    @Override
    public void onStart() {
        super.onStart();
        studentDetailAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        studentDetailAdapter.stopListening();
    }
}