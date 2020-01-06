package com.example.myapplication;
////
////
////
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
public class BookHisFragment extends Fragment{

    ProductAdapter adapter;
    ListView lv;

String username;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View vw=inflater.inflate(R.layout.fragment_bookinghistory, container, false);

        lv=(ListView) vw.findViewById(R.id.listView1);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        username=(mSharedPreference.getString("username", ""));

        new JSONAsynTask().execute("http://dailycarcare.in/androidhistory.php");




        return  vw;


    }

    @Override
    public void onResume() {
        ((navigation) getActivity()).getSupportActionBar().setTitle("Your Bills");

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


                URL url = new URL("http://dailycarcare.in/androidhistory.php");
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
                            String oneObjectsItem = json.getString("start_date");
                            String oneObjectsItem1 = json.getString("monthly_pay");

                            String oneObjectsItem2 = json.getString("address");
                        String oneObjectsItem3 = json.getString("total_wash");
                        String oneObjectsItem4 = json.getString("paid_amt");
                        String oneObjectsItem5 = json.getString("completed");
                        String oneObjectsItem6 = json.getString("balance");

                       data1.add(new history(oneObjectsItem,oneObjectsItem1,oneObjectsItem2,oneObjectsItem3,oneObjectsItem4,oneObjectsItem5,oneObjectsItem6));

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

            adapter = new ProductAdapter(getActivity(), R.layout.layout_history, data1);
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

//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONException;
//
//import android.net.ParseException;
//
//public class BookHisFragment extends Fragment {
//    RecyclerView recyclerView;
//    List<history> historyList;
//    private RecyclerView mRecyclerView;
//    private ListAdapter mListadapter;
//
//    String oneObjectsItem,oneObjectsItem1,oneObjectsItem2,oneObjectsItem3,oneObjectsItem4,oneObjectsItem5,oneObjectsItem6;
//
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//        View v = inflater.inflate(R.layout.fragment_bookinghistory,container,false);
//        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
//
////        date = ( TextView)v.findViewById(R.id.textView5);
////        monthly_pay = (TextView)v.findViewById(R.id.m_amount);
////        address = (TextView ) v.findViewById(R.id.details);
////        total_wash = (TextView ) v.findViewById(R.id.t_wash);
////        paid_amount = (TextView ) v.findViewById(R.id.p_amount);
////        balance_amt = (TextView ) v.findViewById(R.id.b_amount);
////        completed_wash = (TextView ) v.findViewById(R.id.c_wash);
//
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//
////
//
//
//        return v;
//
//
//
//    }
//
//        protected Boolean doInBackground(String... urls) {
//
//
//            try {
//
//
//                HttpGet httppost = new HttpGet(urls[0]);
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpResponse response = httpclient.execute(httppost);
//
//
//                int status = response.getStatusLine().getStatusCode();
//
//                if (status == 200) {
//                    HttpEntity entity = response.getEntity();
//                    String data = EntityUtils.toString(entity);
//
//                    JSONArray JA=new JSONArray(data);
//
////                    for (int i = 0; i < JA.length(); i++) {
////                        JSONObject json = JA.getJSONObject(i);
////                        json=JA.getJSONObject(i);
////                        history actor = new history();
////
////                        actor.setDate(json.getString("start_date"));
////                        actor.setMonthly_pay(json.getString("monthly_pay"));
////                        actor.setAddress(json.getString("address"));
////                        actor.setTotal_wash(json.getString("total_wash"));
////                        actor.setPaid(json.getString("paid_amt"));
////                        actor.setCompleted(json.getString("completed"));
////                        actor.setBalance(json.getString("balance"));
////
////
////                        historyList.add(actor);
////                    }
//                    for(int i=0;i<JA.length();i++)
//                    {
//                        JSONObject json = JA.getJSONObject(i);
//                        json=JA.getJSONObject(i);
//                             oneObjectsItem = json.getString("start_date");
//                             oneObjectsItem1 = json.getString("monthly_pay");
//
//                             oneObjectsItem2 = json.getString("address");
//                         oneObjectsItem3 = json.getString("total_wash");
//                         oneObjectsItem4 = json.getString("paid_amt");
//                         oneObjectsItem5 = json.getString("completed");
//                         oneObjectsItem6 = json.getString("balance");
//
//                        data1.add(new history(oneObjectsItem,oneObjectsItem1,oneObjectsItem2,"5","800","4","7200"));
//
////                         date1[i]=json.getString("start_date");
////                         date.setText(date1[i]);
////
////                         monthly_pay1[i]=json.getString("monthly_pay");
////                        monthly_pay.setText(monthly_pay1[i]);
////
////                        address1[i]=json.getString("address");
////                        address.setText(address1[i]);
////
////                       String total_wash2=json.getString("total_wash");
////                        total_wash.setText(total_wash2);
//
////                        paid_amt1[i]=json.getString("paid_amt");
////                        paid_amount.setText(paid_amt1[i]);
////
////                        completed1[i]=json.getString("completed");
////                        completed_wash.setText(completed1[i]);
////
////                        balance1[i]=json.getString("balance");
////                        balance_amt.setText(balance1[i]);
//                    }
//
//
//
////                    }
//                    mListadapter = new ListAdapter(data1);
//                    mRecyclerView.setAdapter(mListadapter);
//
//                    return true;
//                }
//
//
//
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return false;
//
//        }
//    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>    {
//        private ArrayList<history> dataList;
//
//        public ListAdapter(ArrayList<history> data)
//        {
//            this.dataList = data;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder
//        {
//
//            TextView date, monthly_pay,address, total_wash, paid_amount,balance_amt,completed_wash;
//
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//
//                date = itemView.findViewById(R.id.textView5);
//                monthly_pay = itemView.findViewById(R.id.m_amount);
//                address = itemView.findViewById(R.id.details);
//                total_wash = itemView.findViewById(R.id.t_wash);
//                paid_amount = itemView.findViewById(R.id.p_amount);
//                balance_amt = itemView.findViewById(R.id.b_amount);
//                completed_wash = itemView.findViewById(R.id.c_wash);
//
//            }
//
//
//
//        }
//
//        @Override
//        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//        {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history, parent, false);
//
//            ViewHolder viewHolder = new ViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
//        {
//
//            holder.date.setText(dataList.get(position).getDate());
//            holder.monthly_pay.setText(dataList.get(position).getMonthly_pay());
//            holder.address.setText(dataList.get(position).getAddress());
//            holder.total_wash.setText(dataList.get(position).getTotal_wash());
//            holder.paid_amount.setText(dataList.get(position).getPaid());
//            holder.completed_wash.setText(dataList.get(position).getCompleted());
//            holder.balance_amt.setText(dataList.get(position).getBalance());
//            holder.itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount()
//        {
//            return dataList.size();
//        }
//    }
//}
