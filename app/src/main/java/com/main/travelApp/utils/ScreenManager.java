package com.main.travelApp.utils;

import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ScreenManager {
    public static void enableFullScreen(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
