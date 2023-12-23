package com.main.travelApp.ui.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.main.travelApp.R;
import com.main.travelApp.callbacks.BottomSheetActionHandler;

public class BottomSheet {
    private static Dialog dialog;
    private final View contentView;

    private GestureDetector gestureDetector;
    private float initialY;
    private float downY;

    @SuppressLint("ClickableViewAccessibility")
    public BottomSheet(Context context, LayoutInflater layoutInflater, int contentLayout, String heading) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.component_bottom_sheet);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        if (heading != null) {
            ((TextView) dialog.findViewById(R.id.txt_heading)).setText(heading);
        }

        gestureDetector = new GestureDetector(context, new GestureListener());
        dialog.getWindow().getDecorView().setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });

        dialog.findViewById(R.id.cancelButton).setOnClickListener(view -> dialog.dismiss());

        ViewGroup root = dialog.findViewById(R.id.layout_content_wrapper);
        this.contentView = layoutInflater.inflate(contentLayout, null);
        root.addView(contentView);
    }

    public void build(BottomSheetActionHandler actionHandler) {
        actionHandler.action(dialog, contentView);
    }

    public void show() {
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        private static final int MINIMUM_HEIGHT = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            initialY = dialog.getWindow().getAttributes().height;
            downY = e.getRawY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float deltaY = downY - e2.getRawY();
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.height = (int) (initialY + deltaY);
            dialog.getWindow().setAttributes(layoutParams);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY < 0 && dialog.getWindow().getAttributes().height > MINIMUM_HEIGHT) {
                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                    layoutParams.height = MINIMUM_HEIGHT;
                    dialog.getWindow().setAttributes(layoutParams);
                } else if (diffY > 0) {
                    dialog.dismiss();
                }
            }
            return true;
        }


    }
}
