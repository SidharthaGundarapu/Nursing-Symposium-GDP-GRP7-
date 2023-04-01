package com.nwmissouri.edu.conferencescheduler.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.DialogUtils;
import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivityCreateConferenceBinding;
import com.nwmissouri.edu.conferencescheduler.model.Conference;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class AddConferenceActivity extends AppCompatActivity {

    private ActivityCreateConferenceBinding binding;
    private Conference conference;
    private Uri pickedImage;

    private MySharedPreferences pref;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    pickedImage = uri;
                    Picasso.get().load(pickedImage).into(binding.ivConferencePhoto);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateConferenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            conference = (Conference) extras.getSerializable(Constants.CONFERENCE);
        }
        binding.btCreate.setOnClickListener(v -> {
            addConference();
        });

        binding.llAddConferencePhoto.setOnClickListener(v -> {
            pickImage();
        });

        if (conference != null) {
            binding.tvHeading.setText("Update Conference");
            binding.btCreate.setText("Update");
            binding.tvConferenceImagePick.setText("Change Conference Photo");
            Picasso.get().load(conference.getImageUrl()).into(binding.ivConferencePhoto);
            binding.etConferenceName.setText(conference.getName());
        } else {
            binding.tvHeading.setText("Create Conference");
            binding.btCreate.setText("Create");
            binding.tvConferenceImagePick.setText("Add Conference Photo");
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addConference() {
        String name = Objects.requireNonNull(binding.etConferenceName.getText()).toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "Please enter name");
            return;
        }
        if (pickedImage == null && conference == null) {
            ToastUtils.showToast(this, "Please pick conference image");
            return;
        }

        FirebaseUtilsManager.addConference(this, name, pickedImage, conference, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String msg) {
                binding.etConferenceName.setText("");
                pickedImage = null;
                ToastUtils.showToast(AddConferenceActivity.this, "Success");
                finish();
            }

            @Override
            public void onFailure(String msg) {
            }
        });

    }

    private void pickImage() {
        //ActivityResultContracts.PickVisualMedia.VisualMediaType a = VisualMediaType()
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setVisible(conference != null); // Hide the menu item
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            new DialogUtils().showConfirmationDialog(this, this::deleteConference);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void deleteConference() {
        FirebaseUtilsManager.deleteConference(this, conference.getKey(), new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast(AddConferenceActivity.this, "Deleted");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(AddConferenceActivity.this, msg);
            }
        });
    }


}