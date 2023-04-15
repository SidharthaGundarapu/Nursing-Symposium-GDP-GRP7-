package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAddEventBinding;
import com.nwmissouri.edu.conferencescheduler.model.Event;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.nwmissouri.edu.conferencescheduler.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class EventDetailsActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityAddEventBinding binding;
    private Event event;
    private String speakerKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.initialize(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (Event) extras.getSerializable(Constants.EVENT);
        }

        binding.btCreate.setVisibility(View.GONE);
        binding.tfEventDesc.setEnabled(false);
        binding.tfEventLocation.setEnabled(false);
        binding.tfEventName.setEnabled(false);
        binding.tfSpeakerDetails.setEnabled(false);
        binding.tfSpeakerName.setEnabled(false);

        Picasso.get().load(event.getImageUrl()).into(binding.ivEventPhoto);
        binding.etSpeakerDetails.setText(event.getAboutSpeaker());
        binding.etEventDesc.setText(event.getEventDescription());
        binding.etEventName.setText(event.getEventName());
        binding.etEventLocation.setText(event.getLocation());
        binding.etSpeakerName.setText(event.getSpeaker());
        speakerKey = event.getSpeakerKey();
        binding.tvDate.setText(Utils.dateFormat(event.getEventTime()));
        binding.breakoutRadioButton.setEnabled(false);
        binding.keynoteRadioButton.setEnabled(false);
        if (Objects.equals(event.getType(), "BreakOut")) {
            binding.breakoutRadioButton.setChecked(true);
        } else {
            binding.keynoteRadioButton.setChecked(true);
        }
        binding.eventTypeRadioGroup.setVisibility(View.GONE);
        binding.tfSurveyLink.setVisibility(View.GONE);
        binding.tvSurveyLink.setText(event.getSurveyLink());
        binding.tvSurveyLink.setOnClickListener(v -> {
            openUrl(event.getSurveyLink());
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.tfEventName.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);
        binding.tfEventName.getEditText().setBackground(new ColorDrawable(Color.WHITE));
        binding.tfEventDesc.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_edit) {
            Intent i = new Intent(this, AddEventActivity.class);
            i.putExtra(Constants.EVENT, event);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_event, menu);
        MenuItem item = menu.findItem(R.id.action_edit);
        item.setVisible(event != null && !pref.userType().equals(Constants.USER_TYPE_STUDENT)); // Hide the menu item
        return true;
    }

    void openUrl(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }catch (Exception e){
            ToastUtils.showToast(this, "Something went wrong!");
        }
    }

}