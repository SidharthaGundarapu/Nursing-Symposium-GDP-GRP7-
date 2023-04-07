package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.RadioButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivityAddSponsorBinding;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class AddSponsorActivity extends AppCompatActivity {
    private MySharedPreferences pref;
    private ActivityAddSponsorBinding binding;
    private Uri pickedImage;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    pickedImage = uri;
                    Picasso.get().load(pickedImage).into(binding.iv);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddSponsorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = MySharedPreferences.initialize(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.btAdd.setOnClickListener(v -> {
            addSponsor();
        });

        binding.llAddPhoto.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .build());
        });

    }

    void addSponsor() {
        String name = Objects.requireNonNull(binding.etName.getText()).toString();
        String details = Objects.requireNonNull(binding.etDetails.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "Please enter sponsor name");
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

        RadioButton radioButton = findViewById(binding.sponsorTypeRadioGroup.getCheckedRadioButtonId());
        String type = radioButton.getText().toString();

        FirebaseUtilsManager.addSponsor(this, name, details, type, pickedImage, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast(AddSponsorActivity.this, "Sponsor Added");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(AddSponsorActivity.this, msg);
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