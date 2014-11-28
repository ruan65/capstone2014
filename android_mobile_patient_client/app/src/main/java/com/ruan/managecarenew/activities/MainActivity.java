package com.ruan.managecarenew.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.fragments.LoginDialogFragment;
import com.ruan.managecarenew.fragments.SendMessageDialog;
import com.ruan.managecarenew.helpers.Toaster;


public class MainActivity extends ActionBarActivity {

    SharedPreferences prefs;
    DialogFragment sendMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendMessageDialog = new SendMessageDialog();

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getString(getString(R.string.pref_mrn),
                getString(R.string.empty_str)).startsWith(getString(R.string.dl))) {
            startActivity(new Intent(this, DoctorActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean(getString(R.string.first_time_pref), true)) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(getString(R.string.first_time_pref), false);
            editor.commit();
            startActivity(new Intent(
                    getString(R.string.pref_key_enable_alerts),
                    null, this, SettingsActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.action_reenter:
                prefs.edit().clear().commit();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;

            case R.id.action_help:
                Toaster.toastInfo(this, prefs);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickCheckIn(View view) {

        startActivity(new Intent(this, CheckInActivity.class));
    }

    public void onClickDoctor(View view) {

        sendMessageDialog.show(getSupportFragmentManager(), getString(R.string.empty_str));
    }
}

