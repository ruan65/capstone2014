package com.ruan.managecarenew.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.ruan.managecarenew.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Calendar calendar;

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // this method for some reason (bug?) called twice on 4.1 =< version, so I use onDismiss
        calendar.set(year, month, day, 0, 0, 0);
    }

    public interface DatePickerListener {
        void onDatePicked();
    }

    DatePickerListener datePickerListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerListener = (DatePickerListener) getActivity();

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), this, year, month, day);

        datePickerDialog.setTitle(getActivity().getString(R.string.title_day_picker));

        return datePickerDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        datePickerListener.onDatePicked();
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
