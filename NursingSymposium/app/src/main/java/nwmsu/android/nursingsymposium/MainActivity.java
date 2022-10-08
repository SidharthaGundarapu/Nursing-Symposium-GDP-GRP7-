package nwmsu.android.nursingsymposium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    DashboardFragment dashboardFragment = new DashboardFragment();
    SessionsFragment sessionsFragment = new SessionsFragment();
    MoreFragment moreFragment = new MoreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBottomNav();
    }

    private void createBottomNav() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Having some issues here I think. Had to do the casting to get it to work
        // Pretty sure I'm just referring to this inappropriately.
        bottomNavigationView.setOnNavigationItemSelectedListener(
                (BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.userNav_sessions);
    }

    // Following a tutorial for this one. Not sure I need this
    // Android does their navbar gen differently with the templated bottomNav stuff
    // I need to look into this.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.userNav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboardFragment).commit();
                return true;

            case R.id.userNav_sessions:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, sessionsFragment).commit();
                return true;

            case R.id.userNav_more:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, moreFragment).commit();
                return true;
        }
        return false;
    }

}