package main.java.nwmsu.android.nursingsymposium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SpeakerActivity extends AppCompatActivity {

    TextView speakerName,speakerabout;
    ImageView eventpic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        eventpic=findViewById(R.id.eventpic);
        speakerabout=findViewById(R.id.speakerabout);
        speakerName=findViewById(R.id.speakerName);
        
        String image=getIntent().getStringExtra("image");
        String spekaer=getIntent().getStringExtra("spekaer");
        String aboutSpeaker=getIntent().getStringExtra("aboutSpeaker");
        speakerName.setText(speaker);
        speakerabout.setText(aboutSpeaker);
        Picasso.get().load(image).into(eventpic);
    
    }

}    