package com.project.PlacementInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.PlacementInfo.Fragments.AccountFragment;
import com.project.PlacementInfo.Fragments.ResumeUploadFragment;
import com.project.PlacementInfo.Fragments.UpdateFragment;

public class DashBoard extends AppCompatActivity {
     private BottomNavigationView bottomNavigationView;
     private Fragment SelectedFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // changing the status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black, getApplicationContext().getTheme()));

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.account:
                    SelectedFragment = new AccountFragment();
                    break;

                case R.id.settings:
                     SelectedFragment = new ResumeUploadFragment();
                    break;

                default:
                    SelectedFragment = new UpdateFragment();
            }

          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,SelectedFragment).commit();
          return true;
      });

      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateFragment()).commit();

    }
}