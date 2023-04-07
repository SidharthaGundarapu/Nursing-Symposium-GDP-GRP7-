package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.adapter.SponsorAdapter;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySponsorsBinding;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SponsorsActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivitySponsorsBinding binding;

    private SponsorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySponsorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.initialize(this);
        adapter = new SponsorAdapter();
        binding.rv.setAdapter(adapter);

        FirebaseUtilsManager.listenSponsorsList(data -> {
            adapter.setSponsors(data);
        });

        if (!Objects.equals(MySharedPreferences.getInstance().userType(), Constants.USER_TYPE_STUDENT)) {
            binding.btAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btAdd.setVisibility(View.GONE);
        }

        binding.btAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddSponsorActivity.class));
        });

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