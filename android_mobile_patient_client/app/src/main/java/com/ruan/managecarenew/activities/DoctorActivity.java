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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ruan.managecarenew.R;
import com.ruan.managecarenew.enntities.AnswersData;
import com.ruan.managecarenew.fragments.LoginDialogFragment;
import com.ruan.managecarenew.helpers.NetworkOperations;
import com.ruan.managecarenew.helpers.Toaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static android.widget.AdapterView.OnItemClickListener;

public class DoctorActivity extends ActionBarActivity {

    Context ctx;
    TextView dInfo;
    SharedPreferences prefs;
    ListView listView;
    SimpleAdapter adapter;

    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        ctx = getApplicationContext();

        prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);

        dInfo = (TextView) findViewById(R.id.tv_info);

        String[] from = {getString(R.string.pref_fname), getString(R.string.pref_lname)};

        int[] to = {R.id.list_it_fname, R.id.list_it_lname};

        listView = (ListView) findViewById(R.id.list_all_patients);
        data = new ArrayList<Map<String, String>>();

        adapter = new SimpleAdapter(ctx, data, R.layout.item, from, to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ctx, PatientInfoGraphicActivity.class);
                intent.putExtra(getString(R.string._id), data.get(position).get(getString(R.string._id)));
                intent.putExtra(getString(R.string.pref_fname), data.get(position).get(getString(R.string.pref_fname)));
                intent.putExtra(getString(R.string.pref_lname),
                        data.get(position).get(getString(R.string.pref_lname)));
                startActivity(intent);
                finish();
            }
        });

        new GetPatientsTask().execute(getString(R.string.url_main_server)
                + getString(R.string.url_part_get_patients));
    }

    private class GetPatientsTask extends AsyncTask<String, Void, List<Map<String, String>>> {

        @Override
        protected List<Map<String, String>> doInBackground(String... urls) {
            return NetworkOperations
                    .downloadDoctorPatientsList(urls[0], prefs.getString(getString(R.string.pref_basic),getString(R.string.empty_str)), ctx);
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> list) {

            if (list == null) {
                dInfo.setText(getString(R.string.connection_error));
            } else {
                for (Map<String, String> m : list) {
                    data.add(m);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctror, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_reenter:
                prefs.edit().clear().commit();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;

            case R.id.action_help:
                Toaster.toastInfo(ctx, prefs);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
