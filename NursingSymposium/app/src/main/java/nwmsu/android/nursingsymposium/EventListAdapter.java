package com.nwmissouri.edu.conferencescheduler.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nwmissouri.edu.conferencescheduler.R;
import com.nwmissouri.edu.conferencescheduler.activities.EventDetailsActivity;
import com.nwmissouri.edu.conferencescheduler.databinding.ListItemEventBinding;
import com.nwmissouri.edu.conferencescheduler.databinding.ListItemHeaderMainBinding;
import com.nwmissouri.edu.conferencescheduler.model.Event;
import com.nwmissouri.edu.conferencescheduler.utils.Constants;
import com.nwmissouri.edu.conferencescheduler.utils.MySharedPreferences;
import com.nwmissouri.edu.conferencescheduler.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventListAdapter extends ListAdapter<Object, RecyclerView.ViewHolder> {
    private static final int EVENT_VIEW_TYPE = 1;
    private static final int HEADER_VIEW_TYPE = 2;

    private OnEventListItemClick clickListener;

    public void setOnItemClickListener(OnEventListItemClick listener) {
        this.clickListener = listener;
    }

    public EventListAdapter() {
        super(new DiffUtil.ItemCallback<Object>() {
            @Override
            public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                if (oldItem instanceof Event && newItem instanceof Event) {
                    return ((Event) oldItem).getKey().equals(((Event) newItem).getKey());
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
        if (item instanceof Event) {
            return EVENT_VIEW_TYPE;
        } else {
            return HEADER_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == EVENT_VIEW_TYPE) {
            ListItemEventBinding binding = ListItemEventBinding.inflate(layoutInflater, parent, false);
            return new EventViewHolder(binding);
        } else {

            ListItemHeaderMainBinding binding = ListItemHeaderMainBinding.inflate(layoutInflater, parent, false);
            return new HeaderViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = getItem(position);

        if (holder instanceof EventViewHolder && item instanceof Event) {
            EventViewHolder EventViewHolder = (EventViewHolder) holder;
            Event Event = (Event) item;
            EventViewHolder.bind(Event);
        } else if (holder instanceof HeaderViewHolder && item instanceof String) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            String headerText = (String) item;
            headerViewHolder.bind(headerText);
        }
    }

    public void setEvents(List<Event> events) {

        List<Object> items = new ArrayList<>();
        if (events.isEmpty()) {
            submitList(items);
            return;
        }

        Map<String, List<Event>> groupedMap = events.stream()
                .collect(Collectors.groupingBy(Event::getType));

        for (String key : groupedMap.keySet()) {
            if (!items.contains(key)) {
                items.add(key);
            }
            List<Event> group = groupedMap.get(key);
            for (Event obj : group) {
                items.add(obj);
            }
        }

        submitList(items);
    }

}


