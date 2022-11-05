package nwmsu.android.conferenceapp.ui.tools;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nwmsu.android.conferenceapp.MainActivity;
import nwmsu.android.conferenceapp.R;
import nwmsu.android.conferenceapp.databinding.FragmentToolsBinding;
import nwmsu.android.conferenceapp.ui.tools.ToolsViewModel;

public class ToolsFragment extends Fragment {

    private FragmentToolsBinding binding;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ToolsViewModel toolsViewModel =
                new ViewModelProvider(this).get(ToolsViewModel.class);

        binding = FragmentToolsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTools;
        toolsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button userSwapBTN = (Button) root.findViewById( R.id.userSwapBTN);
        userSwapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Swap to the user actvitiy
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