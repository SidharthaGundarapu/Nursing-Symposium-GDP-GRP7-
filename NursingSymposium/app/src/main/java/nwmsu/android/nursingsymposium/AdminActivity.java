package com.example.nursingsymposium.admin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nursingsymposium.ConferenceAdapter;
import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.DashBoardActivity;
import com.example.nursingsymposium.R;
import com.example.nursingsymposium.fragments.ConferenceFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    
    FloatingActionButton addEvent,addConferrence;
    RecyclerView id_dashboard_recycler;    
    ArrayList<ConferenceDataModel> arrayList;    
    AdminConferenceAdapter adminConferenceAdapter;    
    
    @Override    
    protected void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_admin);        
        addEvent=findViewById(R.id.addEvent);        
        addConferrence=findViewById(R.id.addConferrence);        
        id_dashboard_recycler=findViewById(R.id.id_dashboard_recycler);        
        id_dashboard_recycler.setLayoutManager(new LinearLayoutManager(AdminActivity.this));

        arrayList=new ArrayList<>();        
        getData(arrayList);        
        addEvent.setOnClickListener(new View.OnClickListener() {            
            @Override            
            public void onClick(View view) {                
                Intent intent=new Intent(AdminActivity.this,AddEventActivity.class);                
                startActivity(intent);            
            }        
        });
        addConferrence.setOnClickListener(new View.OnClickListener() {            
            @Override            
            public void onClick(View view) {                
                Intent intent=new Intent(AdminActivity.this,AddConferenceActivity.class);                
                startActivity(intent);            
            }        
        });
        adminConferenceAdapter= new AdminConferenceAdapter(AdminActivity.this,arrayList);        
        id_dashboard_recycler.setAdapter(adminConferenceAdapter);    
    }    
    
    private void getData(ArrayList<ConferenceDataModel> arrayList) {        
        
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();        
        DatabaseReference databaseReference=firebaseDatabase.getReference("Events");        
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override            
            public void onDataChange(@NonNull DataSnapshot snapshot) {                
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){                    
                    ConferenceDataModel model=dataSnapshot.getValue(ConferenceDataModel.class);                  
                    arrayList.add(model);                
                } 

            adminConferenceAdapter.notifyDataSetChanged();

            }            
            
            @Override            
            public void onCancelled(@NonNull DatabaseError error) {            

            } 

        });    
    
    }
    
}















