package com.nwmissouri.edu.conferencescheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivityLoginBinding;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private MySharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = MySharedPreferences.getInstance();

        binding.btContinue.setOnClickListener(v -> login());
        binding.tvSignUp.setOnClickListener(v -> showSignUpActivity());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void showSignUpActivity(){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void login() {
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();

        if (TextUtils.isEmpty(email)) {
            ToastUtils.showToast(this, "Please enter email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(this, "Please enter password");
            return;
        }
        if (TextUtils.equals(email.toLowerCase(), Constants.EMAIL_SUPER_ADMIN)) {
            if (TextUtils.equals(password, Constants.PASSWORD_SUPER_ADMIN)) {
                pref.saveSuperAdminUser();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                ToastUtils.showToast(this, "Please enter valid password");
                return;
            }
            return;
        }
        FirebaseUtilsManager.login(this, email.toLowerCase(), password, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showToast(LoginActivity.this, "Successfully Logged in");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(LoginActivity.this, msg);
            }
        });
    }

}