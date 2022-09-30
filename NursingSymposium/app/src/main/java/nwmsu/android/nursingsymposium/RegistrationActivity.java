package com.example.nursingsymposium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterActivity extends AppCompatActivity {

    TextView login_back;
    TextInputEditText id_name, id_username, id_password, id_confirmpassword;
    Button register_btn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login_back = findViewById(R.id.login_back);
        id_name = findViewById(R.id.id_name);
        id_username = findViewById(R.id.id_username);
        id_password = findViewById(R.id.id_password);
        id_confirmpassword = findViewById(R.id.id_confirmpassword);
        register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = id_name.getText().toString();
                String username = id_username.getText().toString();
                String pass = id_password.getText().toString();
                String confirmpass = id_confirmpassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(confirmpass)) {
                    Toast.makeText(RegisterActivity.this, "Enter confirmpassword", Toast.LENGTH_SHORT).show();
                } else if ((pass.equals(confirmpass))) {
                    try {
                        progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setMessage("Loading....");
                        progressDialog.show();


                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, pass).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.cancel();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();

                                            try {
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("UserDetails").child("Student");
                                                String studentID = FirebaseAuth.getInstance().getUid();
                                                RegistrationModel registrationModel = new RegistrationModel(name, username, pass);
                                                myRef.child(studentID).setValue(registrationModel);

                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            } catch (Exception e) {

                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration UnSuccesfull", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        });

       
    }
}