package com.project.adminapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.project.adminapp.AdminDashBoard;
import com.project.adminapp.R;
import com.project.adminapp.adapter.GetResumesAdapter;
import com.project.adminapp.adapter.MyAdapter;
import com.project.adminapp.model.GetResumesModel;
import com.project.adminapp.model.Updates;

public class GetResumesFragment extends Fragment {

    private RecyclerView recyclerView ;
    private GetResumesAdapter adapter ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminDashBoard) getActivity()).getSupportActionBar().setTitle("Get Resumes");
    }

    public GetResumesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_resumes, container, false);
        recyclerView = view.findViewById(R.id.resumes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseRecyclerOptions<GetResumesModel> options =
                    new FirebaseRecyclerOptions.Builder<GetResumesModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("resume-uploads"), GetResumesModel.class)
                            .build();

            adapter = new GetResumesAdapter(options);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}