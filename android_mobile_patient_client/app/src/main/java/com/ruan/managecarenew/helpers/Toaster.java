package com.ruan.managecarenew.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ruan.managecarenew.R;

public class Toaster {

    public static void toastInfo(Context ctx, SharedPreferences prefs) {

        Toast.makeText(ctx,

                prefs.getString(ctx.getString(R.string.pref_lname), ctx.getString(R.string.empty_str))
                        + ctx.getString(R.string.space)
                        + prefs.getString(ctx.getString(R.string.pref_fname), ctx.getString(R.string.empty_str))
                        + ctx.getString(R.string.space)
                        + prefs.getString(ctx.getString(R.string.pref_mrn), ctx.getString(R.string.empty_str))

                , Toast.LENGTH_LONG).show();
    }
}
