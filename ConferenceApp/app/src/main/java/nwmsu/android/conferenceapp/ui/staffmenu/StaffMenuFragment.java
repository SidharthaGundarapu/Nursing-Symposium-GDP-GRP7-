package nwmsu.android.conferenceapp.ui.staffmenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nwmsu.android.conferenceapp.MainActivity;
import nwmsu.android.conferenceapp.R;
import nwmsu.android.conferenceapp.databinding.FragmentStaffMenuBinding;

public class StaffMenuFragment extends Fragment {

    private FragmentStaffMenuBinding binding;

    public StaffMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StaffMenuFragment newInstance() {
        StaffMenuFragment fragment = new StaffMenuFragment();
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
        StaffMenuViewModel staffMenuViewModel =
                new ViewModelProvider(this).get(StaffMenuViewModel.class);

        binding = FragmentStaffMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStaffmenu;
        staffMenuViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        /**
         * CREATE BUTTON TO SWAP ACTIVITIES
         *  - Swaps to the User Activity set
         */
        Button userSwapBTN = (Button) root.findViewById( R.id.userSwapBTN);
        userSwapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Swap to the user activity
                Intent swapIntent = new Intent(getActivity(), MainActivity.class);
                startActivity( swapIntent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}