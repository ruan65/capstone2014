package com.ruan.managecarenew.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ruan.managecarenew.R;
import com.ruan.managecarenew.activities.DoctorActivity;
import com.ruan.managecarenew.activities.MainActivity;
import com.ruan.managecarenew.activities.PatientInfoGraphicActivity;
import com.ruan.managecarenew.broadcast_receivers.GcmBroadcastReceiver;

public class GcmIntentService extends IntentService {

    public static final int NOTIF_ID = 1;

    private NotificationManager nm;

    NotificationCompat.Builder builder;

    private Handler handler;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    Context ctx;

    SharedPreferences prefs;

    Bundle extras;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        ctx = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging
                    .MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                String title = extras.getString("title");
                String message = extras.getString("message");

                Log.i(getString(R.string.log), "Message received: (" + messageType + ") " +
                        ", title: " + title +
                        ", message: " + message + " intent: ");

                if (prefs.getBoolean(getString(R.string.is_doctor), false)) {

                    sendNotification(getString(R.string.notification_alert_title), title, message);
                    showToast(title);
                } else {
                    sendNotification(title, message, getString(R.string.empty_str));
                    showToast(title + getString(R.string.space) + message);
                }
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void showToast(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendNotification(String title, String msg, String id) {

        nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, prefs.getBoolean(getString(R.string.is_doctor), false)
                ? PatientInfoGraphicActivity.class
                : MainActivity.class);

        intent.putExtra(getString(R.string._id), id);


        String[] words = msg.split("\\s+");

        if (words.length > 1) {
            intent.putExtra(getString(R.string.pref_fname), words[0]);
            intent.putExtra(getString(R.string.pref_lname), words[1]);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 200, 300})
                .setContentText(msg);

        nm.notify(NOTIF_ID, mBuilder.build());
    }

    public void sendNotification(String text) {
        sendNotification(getString(R.string.empty_str), text, getString(R.string.empty_str));
    }
}


















