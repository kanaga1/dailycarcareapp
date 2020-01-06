package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DialogFragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


public class BookNowFragment extends Fragment {


    EditText eText;
    EditText ettime;
    EditText model11;
    EditText date;
    EditText time;
    String pack;
    String veh;
    String bookdate;
    String booktime;
    String carmodel;
    Spinner type1;
    Spinner type2;
    String username;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {

        ((navigation) getActivity()).getSupportActionBar().setTitle("Book Now");

        super.onResume();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booknow, container, false);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = (mSharedPreference.getString("username", ""));
        type1 = v.findViewById(R.id.spinner1);

        type2 = v.findViewById(R.id.spinner2);
        model11 =  v.findViewById(R.id.etcarmodel);
        date = v.findViewById(R.id.in_date);
        bookdate = date.getText().toString();
        time = v.findViewById(R.id.in_time);
        booktime = time.getText().toString();
        final Button date = v.findViewById(R.id.btn_date);
        final Button time = v.findViewById(R.id.btn_time);
        eText = v.findViewById(R.id.in_date);
        ettime = v.findViewById(R.id.in_time);

//
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                android.support.v4.app.DialogFragment newFragment;
                newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");

            }

        });

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");

            }
        });


        Button b = ( Button ) v.findViewById(R.id.button4);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pack = type1.getSelectedItem().toString();
                veh = type2.getSelectedItem().toString();
                carmodel = model11.getText().toString();
                bookdate = eText.getText().toString();
                booktime = ettime.getText().toString();
                if ((type1.getSelectedItem().toString().trim().equals("Select Package Type")) || (type2.getSelectedItem().toString().trim().equals("Select Vehicle Type")) || carmodel.trim().equals("") || bookdate.trim().equals("") || booktime.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter All Fields...", Toast.LENGTH_SHORT).show();
                } else {

                    String pack_type = pack;
                    String vehicle = veh;
                    String model = carmodel;
                    String date = bookdate;
                    String time = booktime;
                    String type = "booknow";


                    BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
                    backgroundWorker.execute(type, pack_type, vehicle, model, date, time, username);
                    type1.setSelection(0);
                    type2.setSelection(0);
                    eText.setText("");
                    ettime.setText("");
                    model11.setText("");

                }
            }
        });
        return v;
    }



}