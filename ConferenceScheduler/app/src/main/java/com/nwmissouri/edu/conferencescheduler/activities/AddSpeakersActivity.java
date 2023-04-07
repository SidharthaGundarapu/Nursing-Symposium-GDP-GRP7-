package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAddSpeakerBinding;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class AddSpeakersActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityAddSpeakerBinding binding;
    private Uri pickedImage;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    pickedImage = uri;
                    Picasso.get().load(pickedImage).into(binding.ivSpeaker);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddSpeakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = MySharedPreferences.initialize(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.btAddSpeaker.setOnClickListener(v -> {
            addSpeaker();
        });

        binding.llAddSpeakerPhoto.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .build());
        });

    }

    void addSpeaker() {
        String name = Objects.requireNonNull(binding.etSpeakerName.getText()).toString();
        String details = Objects.requireNonNull(binding.etSpeakerDetails.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "Please enter speaker name");
            return;
        }
        if (TextUtils.isEmpty(details)) {
            ToastUtils.showToast(this, "Please enter details");
            return;
        }

        if (pickedImage == null) {
            ToastUtils.showToast(this, "Please pick image");
            return;
        }

        FirebaseUtilsManager.addSpeaker(this, name, details, pickedImage, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast(AddSpeakersActivity.this, "Speaker Added");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(AddSpeakersActivity.this, msg);
            }
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