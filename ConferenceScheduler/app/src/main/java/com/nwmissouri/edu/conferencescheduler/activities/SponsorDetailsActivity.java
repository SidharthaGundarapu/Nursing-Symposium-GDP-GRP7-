package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySpeakerDetailsBinding;
import com.nwmissouri.edu.conferencescheduler.model.Sponsor;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SponsorDetailsActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivitySpeakerDetailsBinding binding;
    private Sponsor sponsor;

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
            sponsor = (Sponsor) extras.getSerializable(Constants.SPONSOR);
        }

        Picasso.get().load(sponsor.getImageUrl()).into(binding.ivSpeaker);
        binding.tvName.setText(sponsor.getName());
        binding.tvDetails.setText(sponsor.getDetails());

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