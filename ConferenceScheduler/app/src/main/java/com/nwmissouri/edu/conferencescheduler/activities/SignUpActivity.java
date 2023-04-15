package com.nwmissouri.edu.conferencescheduler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySignupBinding;
import com.nwmissouri.edu.conferencescheduler.model.SignupModel;
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

        SignupModel signup = new SignupModel();
        signup.setUsername(name);
        signup.setEmail(email);
        signup.setPassword(password);
        signup.setConfirmPassword(confirmPassword);

        String errorMessage = checkIfFieldsEmpty(signup);
        if (!errorMessage.isEmpty()) {
            ToastUtils.showToast(this, errorMessage);
            return;
        }

        errorMessage = checkIfFieldsValid(signup);
        if (!errorMessage.isEmpty()) {
            ToastUtils.showToast(this, errorMessage);
            return;
        }

        String checkValid = isValidPassword(signup.getPassword());
        if (!checkValid.isEmpty()) {
            ToastUtils.showToast(this, checkValid);
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

    public static String checkIfFieldsEmpty(SignupModel signup) {
        String errorMsg = "";
        String prompt = "";
        if (signup.getUsername().isEmpty()) {
            errorMsg += "Please enter name";
        }
        if (signup.getEmail().isEmpty()) {
            prompt = "Please enter email";
            errorMsg += (errorMsg.isEmpty()) ? prompt : "\n"+prompt;
        }
        if (signup.getPassword().isEmpty()) {
            prompt = "Please enter password";
            errorMsg += (errorMsg.isEmpty()) ? prompt : "\n"+prompt;
        }
        if (signup.getConfirmPassword().isEmpty()) {
            prompt = "Please confirm password";
            errorMsg += (errorMsg.isEmpty()) ? prompt : "\n"+prompt;
        }
        return errorMsg;
    }

    public static String checkIfFieldsValid(SignupModel signup) {
        String errorMsg = "";
        String prompt = "";
        if (!isValidEmail(signup.getEmail())) {
            errorMsg += "Please enter valid email";
        }

        if (!TextUtils.equals(signup.getConfirmPassword(), signup.getPassword())) {
            prompt = "Password does not match";
            errorMsg += (errorMsg.isEmpty()) ? prompt : "\n"+prompt;
        }
        return errorMsg;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static String isValidPassword(String password) {
        String errorMessage = "";
        String prompt = "";
        if (password == null || password.length() < 4 || password.length() > 10) {
            errorMessage += "Password length should be between 4 and 10";
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
            prompt = "Password should contain at least one uppercase letter";
            errorMessage += (errorMessage.isEmpty()) ? prompt : "\n"+prompt;
        }

        if (!hasSpecialChar) {
            prompt = "Password should contain at least one special character";
            errorMessage += (errorMessage.isEmpty()) ? prompt : "\n"+prompt;
        }
        return errorMessage;
    }
}

