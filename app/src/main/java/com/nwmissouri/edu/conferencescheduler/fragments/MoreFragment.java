package com.nwmissouri.edu.conferencescheduler.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.adapter.MoreListAdapter;
import com.nwmissouri.edu.conferencescheduler.databinding.FragmentMoreBinding;
import com.nwmissouri.edu.conferencescheduler.model.MoreModel;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;


public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;
    private MySharedPreferences pref;

    private MoreListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater);
        pref = MySharedPreferences.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MoreListAdapter();
        binding.rvMore.setAdapter(adapter);

        List<MoreModel> list = new ArrayList<>();
        list.add(new MoreModel("speakers", getString(R.string.speakers), R.drawable.speakers));
        list.add(new MoreModel("maps", getString(R.string.maps), R.drawable.maps));
        list.add(new MoreModel("sponsors", getString(R.string.sponsors), R.drawable.sponsors));
        list.add(new MoreModel("about", getString(R.string.about), R.drawable.about));

        adapter.submitList(list);

    }


}
