package com.ruan.managecarenew.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.helpers.NetworkOperations;

public class SendMessageDialog extends DialogFragment implements View.OnClickListener{

    EditText etMessage;
    Button btnSend;
    Button btnCancel;
    Context ctx;
    SharedPreferences prefs;

    public SendMessageDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        View rootView = inflater.inflate(R.layout.fragment_send_message_dialog, null);

        etMessage = (EditText) rootView.findViewById(R.id.et_message);

        btnSend = (Button) rootView.findViewById(R.id.btn_send_message_to_doctor);
        btnCancel = (Button) rootView.findViewById(R.id.btn_send_message_cancel);

        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        getDialog().setTitle(getString(R.string.title_send_message_dialog));

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_send_message_cancel:
                dismiss();
                break;

            case R.id.btn_send_message_to_doctor:
                new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return NetworkOperations.SendMessageToDoctor(
                          ctx.getString(R.string.url_main_server) + ctx.getString(R.string.url_part_sendmsg)
                        , prefs.getString(ctx.getString(R.string.pref_basic), ctx.getString(R.string.empty_str))
                        , etMessage.getText().toString()
                        , ctx);
                    }

                    @Override
                    protected void onPostExecute(Boolean ok) {
                        if (isAdded()) {
                            Toast.makeText(ctx, ok
                                            ? getString(R.string.msg_sent)
                                            : getString(R.string.network_error_message),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
                dismiss();
                break;

            default:
                break;
        }
    }
}
