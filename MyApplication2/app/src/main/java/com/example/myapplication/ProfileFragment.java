package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    private  TextView nameFragTxt,mobile,email,address,model ;
    String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {


       View v = inflater.inflate(R.layout.fragment_profile,container,false);

        navigation activity = (navigation ) getActivity();
        String myDataFromActivity = activity.getMyData();
        nameFragTxt= (TextView) v.findViewById(R.id.tvname);
        nameFragTxt.setText(myDataFromActivity);
        String myDataFromActivity1 = activity.getMyemail();
        mobile= (TextView) v.findViewById(R.id.tvemail);
        mobile.setText(myDataFromActivity1);
        String myDataFromActivity2 = activity.getMynum();
        email= (TextView) v.findViewById(R.id.tvmobile);
        email.setText(myDataFromActivity2);
        String myDataFromActivity3 = activity.getMyadd();
        address= (TextView) v.findViewById(R.id.tvaddress);
        address.setText(myDataFromActivity3);
        String myDataFromActivity4 = activity.getMymodel();
        model= (TextView) v.findViewById(R.id.tvmodel);
        model.setText(myDataFromActivity4);
        return v;
    }

}
