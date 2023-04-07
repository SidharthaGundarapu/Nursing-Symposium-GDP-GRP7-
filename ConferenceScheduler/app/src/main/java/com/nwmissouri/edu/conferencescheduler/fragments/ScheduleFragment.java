package com.nwmissouri.edu.conferencescheduler.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nwmissouri.edu.conferencescheduler.activities.AddConferenceActivity;
import com.nwmissouri.edu.conferencescheduler.activities.AddEventActivity;
import com.nwmissouri.edu.conferencescheduler.adapter.EventListAdapter;
import com.nwmissouri.edu.conferencescheduler.databinding.FragmentScheduleBinding;
import com.nwmissouri.edu.conferencescheduler.model.Conference;
import com.nwmissouri.edu.conferencescheduler.model.Event;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.ToastUtils;
import com.squareup.picasso.Picasso;


public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private MySharedPreferences pref;
    private EventListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater);
        pref = MySharedPreferences.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new EventListAdapter();
        binding.rvEvents.setAdapter(adapter);
        adapter.setOnItemClickListener(event -> removeEvent(requireContext(), event));
        setData();
    }

    private void setData() {
        if (TextUtils.equals(pref.userType(), Constants.USER_TYPE_ADMIN)
                || TextUtils.equals(pref.userType(), Constants.USER_TYPE_SUPER_ADMIN)) {
            binding.btCreate.setVisibility(View.VISIBLE);
            binding.btCreate.setOnClickListener(v -> {
                startActivity(new Intent(requireContext(), AddConferenceActivity.class));
            });
        } else {
            binding.btCreate.setVisibility(View.GONE);
        }

        FirebaseUtilsManager.listenConferenceListUpdate(data -> {
            if (data.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.llBody.setVisibility(View.GONE);
                return;
            }
            Conference conference = data.get(0);
            binding.llNoData.setVisibility(View.GONE);
            binding.llBody.setVisibility(View.VISIBLE);
            binding.layoutConference.getRoot().setVisibility(View.VISIBLE);
            Picasso.get().load(conference.getImageUrl()).into(binding.layoutConference.iv);
            binding.layoutConference.tvName.setText(conference.getName());

            binding.layoutConference.getRoot().setOnClickListener(v -> {
                if (!TextUtils.equals(pref.userType(), Constants.USER_TYPE_STUDENT)) {
                    Intent i = new Intent(requireContext(), AddConferenceActivity.class);
                    i.putExtra(Constants.CONFERENCE, conference);
                    startActivity(i);
                }
            });

            if (TextUtils.equals(pref.userType(), Constants.USER_TYPE_ADMIN)) {
                binding.layoutConference.ivAddEvent.setVisibility(View.VISIBLE);
                binding.layoutConference.ivAddEvent.setOnClickListener(v -> {
                    Intent i = new Intent(requireContext(), AddEventActivity.class);
                    i.putExtra(Constants.CONFERENCE_ID, conference.getKey());
                    startActivity(i);
                });
            } else {
                binding.layoutConference.ivAddEvent.setVisibility(View.GONE);
            }

            FirebaseUtilsManager.listenScheduledUpdate(conference.getKey(), events -> {
                adapter.setEvents(events);
            });

        });

    }

    private void removeEvent(Context ct, Event event) {
        FirebaseUtilsManager.removeEvent(ct, event, new FirebaseUtilsManager.OnOpListener() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast(ct, data);
                setData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showToast(ct, msg);
            }
        });
    }

}
