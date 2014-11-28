package com.ruan.managecarenew.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.activities.DoctorActivity;
import com.ruan.managecarenew.helpers.NetworkOperations;

import java.util.Map;

import static android.content.SharedPreferences.Editor;

public class LoginDialogFragment extends DialogFragment {

    public interface DialogDismissListener {
        void onDialogDismiss();
    }

    DialogDismissListener listener;

    Button login;
    EditText mrnTv;
    EditText passwTv;
    Context ctx;
    String base64;
    String mrn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
        listener = (DialogDismissListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_dialog_fragment, null);

        mrnTv = (EditText) rootView.findViewById(R.id.tv_mrn);
        passwTv = (EditText) rootView.findViewById(R.id.tv_passw);

        login = (Button) rootView.findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                mrn = mrnTv.getText().toString();
                String credentials = mrn + ctx.getString(R.string.semi_colon) + passwTv.getText();
                base64 = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                new LoginTask().execute(ctx.getString(R.string.url_login));
            }
        });

        getDialog().setTitle(getString(R.string.login_dialog_title));
        setCancelable(false);

        return rootView;
    }

    private class LoginTask extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(String... strings) {

            Map<String, String> userInfo = NetworkOperations.getUserInfo(strings[0], base64, ctx);

            return userInfo;
        }

        @Override
        protected void onPostExecute(Map<String, String> user) {
            super.onPostExecute(user);

            if (user != null && user.get(ctx.getString(R.string.pref_mrn)).equals(mrn)) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

                String name = ctx.getString(R.string.pref_fname);
                String id = ctx.getString(R.string.pref_id);
                String lName = ctx.getString(R.string.pref_lname);

                Editor editor = prefs.edit();

                editor
                        .clear()
                        .commit();
                
                editor
                        .putString(id, user.get(id))
                        .putString(ctx.getString(R.string.pref_mrn), mrn)
                        .putString(ctx.getString(R.string.pref_basic), base64)
                        .putString(name, user.get(name))
                        .putString(lName, user.get(lName))
                        .putBoolean(ctx.getString(R.string.pref_has_id), true)
                        .putBoolean(ctx.getString(R.string.is_doctor),
                                mrn.startsWith(getString(R.string.dl)))
                        .commit();

                Editor editor2 = ctx.getSharedPreferences(
                        DoctorActivity.class.getSimpleName(), Context.MODE_PRIVATE).edit();

                editor2
                        .clear()
                        .commit();

                Toast.makeText(ctx,
                        ctx.getString(R.string.welcome_msg) + user.get(name),
                                                              Toast.LENGTH_SHORT).show();
                listener.onDialogDismiss();
                dismiss();
                
            } else {
                Toast.makeText(ctx,
                        ctx.getString(R.string.wrong_passw_msg), Toast.LENGTH_LONG).show();
            }
        }
    }
}
