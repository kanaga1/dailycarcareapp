package com.example.myapplication;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class reschedule extends Fragment {
    String oneObjectsItem3="";
    EditText eText;
    EditText ettime;

    EditText date;
    EditText time;

    String bookdate;
    String booktime,reschedule;
    TextView tx;
    String username,result_pass;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Reschedule Wash");

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reschedule, container, false);

        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = (mSharedPreference.getString("username", ""));
        tx =(TextView ) v.findViewById(R.id.namer);

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


                bookdate = eText.getText().toString();
                booktime = ettime.getText().toString();
                reschedule = tx.getText().toString();
                if ( bookdate.trim().equals("") || booktime.trim().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter All Fields...", Toast.LENGTH_SHORT).show();
                }
                else if(reschedule=="Not yet Subscribed")
                {
                    Toast.makeText(getActivity(), "Sorry, Not yet Subscribed...", Toast.LENGTH_SHORT).show();
                    eText.setText("");
                    ettime.setText("");
                }
                else {


                    String date = bookdate;
                    String time = booktime;
                    String type = "reschedule";


                    BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
                    backgroundWorker.execute(type, username,date, time );

                    eText.setText("");
                    ettime.setText("");


                }
            }
        });
        new JSONAsynTask().execute("http://dailycarcare.in/android_totalwash.php");

        return v;
    }

    class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
        String result;
        ProgressDialog dialog;
        ArrayList data1 = new ArrayList<history>();

        JSONArray array;




        @Override
        protected Boolean doInBackground(String... urls) {


            try {


                URL url = new URL("http://www.dailycarcare.in/android_totalwash.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                result_pass=result;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {

       if(result_pass!=null)
       {
           tx.setText("Your Next Wash : "+result_pass);
       }
       if(result_pass.equals(""))
       {
           tx.setText("Not yet Subscribed");
       }

            if(result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }

    }

}
