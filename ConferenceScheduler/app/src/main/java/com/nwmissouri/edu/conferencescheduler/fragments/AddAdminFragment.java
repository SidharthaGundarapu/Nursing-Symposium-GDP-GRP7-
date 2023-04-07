package com.nwmissouri.edu.conferencescheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nwmissouri.edu.conferencescheduler.databinding.FragmentAddAdminBinding;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;

import java.util.Objects;


public class AddAdminFragment extends Fragment {

    private FragmentAddAdminBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddAdminBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btCreate.setOnClickListener(v -> {
            addAdmin();
        });
    }

    private void addAdmin() {
        String name = Objects.requireNonNull(binding.etName.getText()).toString();
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
        String confirmPassword = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(requireContext(), "Please enter name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showToast(requireContext(), "Please enter email");
            return;
        }

        if (!isValidEmail(email)) {
            ToastUtils.showToast(requireContext(), "Please enter valid email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(requireContext(), "Please enter password");
            return;
        }

        if (!isValidPassword(password, requireContext())) {
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtils.showToast(requireContext(), "Please confirm password");
            return;
        }
        if (!TextUtils.equals(confirmPassword, password)) {
            ToastUtils.showToast(requireContext(), "Password does not match");
            return;
        }

        FirebaseUtilsManager.createAccount(requireContext(), name, email.toLowerCase(), password, Constants.USER_TYPE_ADMIN, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showToast(requireContext(), "Account successfully created");
                resetValues();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(requireContext(), msg);
            }
        });

    }

    void resetValues() {
        binding.etName.setText("");
        binding.etEmail.setText("");
        binding.etPassword.setText("");
        binding.etConfirmPassword.setText("");
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
