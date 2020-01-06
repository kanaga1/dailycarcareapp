package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


public class confirm_pass extends Fragment  implements View.OnClickListener {
Button b;
EditText passwrd;
    String username,result_pass;

    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Change Password");

        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm_pass, container, false);
        // Inflate the layout for this fragment
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = (mSharedPreference.getString("username", ""));

        passwrd = v.findViewById(R.id.etPassword);
        b = (( Button ) v.findViewById(R.id.btnRegister1));
        b.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {


        if (passwrd.equals("")) {
            Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_LONG).show();
        }

            else {

                new JSONAsynTask().execute("http://www.dailycarcare.in/android_password.php");



        }
    }

    class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
        String result;
        ProgressDialog dialog;
        ArrayList data1 = new ArrayList<history>();

        JSONArray array;




        @Override
        protected Boolean doInBackground(String... urls) {


            try {

                String pass = passwrd.getText().toString();

                URL url = new URL("http://www.dailycarcare.in/android_password.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")
                        ;
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

            if(result==true)
            {
                if(result_pass.equals("pass"))
                {
                    passwrd.setText("");
                    new_change fragment2 = new new_change();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(), "Enter correct password", Toast.LENGTH_LONG).show();

                }

            }



            if(result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

