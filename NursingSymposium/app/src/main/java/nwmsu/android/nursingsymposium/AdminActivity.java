package com.example.nursingsymposium.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    FloatingActionButton addEvent;
    RecyclerView id_dashboard_recycler;
    ArrayList<ConferenceDataModel> models;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addEvent=findViewById(R.id.addEvent);
        id_dashboard_recycler=findViewById(R.id.id_dashboard_recycler);
        id_dashboard_recycler.setLayoutManager(new LinearLayoutManager(AdminActivity.this));

        getdata();
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,AddEventActivity.class);
                startActivity(intent);
            }
        });
    }

   
}