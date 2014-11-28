package com.ruan.managecarenew.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.broadcast_receivers.FulfillCheckInReceiver;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends PreferenceActivity {

    SharedPreferences prefs;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ctx = getApplicationContext();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getPreferenceManager()
                .findPreference(getString(R.string.pref_key_enable_alerts))
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference preference, Object checked) {
                        if ((Boolean) checked) {
                            startAlerts();
                        } else {
                            stopAlerts();
                        }
                        return true;
                    }
                });

        getPreferenceManager().findPreference(getString(R.string.alerts))
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        String text = prefs.getString(getString(R.string.alerts),
                                getString(R.string.empty_str));
                        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set Check-In alerts schedule if device has been rebooted

        Bundle extras = getIntent().getExtras();

        boolean reschedule =
                extras != null
                ? extras.getBoolean(getString(R.string.set_schedule))
                : false;

        boolean isDoctor =
                extras != null
                        ? extras.getBoolean(getString(R.string.is_doctor))
                        : false;

        String action = getIntent().getAction();

        if (action != null && (
                action.equals(getString(R.string.pref_key_enable_alerts)) || reschedule
        )) {
            startAlerts();
            finish();
        }

        if (isDoctor) {
            stopAlerts();
            finish();
        }
    }

    public void startAlerts() {

        int first = prefs.getInt(getString(R.string.checkin_first), 600); // minutes
        int last = prefs.getInt(getString(R.string.checking_last), 1320);

        int checkins_per_day = Integer.parseInt(prefs.getString(
                getString(R.string.checkins_per_day),
                getString(R.string.settings_default_checkins_per_day)));

        int interval = (last - first) / checkins_per_day;

        Intent intent = new Intent(this, FulfillCheckInReceiver.class);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, first / 60);
        calendar.set(Calendar.MINUTE, first % 60);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < checkins_per_day; i++) {

            long triggerAtMillis = calendar.getTimeInMillis() + i * interval * 60 * 1000;

            String time = getString(R.string.checkin_time) + new Date(triggerAtMillis);
            builder.append(time + getString(R.string.new_line_string));

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    triggerAtMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        prefs.edit().putString(getString(R.string.alerts), builder.toString()).commit();
    }

    public void stopAlerts() {

        if (alarmManager != null) {

            int checkins_per_day = Integer.parseInt(
                    prefs.getString(getString(R.string.checkins_per_day),
                            getString(R.string.settings_max_checkins_per_day)));

            Intent intent = new Intent(this, FulfillCheckInReceiver.class);

            for (int i = 0; i < checkins_per_day; i++) {

                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);

                alarmManager.cancel(pendingIntent);
            }
        }
        finish();
    }
}
