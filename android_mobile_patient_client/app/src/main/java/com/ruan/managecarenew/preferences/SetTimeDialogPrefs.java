package com.ruan.managecarenew.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.ruan.managecarenew.R;

public class SetTimeDialogPrefs extends DialogPreference {

    TimePicker timePicker;

    public SetTimeDialogPrefs(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        timePicker = (TimePicker) view.findViewById(R.id.time_first_check_in);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(
                getKey().equals(getContext().getString(R.string.prefs_checkin_first)) ? 8 : 21);
        timePicker.setCurrentMinute(0);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            int minutes = timePicker.getCurrentHour() * 60 + timePicker.getCurrentMinute();
            persistInt(minutes);
        }
    }
}
