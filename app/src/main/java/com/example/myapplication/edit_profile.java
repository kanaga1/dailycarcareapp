package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
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


public class edit_profile extends Fragment implements View.OnClickListener {

    private AwesomeValidation awesomeValidation;

EditText name1,mobile1,mailid1,vehno1,vehmodel1,address1;
    String username;
    Button b;
    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Update Profile");

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=(mSharedPreference.getString("username", ""));

        name1 = v.findViewById(R.id.etName);

        mobile1=v.findViewById(R.id.etMobile);
        mailid1=v.findViewById(R.id.etEmail);

        vehno1=v.findViewById(R.id.etVehicleNo);
        vehmodel1=v.findViewById(R.id.etVehModel);
        address1=v.findViewById(R.id.etAddress);



        b= (( Button ) v.findViewById(R.id.btnRegister));
                b.setOnClickListener(this);
        new JSONAsynTask().execute("http://dailycarcare.in/androidprofile.php");


        return v;
    }

    @Override
    public void onClick(View v) {


        if (v == b) {
            submitForm();

        }


    }

    private void submitForm() {
            String name = name1.getText().toString();
            String mobile = mobile1.getText().toString();
            String email = mailid1.getText().toString();
            String vehno = vehno1.getText().toString();
            String vehmodel = vehmodel1.getText().toString();
            String address = address1.getText().toString();
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(getActivity(), R.id.etMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

        mobile1.setFilters(new InputFilter[] { filter });
        mailid1.setFilters(new InputFilter[] { filter });
        vehmodel1.setFilters(new InputFilter[] { filter });
        address1.setFilters(new InputFilter[] { filter });
        vehno1.setFilters(new InputFilter[] { filter });

            String type = "edit_Profile";

            if(name.equals("")||mobile.equals("")||email.equals("")||vehmodel.equals("")||address.equals("")||vehno.equals(""))
            {
                Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_LONG).show();
            }
            else{
                if (awesomeValidation.validate()) {

                    BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
                    backgroundWorker.execute(type, name, mobile,email,vehno,vehmodel,address);
                }

            }
    }
    class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
        String result;
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
            String nameFragTxt1,mobile2,email12,address2,vehhhno2,vehhmodel2 ;

            final String[] str1 = new String[array.length()];
            for(int i=0;i<array.length();i++)
            {
                try {
                    JSONObject json = array.getJSONObject(i);

                    nameFragTxt1 = (String) json.get("customer_name");
                    name1.setText(nameFragTxt1);
                    mobile2 = (String) json.get("customer_phone_number");
                    mobile1.setText(mobile2);
                    email12 = (String) json.get("customer_email");
                    mailid1.setText(email12);

                   vehhhno2 = (String) json.get("vehicle_no");
                    vehno1.setText(vehhhno2);

                    address2 = (String) json.get("customer_address");
                    address1.setText(address2);
                    vehhmodel2 = (String) json.get("vehicle_model");
                    vehmodel1.setText(vehhmodel2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            dialog.dismiss();
            if(result == false)
                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}
