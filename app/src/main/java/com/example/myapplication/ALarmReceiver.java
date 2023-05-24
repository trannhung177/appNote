package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ALarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Toi trong Receiver","Xin chao");
        Intent myIntent = new Intent(context,Music.class);
        context.startService(myIntent);


        /*String type = intent.getStringExtra("type");
        if ("calendar_reminder".equals(type)) {
            pushNoti(context, intent);
            return;
        }


//        String chuoi_string= intent.getExtras().getString("extra");
//        if (chuoi_string == null)
//            chuoi_string = "";
//        //Log.e("Ban truyen key",chuoi_string);
//
//        Intent myIntent = new Intent(context,Music.class);
//        myIntent.putExtra("extra",chuoi_string);
//        context.startService(myIntent);
    }

    private void pushNoti(Context context, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification pNotification = new NotificationCompat.Builder(context, MyApplication.CHANEL_CHAT_ID)
                .setContentText(intent.getStringExtra("title"))
                .setContentTitle(intent.getStringExtra("content"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager != null) {
            manager.notify(1, pNotification);
        }*/
    }
}
