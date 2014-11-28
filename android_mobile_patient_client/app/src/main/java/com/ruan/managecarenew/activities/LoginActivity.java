package com.ruan.managecarenew.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ruan.managecarenew.R;
import com.ruan.managecarenew.fragments.LoginDialogFragment;
import com.ruan.managecarenew.helpers.NetworkOperations;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginActivity extends ActionBarActivity
        implements LoginDialogFragment.DialogDismissListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    String SENDER_ID = "425226502973";
    String SERVER_API_KEY = "AIzaSyDo_tRwnR263OcNYusSHCeCjarOe1_EKf0"; //0.0.0.0/0

    Context ctx;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = getApplicationContext();

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        String userId = prefs.getString(getString(R.string.pref_mrn),
                getString(R.string.empty_str));

        checkUser(userId);
    }

    public void checkUser(String id) {
        if (id.startsWith(getString(R.string.dl))) {
            startActivity(new Intent(this, DoctorActivity.class));
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(getString(R.string.is_doctor), true);
            finish();
        } else if (id.startsWith(getString(R.string.mr))) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        login();
    }

    private void login() {
        new LoginDialogFragment().show(getSupportFragmentManager(),
                getString(R.string.LOGIN_DIALOG));
    }

    private void registerInBackground() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                String msg;

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(ctx);
                }
                try {
                    regId = gcm.register(SENDER_ID);
                    msg = getString(R.string.msg_device_registred) + regId;

                    storeRegistrationId(ctx, regId);



                } catch (IOException e) {
                    msg = getString(R.string.error) + e.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return sendRegistrationToBackend(ctx);
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        Toast.makeText(ctx, result
                                        ? getString(R.string.success_device_signed)
                                        : getString(R.string.connection_error),
                                Toast.LENGTH_LONG).show();
                    }
                }.execute(null, null, null);
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context ctx, String regId) {

        final SharedPreferences gcmPrefs = getGCMPreferences(ctx);
        int appVersion = getAppVersion(ctx);
        Log.i(getString(R.string.log), getString(R.string.save_reg_id) + appVersion);
        SharedPreferences.Editor editor = gcmPrefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private boolean sendRegistrationToBackend(Context ctx) {

        String base64 = prefs.getString(
                getString(R.string.pref_basic), getString(R.string.empty_str));

        String url = getString(R.string.url_send_reg_gcm_id)
                + prefs.getString(getString(R.string.pref_id), getString(R.string.empty_str));

        return NetworkOperations.sendUserDeviceGcmId(url, base64, getRegistrationId(ctx), ctx);
    }

    private String getRegistrationId(Context ctx) {

        final SharedPreferences gcmPrefs = getGCMPreferences(ctx);

        String registrationId = gcmPrefs.getString(PROPERTY_REG_ID, getString(R.string.empty_str));

        if (registrationId.isEmpty()) {
            Log.i(getString(R.string.log), getString(R.string.error_reg_not_found));
            return getString(R.string.empty_str);
        }

        int registredVersion = gcmPrefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(ctx);

        if (registredVersion != currentVersion) {
            Log.i(getString(R.string.log), getString(R.string.error_app_ver_changed));
            return getString(R.string.empty_str);
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context ctx) {
        return getSharedPreferences(
                DoctorActivity.class.getSimpleName(), ctx.MODE_PRIVATE);
    }

    private int getAppVersion(Context ctx) {

        try {
            PackageInfo packageInfo = ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(getString(R.string.error_package_name_not_found) + e);
        }
    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(getString(R.string.log), getString(R.string.error_device_not_supported));
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onDialogDismiss() {

        if (checkPlayServices()) {

            gcm = GoogleCloudMessaging.getInstance(this);
            regId = getRegistrationId(ctx);
            if (regId.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(getString(R.string.log), getString(R.string.error_no_valid_gps_apk));
        }

        checkUser(prefs.getString(getString(R.string.pref_mrn),
                getString(R.string.empty_str)));
    }
}
