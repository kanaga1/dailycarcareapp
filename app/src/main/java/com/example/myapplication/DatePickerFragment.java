package com.example.myapplication;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import android.widget.TimePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public Dialog onCreateDialog(Bundle savedInstanceState) {


//Set yesterday time milliseconds as date pickers minimum date

        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
        return datePickerDialog;
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        EditText tv = (EditText) getActivity().findViewById(R.id.in_date);
        int mon = (month+1);
        String stringOfDate = day + "/" + mon + "/" + year;
        tv.setText("");
        tv.setText(tv.getText() + stringOfDate);
    }
    }

