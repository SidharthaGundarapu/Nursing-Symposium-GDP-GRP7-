package com.nwmissouri.edu.conferencescheduler.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.nwmissouri.edu.conferencescheduler.DialogUtils;
import com.nwmissouri.edu.conferencescheduler.databinding.FragmentProfileBinding;
import com.nwmissouri.edu.conferencescheduler.model.UserModel;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MySharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        pref = MySharedPreferences.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserModel user = pref.getUser();
        DialogUtils dialogUtils = new DialogUtils();

        binding.etName.setText(user.getName());
        binding.etEmail.setText(user.getEmail());
        binding.tvUserType.setText(user.getUserType());

        if (TextUtils.equals(user.getUserType(), Constants.USER_TYPE_SUPER_ADMIN)) {
            binding.tfEmail.setEnabled(false);
            binding.tfName.setEnabled(false);
        }
        if (TextUtils.equals(user.getUserType(), Constants.USER_TYPE_ADMIN) || TextUtils.equals(user.getUserType(), Constants.USER_TYPE_STUDENT)) {
            binding.tfEmail.setEnabled(false);
            binding.tfName.getEditText().setEnabled(false);
            binding.tfName.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
            binding.tfName.setEndIconOnClickListener(v -> dialogUtils.showEditNameDialog(requireContext(), pref.getUser().getName(), (DialogUtils.OnUpdateListener) updatedName -> {
                if (TextUtils.isEmpty(updatedName)) {
                    ToastUtils.showToast(requireContext(), "Please enter name");
                    return;
                }
                FirebaseUtilsManager.updateName(requireContext(), updatedName, new FirebaseUtilsManager.OnOpListener() {
                    @Override
                    public void onSuccess(String msg) {
                        binding.etName.setText(pref.getUser().getName());
                    }
                    @Override
                    public void onFailure(String msg) {
                    }
                });
            }));
        }

        binding.btLogOut.setOnClickListener(v -> {
            dialogUtils.showConfirmationDialog(requireContext(), () -> {
                pref.logOut();
                restartApp();
            });
        });
    }

    public void restartApp() {
        Intent intent = requireContext().getPackageManager().getLaunchIntentForPackage(requireContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
