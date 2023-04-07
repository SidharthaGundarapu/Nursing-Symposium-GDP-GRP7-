package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAboutBinding;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

@SuppressLint("CustomSplashScreen")
public class AboutActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.initialize(this);

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