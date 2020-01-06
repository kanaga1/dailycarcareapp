package com.example.myapplication;

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

@SuppressWarnings("deprecation")
public class ProfileFragment extends Fragment  implements View.OnClickListener {

    private  TextView nameFragTxt,mobile,email,address,model,vehno ;
    String username;
    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Profile");

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=(mSharedPreference.getString("username", ""));
//        navigation activity = (navigation ) getActivity();
//        String myDataFromActivity = activity.getMyData();
        nameFragTxt= (TextView) v.findViewById(R.id.tvname);
//        String myDataFromActivity1 = activity.getMyemail();
        mobile= (TextView) v.findViewById(R.id.tvmobile);
//        String myDataFromActivity2 = activity.getMynum();
        email= (TextView) v.findViewById(R.id.tvemail);
//        String myDataFromActivity3 = activity.getMyadd();
        address= (TextView) v.findViewById(R.id.tvaddress);
//        String myDataFromActivity4 = activity.getMymodel();
        model= (TextView) v.findViewById(R.id.tvmodel);

        vehno =(TextView) v.findViewById(R.id.vehno);

        ((Button) v.findViewById(R.id.editProfile)).setOnClickListener(this);
        new JSONAsynTask().execute("http://dailycarcare.in/androidprofile.php");
        return  v;

    }

    @Override
    public void onClick(View v) {
        edit_profile fragment2 = new edit_profile();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
        String result1;
        ProgressDialog dialog;
        ArrayList data1 = new ArrayList<history>();

        JSONArray array;
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

                URL url = new URL("http://www.dailycarcare.in/androidprofile.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

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
                 array = new JSONArray(result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;

        }

        protected void onPostExecute(Boolean result) {
              String nameFragTxt1,mobile1,email1,address1,model1,vehno1 ;

          if(result.equals("There is no Username Available..."))
          {
              Toast.makeText(getActivity(), "No username available", Toast.LENGTH_LONG).show();
              nameFragTxt.setText("");
              email.setText("");
              mobile.setText("");
              address.setText("");
              model.setText("");
              vehno.setText("");



          }
          else {
             if((array==null))
             {
                 Toast.makeText(getActivity(), "No username available", Toast.LENGTH_LONG).show();
                 nameFragTxt.setText("");
                 email.setText("");
                 mobile.setText("");
                 address.setText("");
                 model.setText("");
                 vehno.setText("");
             }
             else
                 {
              for(int i=0;i<array.length();i++)
              {
                  try {
                      JSONObject json = array.getJSONObject(i);

                      nameFragTxt1 = (String) json.get("customer_name");
                      nameFragTxt.setText(nameFragTxt1);


                      email1 = (String) json.get("customer_email");
                      email.setText(email1);
                      mobile1 = (String) json.get("customer_phone_number");
                      mobile.setText(mobile1);
                      address1 = (String) json.get("customer_address");
                      address.setText(address1);
                      model1 = (String) json.get("vehicle_model");
                      model.setText(model1);
                      vehno1 = (String) json.get("vehicle_no");
                      vehno.setText(vehno1);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }

          }
          }

            dialog.dismiss();



        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}