package com.example.nursingsymposium.admin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nursingsymposium.ConferenceAdapter;
import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.DashBoardActivity;
import com.example.nursingsymposium.R;

import java.util.ArrayList;

public class AdminConferenceAdapter  extends RecyclerView.Adapter<AdminConferenceAdapter.ViewHolder>{
    AdminActivity activity; 
    ArrayList<ConferenceDataModel> list; 
    
    public AdminConferenceAdapter(AdminActivity dashBoardActivity, ArrayList<ConferenceDataModel> arrayList) { 
        this.activity=dashBoardActivity;
        this.list=arrayList;
    }
    
    @NonNull
    @Override
    
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    
    @Override
    
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ConferenceDataModel myListData = list.get(position); 
        holder.listname.setText(myListData.getName()); 
        holder.listtime.setText(myListData.getTime()); 
        holder.listevent.setText(myListData.getEventName()); 
        holder.addtoschedule.setVisibility(View.GONE); 
    /*  holder.layout_click.setOnClickListener(new View.OnClickListener() {            
        @Override            
        public void onClick(View view) {                
            Intent intent = new Intent(activity, ConferenceSchedulerActivity.class);                
            activity.startActivity(intent);            
        }        
    });        
    */
    holder.addtoschedule.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(activity, "Added to My Schedule", Toast.LENGTH_SHORT).show();
        }
    });
}









































































