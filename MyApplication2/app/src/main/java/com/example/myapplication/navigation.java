package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import android.view.View;
import android.app.DialogFragment;
import android.widget.TimePicker;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    public String P_name,P_mobile,P_email,P_address,P_model;
    TextView resultView;
    private TextView nameTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar=findViewById(R.id.toolbar);

          Intent intent = getIntent();
           P_name= intent.getStringExtra("P_name");
        P_mobile= intent.getStringExtra("P_mobile");
        P_email= intent.getStringExtra("P_email");
        P_address= intent.getStringExtra("P_address");
        P_model= intent.getStringExtra("P_model");




        System.out.println("Navigation"+P_name);




        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new BookNowFragment()).commit();
        }


    NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




    }
    public String getMyData() {
        return P_name;
    }
    public String getMynum() {
        return P_mobile;
    }
    public String getMyemail() {
        return P_email;
    }
    public String getMyadd() {
        return P_address;
    }
    public String getMymodel() {
        return P_model;
    }



    public void  f1(String pack, String vehicle, String model,String date,String time)
    {
        String type = "booknow";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, pack, vehicle,model,date,time);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment() ).commit();
                break;

            case R.id.booknow:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BookNowFragment() ).commit();
                break;

            case R.id.bookhis:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BookHisFragment() ).commit();
                break;

            case R.id.btn_logout:
                Intent intent = new Intent(navigation.this, HomeActivity.class);
                startActivity(intent);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

}
