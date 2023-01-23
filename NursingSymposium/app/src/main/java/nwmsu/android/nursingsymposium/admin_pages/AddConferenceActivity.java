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

    private void opengallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 22);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                opengallery();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 22:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imagestring=picturePath;
                                imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                            uploadImagetofirebase(selectedImage);

                        }
                    }
                    break;
            }
        }
    }

    private void uploadImagetofirebase(Uri selectedImage) {
        if(selectedImage!=null){
            // Defining the child of storageReference
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(selectedImage);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imagestring=downloadUri.toString();

                    } else {
                        // Handle failures
                    }
                }
            });


        }
    }
}