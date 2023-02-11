package com.example.nursingsymposium.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nursingsymposium.ConferenceAdapter;
import com.example.nursingsymposium.ConferenceDataModel;
import com.example.nursingsymposium.DashBoardActivity;
import com.example.nursingsymposium.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConferenceFragment extends Fragment {

    RecyclerView id_dashboard_recycler;
    ArrayList<ConferenceDataModel> arrayList;
    ConferenceAdapter conferenceAdapter;

    private void getData(ArrayList<ConferenceDataModel> arrayList) {

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Events");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ConferenceDataModel model=dataSnapshot.getValue(ConferenceDataModel.class);
                    ConferenceFragment.this.arrayList.add(model);
                }
                conferenceAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_conference, container, false);
        id_dashboard_recycler=view.findViewById(R.id.id_dashboard_recycler);
        id_dashboard_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList=new ArrayList<>();
        getData(arrayList);
        conferenceAdapter= new ConferenceAdapter((DashBoardActivity) getActivity(),arrayList);
        return view;
    }


}