
package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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


public class new_change extends Fragment implements View.OnClickListener {
    private AwesomeValidation awesomeValidation;

    EditText password1,password2;
        String username;
    Button b;
    @Override
    public void onResume() {
        (( navigation ) getActivity()).getSupportActionBar().setTitle("Change Password");

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());

        username=(mSharedPreference.getString("username", ""));
        View v =inflater.inflate(R.layout.fragment_new_change, container, false);

        password1=v.findViewById(R.id.newpassword);
        password2=v.findViewById(R.id.confirm);
        b= (( Button ) v.findViewById(R.id.changepass1));
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {


        if (v == b) {
            submitForm();

        }


    }

    private void submitForm() {


        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();


        String type = "new_change";
        if (pass1.equals("") || pass2.equals("")) {
            Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_LONG).show();
        } else if (pass1.equals(pass2)) {

                BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
                backgroundWorker.execute(type, username, pass1);

            password1.setText("");
            password2.setText("");

        } else {
            Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_LONG).show();

        }
    }
}