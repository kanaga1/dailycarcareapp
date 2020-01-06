package com.example.myapplication;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ProductAdapter extends ArrayAdapter<history> {
    ArrayList<history> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ProductAdapter(Context context, int resource, ArrayList<history> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design

        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.date = (TextView ) v.findViewById(R.id.textView5);

            holder.monthly_pay = (TextView)v.findViewById(R.id.m_amount);
            holder.address = (TextView)v.findViewById(R.id.details);
            holder.total_wash = (TextView)v.findViewById(R.id.t_wash);
            holder.paid_amount = (TextView)v.findViewById(R.id.p_amount);
            holder.balance_amt = (TextView)v.findViewById(R.id.b_amount);
            holder.completed_wash = (TextView)v.findViewById(R.id.c_wash);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.date.setText(actorList.get(position).getDate());
        holder.monthly_pay.setText(actorList.get(position).getMonthly_pay());
        holder.address.setText(actorList.get(position).getAddress());
        holder.total_wash.setText(actorList.get(position).getTotal_wash());
        holder.paid_amount.setText(actorList.get(position).getPaid());
        holder.completed_wash.setText(actorList.get(position).getCompleted());
        holder.balance_amt.setText(actorList.get(position).getBalance());

        return v;

    }

    static class ViewHolder {
     public    TextView date, monthly_pay,address, total_wash, paid_amount,balance_amt,completed_wash;


    }


}