package com.example.myapplication;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    String aMpM;
    int currentHour;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

         aMpM = "AM";
        if(hour >11)
        {
            aMpM = "PM";
        }

        //Make the 24 hour time format to 12 hour time format

        if(hour>11)
        {
            currentHour = hour - 12;
        }
        else
        {
            currentHour = hour;
        }
        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Do something with the date chosen by the user
        EditText tv = (EditText) getActivity().findViewById(R.id.in_time);
        String s =  hourOfDay + ":"+ minute + aMpM;
        tv.setText(tv.getText() + s );


    }

}
