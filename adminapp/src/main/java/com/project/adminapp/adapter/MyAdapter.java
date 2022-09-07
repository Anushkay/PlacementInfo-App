package com.project.adminapp.adapter;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.project.adminapp.model.Updates;
import com.project.adminapp.R;
import java.util.ArrayList;

public class MyAdapter extends FirebaseRecyclerAdapter< Updates ,MyAdapter.MyViewHolder> {


    public MyAdapter(@NonNull FirebaseRecyclerOptions<Updates> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull  final MyViewHolder holder,  final int position, @NonNull  final Updates model) {
        holder.updates_text.setText(model.getUpdate());




    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates,parent,false);
        return new MyViewHolder(v);
    }



    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView  updates_text ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            updates_text = (TextView) itemView.findViewById(R.id.updates_text);

        }
    }

}