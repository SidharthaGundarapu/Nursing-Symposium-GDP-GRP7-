package com.nwmissouri.edu.conferencescheduler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nwmissouri.edu.conferencescheduler.adapter.SpeakersAdapter;
import com.nwmissouri.edu.conferencescheduler.model.Speaker;
import com.nwmissouri.edu.conferencescheduler.utils.FirebaseUtilsManager;

public class DialogUtils {

    public void showEditNameDialog(Context context, String currentName, OnUpdateListener onUpdate) {
        EditText editTextName = new EditText(context);
        editTextName.setHint("Enter Name");
        editTextName.setText(currentName);
        // editTextName.setTextColor(ContextCompat.getColor(this, R.color.gray))
        editTextName.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextName.setMaxLines(1);
        editTextName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Name");
        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedName = editTextName.getText().toString();
            onUpdate.onUpdate(updatedName);
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(editTextName);
        builder.show();
    }

    public void showConfirmationDialog(Context context, OnYesListener onYes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onYes.onYes();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public interface OnUpdateListener {
        void onUpdate(String updatedName);
    }

    public interface OnYesListener {
        void onYes();
    }

    public void showSpeakerList(Context ct, SpeakersAdapter.OnSpeakerItemClick listener) {
        Dialog dialog = new Dialog(ct);
        dialog.setContentView(R.layout.activity_speakers);

        RecyclerView recyclerView = dialog.findViewById(R.id.rvSpeakers);
        dialog.findViewById(R.id.btAdd).setVisibility(View.GONE);

        SpeakersAdapter adapter = new SpeakersAdapter();
        adapter.setOnItemClickListener(data -> {
            listener.onClick(data);
            dialog.dismiss();
        });
        recyclerView.setAdapter(adapter);

        FirebaseUtilsManager.listenSpeakersListUpdate(data -> {
            adapter.submitList(data);
        });

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.9);
            layoutParams.height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.7);
            window.setAttributes(layoutParams);
        }
        dialog.show();
    }


}
