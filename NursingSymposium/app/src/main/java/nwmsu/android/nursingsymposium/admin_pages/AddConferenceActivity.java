package com.example.nursingsymposium.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddConferenceActivity extends AppCompatActivity {

    TextInputEditText id_conferenceName;
    ImageView imageview;
    Button image,submitevent;
    String imagestring="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conference);
        id_conferenceName=findViewById(R.id.id_conferenceName);
        image=findViewById(R.id.image);
        imageview=findViewById(R.id.imageview);
        submitevent=findViewById(R.id.submitevent);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AddConferenceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},11);
                    }
                }
                else{
                    opengallery();
                }
            }
        });

        submitevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=id_conferenceName.getText().toString();
                String pic=imagestring;
                String key= FirebaseAuth.getInstance().getUid();

                if(TextUtils.isEmpty(name)){
                Toast.makeText(AddConferenceActivity.this, "Enter Event Name", Toast.LENGTH_SHORT).show();
            }  else if(TextUtils.isEmpty(pic)){
                Toast.makeText(AddConferenceActivity.this, "Select image", Toast.LENGTH_SHORT).show();
            }
                else {
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=firebaseDatabase.getReference("ConferenceEvents").child(key);

                    ConferenceModelAdd conferenceDataModel=new ConferenceModelAdd(
                          key,pic,name
                    );

                    databaseReference.setValue(conferenceDataModel);
                    finish();

                }
            }
        });
    }

    
}