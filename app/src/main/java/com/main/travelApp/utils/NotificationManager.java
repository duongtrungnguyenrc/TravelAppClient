package com.main.travelApp.utils;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationManager {

    private final Context mContext;
    private final android.app.NotificationManager mNotificationManager;

    public NotificationManager(Context context) {
        mContext = context;
        mNotificationManager = (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(String title, String message, Class<?> targetActivity) {
        int notificationId = 1; // Unique ID for the notification
        String channelId = "channel_id"; // Create a unique channel ID
        Intent intent = new Intent(mContext, targetActivity);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Channel Name";
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(notificationId, builder.build());
    }
}
