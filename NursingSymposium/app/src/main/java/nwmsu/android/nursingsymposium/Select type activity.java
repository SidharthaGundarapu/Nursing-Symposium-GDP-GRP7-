package com.example.nursingsymposium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SelectTypeActivity extends AppCompatActivity {
    
    CardView student,admin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        student=findViewById(R.id.student);
        admin=findViewById(R.id.admins);