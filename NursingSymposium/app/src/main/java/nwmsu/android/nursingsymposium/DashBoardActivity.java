

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

  
}