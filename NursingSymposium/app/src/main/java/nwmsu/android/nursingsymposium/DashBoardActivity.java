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
 BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }

    private void getData() {
        arrayList=new ArrayList<>();
        arrayList.add(new ConferenceModel("Event1","CooldenHall","5:40"));
        arrayList.add(new ConferenceModel("Event2","valkcenter","2:20"));
        arrayList.add(new ConferenceModel("Event3","Admindepartment","6:40"));
        arrayList.add(new ConferenceModel("Event4","StudentUnion","3:20"));
    }
//private void replacefragment(Fragment fragment) {
      //  FragmentManager fm = getFragmentManager();
      //  FragmentTransaction fragmentTransaction = fm.beginTransaction();
      //  fragmentTransaction.replace(R.id.id_frameLayout, fragment);
     //   fragmentTransaction.commit();
   // }
}