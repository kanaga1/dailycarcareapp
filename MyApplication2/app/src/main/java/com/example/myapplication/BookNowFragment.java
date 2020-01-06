package com.example.myapplication;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class BookNowFragment extends Fragment {


    EditText eText;
    EditText ettime;
    EditText model;
    EditText date;
    EditText time;
    String pack;
    String veh;
    String bookdate;
    String booktime;
    String carmodel;
    Spinner type1;
    Spinner type2;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_booknow,container,false);

        type1 = v.findViewById(R.id.spinner1);

        type2 = v.findViewById(R.id.spinner2);

        model = (EditText ) v.findViewById(R.id.etcarmodel);


        date = v.findViewById(R.id.in_date);
        bookdate= date.getText().toString();
        time = v.findViewById(R.id.in_time);
        booktime= time.getText().toString();


        Button date = v.findViewById(R.id.btn_date);
        Button time = v.findViewById(R.id.btn_time);


        eText=v.findViewById(R.id.in_date);
        eText.setInputType(InputType.TYPE_NULL);

        ettime  = v.findViewById(R.id.in_time);
        ettime.setInputType(InputType.TYPE_NULL);

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                android.support.v4.app.DialogFragment newFragment;
                newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");

            }

        });

        date.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View arg0) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");

            }
        });


        Button b = (Button) v.findViewById(R.id.button4);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pack = type1.getSelectedItem().toString();
                veh = type2.getSelectedItem().toString();
                carmodel= model.getText().toString();
                bookdate = eText.getText().toString();
                booktime = ettime.getText().toString();

                String pack_type = pack;
                String vehicle = veh;
                String model = carmodel;
                String date = bookdate;
                String time =booktime;

                navigation navigation = ( com.example.myapplication.navigation ) getActivity();
                navigation.f1(pack_type,vehicle,model,date,time);
            }
        });


        return v;
    }


}
