package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAboutBinding;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityMainBinding;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityMapBinding;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class MapActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.initialize(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}