package com.project.adminapp.adapter;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.project.adminapp.ViewPdfActivity;
import com.project.adminapp.model.GetResumesModel;
import com.project.adminapp.model.Updates;
import com.project.adminapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GetResumesAdapter extends FirebaseRecyclerAdapter< GetResumesModel , GetResumesAdapter.MyViewHolder> {


    public GetResumesAdapter(@NonNull FirebaseRecyclerOptions<GetResumesModel> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull  final MyViewHolder holder,  final int position, @NonNull  final GetResumesModel model) {
        holder.username.setText(model.getUser_name());
        holder.enrollment.setText(model.getEnrollment());
        holder.file_name.setText(model.getName());

        holder.pdf_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.pdf_image.getContext(), ViewPdfActivity.class);
                intent.putExtra("filename",model.getName());
                intent.putExtra("url",model.getUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.pdf_image.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_resumes_card,parent,false);
        return new MyViewHolder(v);
    }



    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView pdf_image;
        TextView  username ;
        TextView enrollment;
        TextView file_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pdf_image = (ImageView) itemView.findViewById(R.id.pdf_image);
            username = (TextView) itemView.findViewById(R.id.student_name);
            enrollment = (TextView) itemView.findViewById(R.id.student_enrollment);
            file_name = (TextView) itemView.findViewById(R.id.file_name);

        }
    }

}
