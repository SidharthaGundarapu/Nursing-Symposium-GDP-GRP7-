package com.nwmissouri.edu.conferencescheduler.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nwmissouri.edu.conferencescheduler.activities.AboutActivity;
import com.nwmissouri.edu.conferencescheduler.activities.MapActivity;
import com.nwmissouri.edu.conferencescheduler.activities.SpeakersActivity;
import com.nwmissouri.edu.conferencescheduler.activities.SponsorsActivity;
import com.nwmissouri.edu.conferencescheduler.databinding.ActivitySpeakersBinding;
import com.nwmissouri.edu.conferencescheduler.databinding.ListItemMoreBinding;
import com.nwmissouri.edu.conferencescheduler.model.MoreModel;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;

public class MoreListAdapter extends ListAdapter<MoreModel, MoreListAdapter.ViewHolder> {

    public MoreListAdapter() {
        super(new DiffUtil.ItemCallback<MoreModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull MoreModel oldItem, @NonNull MoreModel newItem) {
                // Check if items have the same ID
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull MoreModel oldItem, @NonNull MoreModel newItem) {
                // Check if items have the same content
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get the LayoutInflater instance from the parent context
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout for each item
        ListItemMoreBinding binding = ListItemMoreBinding.inflate(inflater, parent, false);

        // Create and return a new ViewHolder
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the event at the current position in the list
        MoreModel moreModel = getItem(position);

        // Bind the event data to the ViewHolder
        holder.bind(moreModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemMoreBinding binding;
        private final MySharedPreferences pref;

        public ViewHolder(ListItemMoreBinding binding) {
            super(binding.getRoot());
            this.pref = MySharedPreferences.getInstance();
            this.binding = binding;
        }

        public void bind(MoreModel moreModel) {
            binding.tvName.setText(moreModel.getName());
            binding.iv.setImageResource(moreModel.getDrawableResource());

            Context ct = binding.getRoot().getContext();
            binding.getRoot().setOnClickListener(v -> {
                if (moreModel.getId().equals("speakers")) {
                    ct.startActivity(new Intent(ct, SpeakersActivity.class));
                    return;
                }
                if (moreModel.getId().equals("maps")) {
                    ct.startActivity(new Intent(ct, MapActivity.class));
                    return;
                }
                if (moreModel.getId().equals("sponsors")) {
                    ct.startActivity(new Intent(ct, SponsorsActivity.class));
                    return;
                }
                if (moreModel.getId().equals("about")) {
                    ct.startActivity(new Intent(ct, AboutActivity.class));
                    return;
                }
            });

        }
    }
}





