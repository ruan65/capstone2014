package com.ruan.managecarenew.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.ruan.managecarenew.activities.MainActivity;

public class FulfillCheckInReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = new Intent(context.getApplicationContext(), MainActivity.class);

        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(startIntent);

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(1000);

    }
}
