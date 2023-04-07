package com.nwmissouri.edu.conferencescheduler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nwmissouri.edu.conferencescheduler.databinding.ListItemSpeakerBinding;
import com.nwmissouri.edu.conferencescheduler.model.Speaker;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.squareup.picasso.Picasso;

public class SpeakersAdapter extends ListAdapter<Speaker, SpeakersAdapter.ViewHolder> {

    private OnSpeakerItemClick clickListener;

    public void setOnItemClickListener(OnSpeakerItemClick listener) {
        this.clickListener = listener;
    }

    public SpeakersAdapter() {
        super(new DiffUtil.ItemCallback<Speaker>() {
            @Override
            public boolean areItemsTheSame(@NonNull Speaker oldItem, @NonNull Speaker newItem) {
                // Check if items have the same ID
                return oldItem.getKey().equals(newItem.getKey());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Speaker oldItem, @NonNull Speaker newItem) {
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
        ListItemSpeakerBinding binding = ListItemSpeakerBinding.inflate(inflater, parent, false);

        // Create and return a new ViewHolder
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the Speaker at the current position in the list
        Speaker Speaker = getItem(position);

        // Bind the Speaker data to the ViewHolder
        holder.bind(Speaker);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemSpeakerBinding binding;
        private final MySharedPreferences pref;

        public ViewHolder(ListItemSpeakerBinding binding) {
            super(binding.getRoot());
            this.pref = MySharedPreferences.getInstance();
            this.binding = binding;
        }

        public void bind(Speaker speaker) {
            binding.tvName.setText(speaker.getName());
            Picasso.get().load(speaker.getImageUrl()).resize(500, 300)
                    .into(binding.iv);

            binding.getRoot().setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(speaker);
                }
            });
        }
    }

    public interface OnSpeakerItemClick {
        void onClick(Speaker data);
    }
}





