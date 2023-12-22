package com.main.travelApp.ui.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.main.travelApp.R;

public class MyDialog extends DialogFragment {
    private Context context;
    private LayoutInflater layoutInflater;
    private int layout;
    private Handler handler;

    public MyDialog(Context context, LayoutInflater layoutInflater, int layout, Handler handler){
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.layout = layout;
        this.handler = handler;
    }
    public interface Handler {
        void handle(AlertDialog dialog, View contentView);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = layoutInflater.inflate(layout, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        handler.handle(dialog, dialogView);

        return dialog;
    }
}
