package com.main.travelApp.utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

public class LayoutManagerUtil {
    public static LinearLayoutManager disabledScrollLinearManager(Context context, int orientation){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(orientation);
        return layoutManager;
    }

    public static GridLayoutManager disabledScrollGridManager(Context context, int spanCount){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        return gridLayoutManager;
    }
}
