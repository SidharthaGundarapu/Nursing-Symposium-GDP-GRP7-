package nwmsu.android.conferenceapp.ui.staffmenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nwmsu.android.conferenceapp.databinding.FragmentStaffMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}