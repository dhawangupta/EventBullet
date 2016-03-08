package com.placediscovery.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.R;
import com.placediscovery.ui.activity.EventsContentActivity;

/**
 * Created by Dhawan Gupta on 07-03-2016.
 */
public class AlaramReceiver extends BroadcastReceiver {

    int MID;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Event event = (Event)intent.getExtras().getSerializable("event");

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, EventsContentActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("event",event);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.logo)
                .setContentTitle(event.getName())
                .setContentText("It is going to start in 1 hour").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;

    }

}