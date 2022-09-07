package com.project.adminapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.project.adminapp.AddUpdate;
import com.project.adminapp.AdminDashBoard;
import com.project.adminapp.model.Updates;
import com.project.adminapp.adapter.MyAdapter;
import com.project.adminapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AdminPostFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    FloatingActionButton fab;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminDashBoard) getActivity()).getSupportActionBar().setTitle("Updates");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin_post, container, false);
        recyclerView = view.findViewById(R.id.updates_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab = view.findViewById(R.id.floatingActionButton);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseRecyclerOptions<Updates> options =
                    new FirebaseRecyclerOptions.Builder<Updates>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("updates"), Updates.class)
                            .build();

            myAdapter = new MyAdapter(options);
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }

        // adding update

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddUpdate.class));
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
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

    // left swipe to edit and right swipe to delete
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT: {
                    // deleting update
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("deleting update");
                    builder.setMessage("please Confirm before delete");

                    // confirm
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase.getInstance().getReference().child("updates").child(Objects.requireNonNull(myAdapter.getRef(position).getKey())).removeValue();
                        }
                    });

                    // cancel
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();

                    break;
                }
                case ItemTouchHelper.RIGHT: {

                    // dialog for editing the update
                    final DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
                            .setContentHolder(new ViewHolder(R.layout.edit_dialog)).setExpanded(true, 1100).setOnCancelListener(new OnCancelListener() {
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
                    EditText editUpdate = myView.findViewById(R.id.edit_update);
                    Button saveButton = myView.findViewById(R.id.save_button);

                    editUpdate.setText(myAdapter.getItem(position).getUpdate());

                    dialogPlus.show();

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // if the data is changed then only , following code will run
                            String update = myAdapter.getItem(position).getUpdate();
                            if (!update.equals(editUpdate.getText().toString()) && user != null) {

                                Map<String, Object> map = new HashMap<>();
                                map.put("update", editUpdate.getText().toString());
//
//
                                FirebaseDatabase.getInstance().getReference().child("updates").child(Objects.requireNonNull(myAdapter.getRef(position).getKey())).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialogPlus.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                            } else {
                                dialogPlus.dismiss();
                            }
                        }
                    });


                    break;
                }
            }
            myAdapter.notifyItemChanged(viewHolder.getBindingAdapterPosition());
        }

        // design of left and write swipe for edit and delete

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
//            float newDx = dx;
//            if (newDx >= 100f) {
//                newDx = 100f;
//            }
            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
            Drawable icon;
            ColorDrawable background;

            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;
            if (dx > 0) {
                icon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_edit_24);
                background = new ColorDrawable(Color.GRAY);
            } else {
                icon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_delete_outline_24);
                background = new ColorDrawable(Color.RED);

            }
            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dx > 0) {
                // swipping to the right
                int iconLeft = itemView.getLeft() + iconMargin;
                int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dx) + backgroundCornerOffset, itemView.getBottom());
            } else if (dx < 0) {
                // swipping to left
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dx) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());

            } else {
                background.setBounds(0, 0, 0, 0);

            }
            background.draw(c);
            icon.draw(c);
        }
    };


}