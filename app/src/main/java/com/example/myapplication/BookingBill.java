package com.example.myapplication;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//
//
//public class BookingBill extends Fragment {
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_booking_bill, container, false);
//    }
//
//
//
//
//}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

@SuppressWarnings("deprecation")
public class BookingBill extends Fragment{

    HistoryAdaptor adapter;
    ListView lv;

    String username;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View vw=inflater.inflate(R.layout.fragment_booking_bill, container, false);

        lv=(ListView) vw.findViewById(R.id.listView2);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=(mSharedPreference.getString("username", ""));

        new JSONAsynTask().execute("http://dailycarcare.in/androidhistory.php");




        return  vw;


    }

    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Booking History");

        super.onResume();
    }

    class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
        String result;
        ProgressDialog dialog;
        ArrayList data1 = new ArrayList<history>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }



        @Override
        protected Boolean doInBackground(String... urls) {


            try {


                URL url = new URL("http://dailycarcare.in/androidbill.php");
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








                JSONArray JA=new JSONArray(result);

                final String[] str1 = new String[JA.length()];
                for(int i=0;i<JA.length();i++)
                {
                    JSONObject json = JA.getJSONObject(i);
                    json=JA.getJSONObject(i);
                    String oneObjectsItem = json.getString("service_date");
                    String oneObjectsItem1 = json.getString("package_type");

                    String oneObjectsItem2 = json.getString("model_name");
                    String oneObjectsItem3 = json.getString("service_time");
                    String oneObjectsItem4 = json.getString("vehicle_type");


                    data1.add(new bill(oneObjectsItem,oneObjectsItem1,oneObjectsItem2,oneObjectsItem3,oneObjectsItem4));

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return true;





            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;

        }

        protected void onPostExecute(Boolean result) {

            adapter = new HistoryAdaptor(getActivity(), R.layout.bookingbillhistory, data1);
            lv.setAdapter(adapter);



            dialog.dismiss();
            adapter.notifyDataSetChanged();
            if(data1.size()==0)            {
                Toast.makeText(getActivity(), "Oops...No Bookings Available", Toast.LENGTH_LONG).show();

            }
            if(result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }

    }


}