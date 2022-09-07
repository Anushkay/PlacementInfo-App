package com.project.adminapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.project.adminapp.AdminDashBoard;
import com.project.adminapp.R;
import com.project.adminapp.model.StudentDataStore;

import org.w3c.dom.Text;


public class StudentDetailAdapter extends FirebaseRecyclerAdapter<StudentDataStore, StudentDetailAdapter.MyViewHolder> {

    public StudentDetailAdapter(@NonNull FirebaseRecyclerOptions<StudentDataStore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final StudentDetailAdapter.MyViewHolder holder, final int position, @NonNull final StudentDataStore model) {
        holder.name.setText(model.getName());
        holder.branch.setText(model.getBranch());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                // dialog for editing the update
                final DialogPlus dialogPlus = DialogPlus.newDialog(view.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_student_detail)).setExpanded(true, 900).setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel(DialogPlus dialog) {
                                dialog.dismiss();
                            }
                        }).setOnBackPressListener(new OnBackPressListener() {
                            @Override
                            public void onBackPressed(DialogPlus dialog) {
                                dialog.dismiss();
                            }
                        }).create();

                // setting the text
                View myView = dialogPlus.getHolderView();
                TextView name = myView.findViewById(R.id.name);
                TextView email = myView.findViewById(R.id.email);
                TextView phone = myView.findViewById(R.id.phone);
                TextView branch = myView.findViewById(R.id.branch);
                TextView enrollment = myView.findViewById(R.id.enrollment);
                TextView cgpa = myView.findViewById(R.id.cgpa);


                name.setText(model.getName());
                email.setText(model.getEmail());
                phone.setText(model.getPhone_number());
                branch.setText(model.getBranch());
                enrollment.setText(model.getEnrollment());
                cgpa.setText(model.getCGPA());
                dialogPlus.show();

            }
        });

    }

    @NonNull
    @Override
    public StudentDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_detail, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView branch;
        View view ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.student_name);
            branch = (TextView) itemView.findViewById(R.id.student_branch);
            view = itemView;




        }
    }
}
