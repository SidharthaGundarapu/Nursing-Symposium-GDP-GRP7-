package com.nwmissouri.edu.conferencescheduler.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityMainBinding;
import com.nwmissouri.edu.conferencescheduler.fragments.AddAdminFragment;
import com.nwmissouri.edu.conferencescheduler.fragments.HomeFragment;
import com.nwmissouri.edu.conferencescheduler.fragments.MoreFragment;
import com.nwmissouri.edu.conferencescheduler.fragments.ProfileFragment;
import com.nwmissouri.edu.conferencescheduler.fragments.ScheduleFragment;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView bottomNav;

    private MySharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.getInstance();

        setBottomNavigation();
    }

    private void setBottomNavigation() {
        bottomNav = findViewById(R.id.navView);

        Menu menu = bottomNav.getMenu();
        MenuInflater inflater = getMenuInflater();

        if (TextUtils.equals(pref.userType(), Constants.USER_TYPE_SUPER_ADMIN)) {
            menu.findItem(R.id.navigation_schedule).setVisible(false);
            menu.findItem(R.id.navigation_more).setVisible(false);
        } else if (TextUtils.equals(pref.userType(), Constants.USER_TYPE_ADMIN)) {
            menu.findItem(R.id.navigation_create_admin).setVisible(false);
            menu.findItem(R.id.navigation_schedule).setVisible(false);
        } else {
            menu.findItem(R.id.navigation_create_admin).setVisible(false);
            menu.findItem(R.id.navigation_create_admin).setVisible(false);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                setFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.navigation_create_admin) {
                setFragment(new AddAdminFragment());
                return true;
            } else if (itemId == R.id.navigation_profile) {
                setFragment(new ProfileFragment());
                return true;
            } else if (itemId == R.id.navigation_schedule) {
                setFragment(new ScheduleFragment());
                return true;
            } else if (itemId == R.id.navigation_more) {
                setFragment(new MoreFragment());
                return true;
            }
            return true;
        });

        bottomNav.setSelectedItemId(R.id.navigation_home);

    }

    private void setFragment(Fragment fg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fg).commit();
    }

}