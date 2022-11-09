package nwmsu.android.conferenceapp.ui.sessions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import nwmsu.android.conferenceapp.EventRecyclerViewAdapter;
import nwmsu.android.conferenceapp.EventRecyclerData;
import nwmsu.android.conferenceapp.databinding.FragmentSessionsBinding;

public class SessionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<EventRecyclerData> recyclerDataArrayList;

    private FragmentSessionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SessionsViewModel sessionsViewModel =
                new ViewModelProvider(this).get(SessionsViewModel.class);

        binding = FragmentSessionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textSessions;
        //sessionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // populate our recycler
        recyclerView = binding.rvSessions;
        // Get data here normally
        populateStaticRecyclerData();

        // added data from arraylist to adapter class.
        EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter(recyclerDataArrayList,getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    private void populateStaticRecyclerData() {
        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // (int tIcon, String tName, String tDescription, String tTime)
        // added data to array list
        recyclerDataArrayList.add(new EventRecyclerData(
                1, "Event 1", "Heart stuff here", "08:00 - 09:00 AM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                2, "Event 2", "Learn about cardiovascular diseases", "09:00 - 10:00 AM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                3, "Event 3", "Electric Therapy and you", "10:00 - 11:00AM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                4, "Event 4", "How to diagnose diseases early", "01:00 - 02:00 PM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                5, "Event 5", "Researching the heart", "03:00 - 04:00 PM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                6, "Event 6", "How to manage your employees", "03:00 - 04:00 PM"));
        recyclerDataArrayList.add(new EventRecyclerData(
                7, "Event 7", "Capping it all off, a description about how to manage "
                + "your time wisely while putting everyone you learned into practice.  The rest of this description "
                + "is just to test how long I can make the fragment.  We have to probably put in a description limit "
                + "in the display. Like we max 97 characters and concat a ... to the end", "04:00 - 05:00 PM"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}