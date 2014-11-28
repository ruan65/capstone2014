package com.ruan.managecarenew.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.ruan.managecarenew.R;

import java.util.Calendar;
import java.util.TimeZone;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Calendar calendar;

   public interface TimePickerListener {
        void onTimePicked();
    }

    TimePickerListener timePickerListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        timePickerListener = (TimePickerListener) getActivity();

        TimePickerDialog timePickerDialog =
                new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.setTitle(getActivity().getString(R.string.title_pick_time));
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        timePickerListener.onTimePicked();
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
