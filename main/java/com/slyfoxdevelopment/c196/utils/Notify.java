package com.slyfoxdevelopment.c196.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notify extends Application {

    public static final String CHANNEL_1_ID = "";
    public static final String CHANNEL_2_ID = "";

    @Override
    public void onCreate(){
        super.onCreate();
        
        createNotificationChannels();
        
        
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Look at me", NotificationManager.IMPORTANCE_HIGH);

            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Look at me again", NotificationManager.IMPORTANCE_LOW);

            channel2.setDescription("This is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
