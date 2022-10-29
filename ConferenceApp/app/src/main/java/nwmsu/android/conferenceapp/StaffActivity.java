package nwmsu.android.conferenceapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import nwmsu.android.conferenceapp.databinding.ActivityStaffBinding;

public class StaffActivity extends AppCompatActivity {

    private ActivityStaffBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView staffView = findViewById(R.id.staff_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tools, R.id.navigation_statistics, R.id.navigation_staff_menu)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_staff);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.staffView, navController);
    }

}