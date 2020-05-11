package org.kendar.kendarnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Ashish.kapoor on 04.09.2017.
 * Modified by Kendar on 2020.05.04
 */

public class MyFirebaseMessagingService
        extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "MyMessagingService";

    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String strTitle = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Log.d(TAG, "onMessageReceived: Message Received: \n" +
                "Title: " + strTitle + "\n" +
                "Message: " + message);

        sendNotification(strTitle, message, remoteMessage.getData());
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title, String messageBody, Map<String, String> data) {

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(
                    MyConstants.CHANNEL_ID,
                    MyConstants.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(MyConstants.CHANNEL_DESCRIPTION);
            //mChannel.enableLights(true);
            //mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MyConstants.STR_INCIDENT_NO, data.get(MyConstants.STR_INCIDENT_NO));
        intent.putExtra(MyConstants.STR_DESC, data.get(MyConstants.STR_DESC));
        intent.putExtra(MyConstants.STR_SHORT_DESC, data.get(MyConstants.STR_SHORT_DESC));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri
                (RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(this, MyConstants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource
                        (getResources(), R.mipmap.ic_launcher));

        notificationManager.notify(0, notificationBuilder.build());
    }
}