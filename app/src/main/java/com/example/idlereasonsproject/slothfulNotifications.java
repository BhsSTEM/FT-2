package com.example.idlereasonsproject;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.Manifest;

import com.example.idlereasonsproject.FBDatabase.ReportObject;

public class slothfulNotifications {
    //id for notifications is "Slothful Notifications
    //creates a notification for a sent in report object
    //SuppressLint because Android Studio doesn't recognize my way of checking if notifications are enabled, and wants me to use one which doesn't work
    @SuppressLint("MissingPermission")
    public static void idleReportNotifications(Context context, Activity activity, ReportObject report){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Slothful Notifications");
        builder.setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("Idle Report").setContentText(report.reportText()).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true);
        Log.i("Noti", "Builder varibale set");
        requestNotificationPerms(context, activity);
        // notificationId is a unique int for each notification that you must define.
        notificationManagerCompat.notify(3, builder.build());
        Log.i("Noti", "Notification sent");
    }

    public static void requestNotificationPerms(Context context, Activity activity) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (!notificationManagerCompat.areNotificationsEnabled())
        {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                        int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.w("Noti", "Permissions not given yet");
            requestPermissions(activity,new String[]{Manifest.permission.POST_NOTIFICATIONS},3 );
        }
    }
}
