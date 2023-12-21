package com.main.travelApp.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.main.travelApp.R;
import com.main.travelApp.callbacks.BottomSheetActionHandler;

public class BottomSheet {
    private static Dialog dialog;
    private final View contentView;

    public BottomSheet(Context context, LayoutInflater layoutInflater, int contentLayout, String heading) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.component_bottom_sheet);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        if(heading != null) {
            ((TextView) dialog.findViewById(R.id.txt_heading)).setText(heading);
        }

        dialog.findViewById(R.id.cancelButton).setOnClickListener(view -> dialog.dismiss());

        ViewGroup root = dialog.findViewById(R.id.layout_content_wrapper);
        this.contentView = layoutInflater.inflate(contentLayout, null);
        root.addView(contentView);
    }

    public void build(BottomSheetActionHandler actionHandler) {
        actionHandler.action(dialog, contentView);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
