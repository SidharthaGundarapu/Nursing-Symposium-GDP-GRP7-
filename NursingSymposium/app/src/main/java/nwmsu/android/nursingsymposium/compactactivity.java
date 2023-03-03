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
        
    

}    