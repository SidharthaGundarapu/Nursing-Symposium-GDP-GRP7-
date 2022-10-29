package nwmsu.android.conferenceapp.ui.tools;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nwmsu.android.conferenceapp.MainActivity;
import nwmsu.android.conferenceapp.R;
import nwmsu.android.conferenceapp.StaffActivity;

public class ToolsFragment extends Fragment {

    public ToolsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ToolsFragment newInstance() {
        ToolsFragment fragment = new ToolsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        // Find the swap button
        Button userSwapBTN = (Button) getView().findViewById( R.id.userSwapBTN);
        userSwapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Swap to the user actvitiy
                Intent swapIntent = new Intent(getActivity(), MainActivity.class);
                startActivity( swapIntent);
            }
        });
        return view;
    }
}