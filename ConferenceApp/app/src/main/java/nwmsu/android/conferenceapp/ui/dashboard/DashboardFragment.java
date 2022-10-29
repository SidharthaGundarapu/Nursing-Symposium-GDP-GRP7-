package nwmsu.android.conferenceapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nwmsu.android.conferenceapp.MainActivity;
import nwmsu.android.conferenceapp.R;
import nwmsu.android.conferenceapp.StaffActivity;
import nwmsu.android.conferenceapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button staffSwapBTN = (Button) getView().findViewById( R.id.staffSwapBTN);
        staffSwapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Swap to the user actvitiy
                Intent swapIntent = new Intent(getActivity(), StaffActivity.class);
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