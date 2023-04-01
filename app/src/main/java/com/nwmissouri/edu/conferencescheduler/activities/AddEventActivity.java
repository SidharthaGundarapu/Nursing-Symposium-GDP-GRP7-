package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.nwmissouri.edu.conferencescheduler.DialogUtils;
import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAddEventBinding;
import com.nwmissouri.edu.conferencescheduler.model.Event;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.nwmissouri.edu.conferencescheduler.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class AddEventActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityAddEventBinding binding;
    private Event event;
    private Uri pickedImage;
    private Date pickedDate;
    private String conferenceId;
    private String speakerKey;
    private String imageUrl;
    private String subscriberId;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    pickedImage = uri;
                    Picasso.get().load(pickedImage).into(binding.ivEventPhoto);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogUtils dialogUtils = new DialogUtils();
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.initialize(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                event = (Event) extras.getSerializable(Constants.EVENT);
            } catch (Exception e) {
            }
            try {
                conferenceId = (String) extras.getString(Constants.CONFERENCE_ID);
            } catch (Exception e) {
            }
        }

        if (event == null) {
            binding.tvHeading.setText("Add Event");
            binding.btCreate.setText("Add");
            binding.tvEventPhoto.setText("Set event photo");
        } else {
            binding.tvHeading.setText("Update Event");
            binding.btCreate.setText("Update");
            binding.tvEventPhoto.setText("Change event photo");

            Picasso.get().load(event.getImageUrl()).into(binding.ivEventPhoto);
            binding.etSpeakerDetails.setText(event.getAboutSpeaker());
            binding.etEventDesc.setText(event.getEventDescription());
            binding.etEventName.setText(event.getEventName());
            binding.etEventLocation.setText(event.getLocation());
            binding.etSpeakerName.setText(event.getSpeaker());
            speakerKey = event.getSpeakerKey();
            binding.tvDate.setText(Utils.dateFormat(event.getEventTime()));
            pickedDate = event.getEventTime();
            subscriberId = event.getSubscribeDocumentID();
            if (Objects.equals(event.getType(), "BreakOut")) {
                binding.breakoutRadioButton.setChecked(true);
            } else {
                binding.keynoteRadioButton.setChecked(true);
            }
            binding.tfSurveyLink.setVisibility(View.GONE);
            binding.eventTypeRadioGroup.setVisibility(View.GONE);

        }
        binding.btCreate.setOnClickListener(v -> {
            addEvent();
        });

        binding.llEventPhoto.setOnClickListener(v -> {
            pickImage();
        });

        binding.tfSpeakerName.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        binding.tfSpeakerName.setEndIconOnClickListener(v -> {
            dialogUtils.showSpeakerList(this, data -> {
                binding.etSpeakerDetails.setText(data.getDetails());
                binding.etSpeakerName.setText(data.getName());
                speakerKey = data.getKey();
            });
        });

        binding.llDate.setOnClickListener(v -> {
            pickDateAndTime();
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void pickImage() {
        //ActivityResultContracts.PickVisualMedia.VisualMediaType a = VisualMediaType()
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .build());
    }

    private void pickDateAndTime() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    // Set the selected date to the calendar object
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    // Create a time picker dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this,
                            (view1, hourOfDay1, minute1) -> {
                                // Set the selected time to the calendar object
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay1);
                                calendar.set(Calendar.MINUTE, minute1);

							   /* binding.tvDate.setText(year1 + "-" + (month1 + 1) + "-" + dayOfMonth +
										" " + hourOfDay1 + ":" + minute1);*/
                                pickedDate = calendar.getTime();
                                binding.tvDate.setText(Utils.dateFormat(pickedDate));
                            }, hourOfDay, minute, true);
                    timePickerDialog.show();

                }, year, month, day);

        datePickerDialog.show();

    }

    private void addEvent() {
        String name = Objects.requireNonNull(binding.etEventName.getText()).toString();
        String desc = Objects.requireNonNull(binding.etEventDesc.getText()).toString();
        String speakerName = Objects.requireNonNull(binding.etSpeakerName.getText()).toString();
        String speakerDetails = Objects.requireNonNull(binding.etSpeakerDetails.getText()).toString();
        String eventLocation = Objects.requireNonNull(binding.etEventLocation.getText()).toString();
        String surveyLink = Objects.requireNonNull(binding.etSurveyLink.getText()).toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "Please enter event name");
            return;
        }
        if (TextUtils.isEmpty(desc)) {
            ToastUtils.showToast(this, "Please enter event description");
            return;
        }
        if (TextUtils.isEmpty(speakerName)) {
            ToastUtils.showToast(this, "Please select speaker");
            return;
        }
        if (TextUtils.isEmpty(eventLocation)) {
            ToastUtils.showToast(this, "Please enter location");
            return;
        }
        if (pickedDate == null) {
            ToastUtils.showToast(this, "Please select date and time");
            return;
        }
        if (pickedImage == null && event == null) {
            ToastUtils.showToast(this, "Please pick event image");
            return;
        }

        String eventId = null;
        if (event != null) {
            eventId = event.getKey();
            conferenceId = event.getConferenceId();
            imageUrl = event.getImageUrl();
        }

        RadioButton radioButton = findViewById(binding.eventTypeRadioGroup.getCheckedRadioButtonId());
        String type = radioButton.getText().toString();

        String surveyLinkData;
        if (surveyLink.isEmpty()) {
            surveyLinkData = "";
        } else {
            surveyLinkData = surveyLink;
        }
        Event ev = new Event(conferenceId, null, type, name, desc, speakerName, speakerDetails, eventLocation, "", "", imageUrl, speakerKey, pickedDate, subscriberId, surveyLinkData);
        FirebaseUtilsManager.addEvent(this, eventId, conferenceId, pickedImage, ev, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                runOnUiThread(() -> {
                    ToastUtils.showToast(AddEventActivity.this, "Success");
                });
                finishAffinity();
                startActivity(new Intent(AddEventActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(AddEventActivity.this, msg);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            new DialogUtils().showConfirmationDialog(this, this::deleteEvent);
        }
        return super.onOptionsItemSelected(item);
    }

    void deleteEvent() {
        FirebaseUtilsManager.deleteEvent(this, event.getConferenceId(), event.getKey(), new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast(AddEventActivity.this, "Deleted");
                finishAffinity();
                startActivity(new Intent(AddEventActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(AddEventActivity.this, msg);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setVisible(event != null); // Hide the menu item
        return true;
    }

}