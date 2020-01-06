package com.example.myapplication;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class HistoryAdaptor extends ArrayAdapter<bill> {
    ArrayList<bill> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    String username;
    String date_pass,time_pass,package1,booktype1,model1;
    Button d;

    public HistoryAdaptor(Context context, int resource, ArrayList<bill> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
        username=(mSharedPreference.getString("username", ""));
        View v = convertView;
        if (v == null) {

            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.date = (TextView ) v.findViewById(R.id.dateview);

            holder.p_type = (TextView)v.findViewById(R.id.p_type);
            holder.v_type = (TextView)v.findViewById(R.id.v_type);
            holder.c_model = (TextView)v.findViewById(R.id.c_model);
            holder.b_time = (TextView)v.findViewById(R.id.b_time);

            holder.Button1= (Button)  v  .findViewById(R.id.delete);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // new JSONAsynTask().execute("http://www.dailycarcare.in/androidhistory.php");



        holder.date.setText(actorList.get(position).getDate());
        holder.p_type.setText(actorList.get(position).getP_type());
        holder.v_type.setText(actorList.get(position).getV_type());
        holder.c_model.setText(actorList.get(position).getC_model());
        holder.b_time.setText(actorList.get(position).getTime());


        holder.Button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                date_pass=  actorList.get(position).getDate();
                time_pass=  actorList.get(position).getTime();

                AlertDialog a =  new AlertDialog.Builder(getContext())
                        .setMessage("Are you wish to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                submitform();
                                actorList.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });



        return v;

    }

    static class ViewHolder {
        public    TextView date, p_type,v_type, c_model, b_time;
        public Button Button1,edit;


    }
    private void submitform() {

        String type = "delete";
        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        backgroundWorker.execute(type,username,date_pass,time_pass);


    }



}