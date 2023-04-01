package com.nwmissouri.edu.conferencescheduler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySignupBinding;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btSignUp.setOnClickListener(v -> signUp());
        binding.ibBack.setOnClickListener(v -> onBackPressed());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void signUp() {
        String name = Objects.requireNonNull(binding.etName.getText()).toString();
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
        String confirmPassword = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(this, "Please enter name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showToast(this, "Please enter email");
            return;
        }
        if (!isValidEmail(email)) {
            ToastUtils.showToast(this, "Please enter valid email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(this, "Please enter password");
            return;
        }

        if (!isValidPassword(password, this)) {
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtils.showToast(this, "Please confirm password");
            return;
        }
        if (!TextUtils.equals(confirmPassword, password)) {
            ToastUtils.showToast(this, "Password does not match");
            return;
        }
        FirebaseUtilsManager.createAccount(this, name, email.toLowerCase(), password, Constants.USER_TYPE_STUDENT, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showToast(SignUpActivity.this, "Account successfully created");
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(SignUpActivity.this, msg);
            }
        });
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isValidPassword(String password, Context context) {
        if (password == null || password.length() < 4 || password.length() > 10) {
            Toast.makeText(context, "Password length should be between 4 and 10", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean hasUppercase = false;
        boolean hasSpecialChar = false;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase) {
            Toast.makeText(context, "Password should contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!hasSpecialChar) {
            Toast.makeText(context, "Password should contain at least one special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

