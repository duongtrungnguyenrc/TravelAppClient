package com.main.travelApp.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class LinearLayoutManagerUtil {
    public static LinearLayoutManager disabledScrollManager(Context context, int orientation){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(orientation);
        return layoutManager;
    }
}
