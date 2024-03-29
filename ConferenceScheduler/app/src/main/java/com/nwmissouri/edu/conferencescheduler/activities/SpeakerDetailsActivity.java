package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.adapter.SpeakersAdapter;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySpeakerDetailsBinding;
import com.nwmissouri.edu.conferencescheduler.model.Conference;
import com.nwmissouri.edu.conferencescheduler.model.Speaker;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SpeakerDetailsActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivitySpeakerDetailsBinding binding;
    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpeakerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = MySharedPreferences.initialize(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            speaker = (Speaker) extras.getSerializable(Constants.SPEAKER);
        }

        Picasso.get().load(speaker.getImageUrl()).into(binding.ivSpeaker);
        binding.tvName.setText(speaker.getName());
        binding.tvDetails.setText(speaker.getDetails());

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