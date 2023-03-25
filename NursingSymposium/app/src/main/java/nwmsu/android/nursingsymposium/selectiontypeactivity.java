package com.nwmissouri.edu.conferencescheduler.adapter;

import android.content.Intent;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.DiffUtil;

import androidx.recyclerview.widget.ListAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.nwmissouri.edu.conferencescheduler.databinding.ListItemHeaderBinding;

import com.nwmissouri.edu.conferencescheduler.databinding.ListItemSponsorBinding;

import com.nwmissouri.edu.conferencescheduler.model.Sponsor;

import com.nwmissouri.edu.conferencescheduler.utils.Constants;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.Collections;

import java.util.LinkedHashSet;

import java.util.List;

import java.util.Map;

import java.util.Set;

import java.util.stream.Collectors;
public class SponsorAdapter extends ListAdapter<Object, RecyclerView.ViewHolder> {

        private static final int SPONSOR_VIEW_TYPE = 1;
    
        private static final int HEADER_VIEW_TYPE = 2;
    
    
    
    
        public SponsorAdapter() {
    
            super(new DiffUtil.ItemCallback<Object>() {
    
                @Override
    
                public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
    
                    if (oldItem instanceof Sponsor && newItem instanceof Sponsor) {
    
                        return ((Sponsor) oldItem).getKey().equals(((Sponsor) newItem).getKey());
    
                    } else if (oldItem instanceof String && newItem instanceof String) {
    
                        return oldItem.equals(newItem);
    
                    } else {
    
                        return false;
    
                    }
    
                }
    
    
    
    
                @Override
    
                public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
    
                    return areItemsTheSame(oldItem, newItem);
    
                }
    
            });
    
        }
    
    
    
    
        @Override
    
        public int getItemViewType(int position) {
    
            Object item = getItem(position);
    
            if (item instanceof Sponsor) {
    
                return SPONSOR_VIEW_TYPE;
    
            } else {
    
                return HEADER_VIEW_TYPE;
    
            }
    
        }
    
    
    
    
        @NonNull
    
        @Override
    
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    
    
    
    
            if (viewType == SPONSOR_VIEW_TYPE) {
    
                ListItemSponsorBinding binding = ListItemSponsorBinding.inflate(layoutInflater, parent, false);
    
                return new SponsorViewHolder(binding);
    
            } else {
    
                ListItemHeaderBinding binding = ListItemHeaderBinding.inflate(layoutInflater, parent, false);
    
                return new HeaderViewHolder(binding);
    
            }
    
    
    
    
        }
    
    
    
    
        @Override
    
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    
            Object item = getItem(position);
    
    
    
    
            if (holder instanceof SponsorViewHolder && item instanceof Sponsor) {
    
                SponsorViewHolder sponsorViewHolder = (SponsorViewHolder) holder;
    
                Sponsor sponsor = (Sponsor) item;
    
                sponsorViewHolder.bind(sponsor);
    
            } else if (holder instanceof HeaderViewHolder && item instanceof String) {
    
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
    
                String headerText = (String) item;
    
                headerViewHolder.bind(headerText);
    
            }
    
        }
     
        public void setSponsors(List<Sponsor> sponsors) {
    
            List<Object> items = new ArrayList<>();
    
            Map<String, List<Sponsor>> groupedMap = sponsors.stream()
    
                    .collect(Collectors.groupingBy(Sponsor::getType));
    
            Set<String> mySet = groupedMap.keySet();
    
            List<String> sortedType = new ArrayList<>(mySet);
    
            Collections.sort(sortedType);
    
        Set<String> sortedSet = new LinkedHashSet<>(sortedType);

            for (String key : sortedSet) {
    
                if (!items.contains(key)) {
    
                    items.add(key);
    
                }
    
                List<Sponsor> group = groupedMap.get(key);
    
                for (Sponsor obj : group) {
    
                    items.add(obj);
    
                }
    
            }
    
    
    
    
            submitList(items);
    
        }
    
    
    
    
        static class SponsorViewHolder extends RecyclerView.ViewHolder {
    
            private ListItemSponsorBinding binding;
    
    
    
    
            public SponsorViewHolder(ListItemSponsorBinding binding) {
    
                super(binding.getRoot());
    
                this.binding = binding;
    
            }
    
    
    
    
            public void bind(Sponsor sponsor) {
    
                binding.tvName.setText(sponsor.getName());
    
                binding.tvDetails.setText(sponsor.getDetails());
    
                Picasso.get().load(sponsor.getImageUrl()).resize(300, 200).into(binding.iv);
    
                binding.getRoot().setOnClickListener(v -> {
    
                    Intent i = new Intent(binding.getRoot().getContext(), SponsorDetailsActivity.class);
    
                    i.putExtra(Constants.SPONSOR, sponsor);
    
                    binding.getRoot().getContext().startActivity(i);
    
                });
    
            }
    
        }
    
    
    
    
        static class HeaderViewHolder extends RecyclerView.ViewHolder {
    
            private ListItemHeaderBinding binding;
    
    
    
    
            public HeaderViewHolder(ListItemHeaderBinding binding) {
    
                super(binding.getRoot());
    
                this.binding = binding;
    
            }
    
    
    
    
            public void bind(String headerText) {
    
                binding.tvName.setText(headerText);
    
            }
    
        }
    
    }
