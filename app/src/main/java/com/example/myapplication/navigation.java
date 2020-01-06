package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;



public class navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , ConnectivityReceiver.ConnectionReceiverListener {
    ConnectivityReceiver myReceiver;
    private DrawerLayout drawer;
    public String P_name, P_mobile, P_email, P_address, P_model, username;
    public String profile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
            setContentView(R.layout.activity_navigation);
            Toolbar toolbar = ( Toolbar ) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Intent intent = getIntent();
            P_name = intent.getStringExtra("P_name");
            P_mobile = intent.getStringExtra("P_mobile");
            P_email = intent.getStringExtra("P_email");
            P_address = intent.getStringExtra("P_address");
            P_model = intent.getStringExtra("P_model");
            setSupportActionBar(toolbar);

            final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            username = (mSharedPreference.getString("username", ""));


            drawer = findViewById(R.id.drawer_layout);


            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new BookNowFragment()).commit();
            }


        System.out.println(profile);

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();



    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();


        myReceiver = new ConnectivityReceiver();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectivityReceiver();
        registerReceiver(myReceiver, intentFilter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        TestApplication.getInstance().setConnectionListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {

        }
        else {

        }
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


    public void f1(String pack, String vehicle, String model, String date, String time) {
        String type = "booknow";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, pack, vehicle, model, date, time);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.change:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new confirm_pass()).addToBackStack(null).commit();
                break;

            case R.id.booknow:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookNowFragment()).addToBackStack(null).commit();
                break;
            case R.id.contact:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new contact()).addToBackStack(null).commit();
                break;
            case R.id.reschedule:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new reschedule()).addToBackStack(null).commit();
                break;
            case R.id.bookhis:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookHisFragment()).addToBackStack(null).commit();
                break;
            case R.id.bookbill:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookingBill()).addToBackStack(null).commit();
                break;
            case R.id.btn_logout:
                logout_function();

        }


        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logout_function() {


        new AlertDialog.Builder(this)
                .setMessage("Log out from Dailycarcare?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent intent = new Intent(navigation.this, HomeActivity.class);
                        clear();
                        startActivity(intent);
                    }

                })

                .setNegativeButton("No", null)
                .show();


    }

    public void clear()
    {
        GlobalClass.clearUserName(this);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {


            new AlertDialog.Builder(this)
                    .setMessage("Exit from Dailycarcare?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            navigation.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            getSupportFragmentManager().popBackStack();

        }


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }



    }


}