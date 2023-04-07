package com.nwmissouri.edu.conferencescheduler.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialogUtils {
    private static ProgressDialog sProgressDialog;

    public static void showLoadingDialog(Context context, String message) {
        if (sProgressDialog == null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setMessage(message);
            sProgressDialog.setCancelable(false);
            sProgressDialog.show();
        }
    }

    public static void hideLoadingDialog() {
        if (sProgressDialog != null) {
            sProgressDialog.dismiss();
            sProgressDialog = null;
        }
    }
}
