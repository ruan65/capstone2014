package com.ruan.managecarenew.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.activities.SettingsActivity;

public class ReScheduleAlarmsAfterReboot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(context.getString(R.string.intent_action_reboot))) {

            intent = new Intent(context, SettingsActivity.class);
            intent.putExtra(context.getString(R.string.set_schedule), true);

            context.startActivity(intent);
        }
    }
}
