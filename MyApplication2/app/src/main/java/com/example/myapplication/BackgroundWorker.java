package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.Arrays;


public class BackgroundWorker  extends AsyncTask<String,Void,String> {
    public  String P_name,P_mobile,P_email,P_address,P_model= "";
    Context context;
    AlertDialog alertDialog;
    public String user="";

    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://dailycarcare.in/androidconnection.php";
        String reg_url = "http://dailycarcare.in/androidregistration.php";
        String booknow_url = "http://dailycarcare.in/androidbooknow.php";

        if(type.equals("login")) {
            try {
                user= params[1];

                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       else if(type.equals("register")) {
            try {
                String name = params[1];
                String mobile = params[2];
                String email = params[3];
                String password = params[4];
                String vehno = params[5];
                String vehmodel = params[6];
                String address = params[7];

                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("r_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("r_mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                URLEncoder.encode("r_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("r_password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                URLEncoder.encode("r_vehno","UTF-8")+"="+URLEncoder.encode(vehno,"UTF-8")+"&"
                        +URLEncoder.encode("r_vehmodel","UTF-8")+"="+URLEncoder.encode(vehmodel,"UTF-8")+"&"+
            URLEncoder.encode("r_address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8");
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
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if(type.equals("booknow")) {
            try {
                String packtype = params[1];
                String vehtype = params[2];
                String carmodel = params[3];
                String bookdate = params[4];
                String booktime = params[5];


                URL url = new URL(booknow_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("pack","UTF-8")+"="+URLEncoder.encode(packtype,"UTF-8")+"&"
                        +URLEncoder.encode("vehicle","UTF-8")+"="+URLEncoder.encode(vehtype,"UTF-8")+"&"+
                        URLEncoder.encode("model","UTF-8")+"="+URLEncoder.encode(carmodel,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(bookdate,"UTF-8")+"&"+
                        URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(booktime,"UTF-8");
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
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("Oh my god"+result);
        String[] values = result.split(",");
        System.out.println(Arrays.toString(values));
        P_name=values[1];
        P_mobile=values[2];
        P_email=values[3];
        P_address=values[4];
        P_model=values[5];

        String user = result.substring(16);
        String status = result.substring(0,16);
        if(result!=null && status.equals("connectedSuccess") ) {
          Intent intent = new Intent(context, navigation.class);
            intent.putExtra("key",user);
            intent.putExtra("P_name",P_name);
            intent.putExtra("P_mobile",P_mobile);
            intent.putExtra("P_email",P_email);
            intent.putExtra("P_address",P_address);
            intent.putExtra("P_model",P_model);

            context.startActivity(intent);


        }
        else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
