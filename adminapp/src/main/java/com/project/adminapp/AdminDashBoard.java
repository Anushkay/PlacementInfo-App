package com.project.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.adminapp.Fragments.AdminPostFragment;
import com.project.adminapp.Fragments.GetResumesFragment;
import com.project.adminapp.Fragments.StudentDetailFragment;

public class AdminDashBoard extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment SelectedFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        // changing the status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black, getApplicationContext().getTheme()));

        Intent intent = getIntent();
        String s1 = intent.getStringExtra("Check");



        if(s1!=null && s1.equals(""))
        {
            s1 = "";
            Fragment fragment = new AdminPostFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminPostFragment()).commit();

        }


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.get_resumes:
                    SelectedFragment = new GetResumesFragment();
                    break;

                case R.id.student_details:
                    SelectedFragment = new StudentDetailFragment();
                    break;

                default:
                    SelectedFragment = new AdminPostFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,SelectedFragment).commit();
            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminPostFragment()).commit();

    }
    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }


}
