package com.example.nursingsymposium.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nursingsymposium.ConferenceAdapter;
import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.DashBoardActivity;
import com.example.nursingsymposium.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SchduleFragment extends Fragment {

    RecyclerView id_schedulelist;
    ArrayList<ConferenceDataModel> arrayList;
    SchduleFragmentAdapter conferenceAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(arrayList!=null){
            if(arrayList.size()>=0){
                id_schedulelist.setAdapter(conferenceAdapter);
            }
        }
    }

}

