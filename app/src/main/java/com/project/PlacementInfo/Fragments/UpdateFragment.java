package com.project.PlacementInfo.Fragments;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.project.PlacementInfo.Adapter.MyAdapter;

import com.project.PlacementInfo.DashBoard;
import com.project.PlacementInfo.R;
import com.project.PlacementInfo.models.Updates;

import java.util.ArrayList;

public class UpdateFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Updates> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Updates");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_update, container, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseRecyclerOptions<Updates> options =
                    new FirebaseRecyclerOptions.Builder<Updates>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("updates"), Updates.class)
                            .build();


            myAdapter = new MyAdapter(options);

            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }


        return recyclerView;

    }

    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}