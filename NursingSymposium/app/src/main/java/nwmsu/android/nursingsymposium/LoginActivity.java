package nwmsu.android.nursingsymposium;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nwmsu.android.nursingsymposium.R;
import nwmsu.android.nursingsymposium.ui.login.LoginViewModel;
import nwmsu.android.nursingsymposium.ui.login.LoginViewModelFactory;
import nwmsu.android.nursingsymposium.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    TextInputEditText id_username;
    TextInputEditText id_userpassword;
    TextView forgotPassword, register;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        id_userpassword = findViewById(R.id.id_userpassword);
        forgotPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.register);
        id_username = findViewById(R.id.id_username);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = id_username.getText().toString();
                String password = id_userpassword.getText().toString();


                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Loading....");
                        progressDialog.show();
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.cancel();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    } catch (Exception e
                    ) {
                        e.printStackTrace();
                    }

                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}