package com.main.travelApp.ui.components.checkmark;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.main.travelApp.R;

public class SuccessCheckmark extends LinearLayout {
    private final Context mContext;

    public SuccessCheckmark(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public SuccessCheckmark(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_success_checkmark, this, true);


        ImageView imgDone = view.findViewById(R.id.img_done);
        Drawable drawable = imgDone.getDrawable();
        if(drawable instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) drawable;
            animatedVectorDrawableCompat.start();
        } else if (drawable instanceof  AnimatedVectorDrawable) {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
            animatedVectorDrawable.start();
        }
    }
}
