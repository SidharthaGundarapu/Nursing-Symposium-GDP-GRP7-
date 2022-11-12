package com.example.nursingsymposium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView id_dashboard_recycler;
    ArrayList<ConferenceModel> arrayList;
    ConstraintLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        id_dashboard_recycler=findViewById(R.id.id_dashboard_recycler);
        logout=findViewById(R.id.logout);
        id_dashboard_recycler.setLayoutManager(new LinearLayoutManager(this));
        getData();
        if(arrayList!=null){
            if(arrayList.size()>0){
                id_dashboard_recycler.setAdapter(new ConferenceAdapter(this,arrayList));
            }
        }

   
}