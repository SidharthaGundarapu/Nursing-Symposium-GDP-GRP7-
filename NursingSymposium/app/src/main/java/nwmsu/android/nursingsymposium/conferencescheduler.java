package com.example.nursingsymposium;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ConferenceSchedulerActivity extends AppCompatActivity {

 
    Button speaker_click;
    ImageView conference_image;
    TextView conference_name,conference_speaker,conference_location,conference_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_scheduler);
        speaker_click=findViewById(R.id.speaker_click);
        conference_image=findViewById(R.id.conference_image);
        conference_name=findViewById(R.id.conference_name);
        conference_speaker=findViewById(R.id.conference_speaker);
        conference_location=findViewById(R.id.conference_location);
        conference_time=findViewById(R.id.conference_time);

