package com.example.noteapp.firebase;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.noteapp.NewsDetailsActivity;
import com.example.noteapp.R;
import com.example.noteapp.model.News;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static String CHANNEL_ID = "NOTEAPP_NOTIFICIATION";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("New token", token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);


        if (message.getNotification() != null) {
            Log.d("FCM title:", message.getNotification().getTitle());
            Log.d("FCM body:", message.getNotification().getBody());
        }

        if (message.getData() != null) {
            String title = message.getData().get("title");
            String content = message.getData().get("body");


            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            CharSequence name = "Note app";
            String description = "Note app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.

            News news = new Gson().fromJson(message.getData().get("news"), News.class);
            Intent intent = new Intent(this, NewsDetailsActivity.class);
            intent.putExtra("news", news);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);


            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.baseline_share_24)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSound(sound)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(100, builder.build());


        }


    }
}

