package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.adapter.SpeakersAdapter;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySpeakersBinding;
import com.nwmissouri.edu.conferencescheduler.model.Speaker;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SpeakersActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivitySpeakersBinding binding;
    private SpeakersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpeakersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = MySharedPreferences.initialize(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new SpeakersAdapter();
        adapter.setOnItemClickListener(data -> {
            Intent i = new Intent(binding.getRoot().getContext(), SpeakerDetailsActivity.class);
            i.putExtra(Constants.SPEAKER, data);
            binding.getRoot().getContext().startActivity(i);
        });
        binding.rvSpeakers.setAdapter(adapter);

        FirebaseUtilsManager.listenSpeakersListUpdate(data -> {
            adapter.submitList(data);
        });

        if (!Objects.equals(MySharedPreferences.getInstance().userType(), Constants.USER_TYPE_STUDENT)) {
            binding.btAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btAdd.setVisibility(View.GONE);
        }

        binding.btAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddSpeakersActivity.class));
        });

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