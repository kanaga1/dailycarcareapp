package com.example.myapplication;


import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectionReceiverListener  {


    NetworkInfo networkInfo;
    ConnectivityReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        myReceiver =new ConnectivityReceiver();

        IntentFilter intentFilter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectivityReceiver();
        registerReceiver(myReceiver,intentFilter);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.green));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        TestApplication.getInstance().setConnectionListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected) {

            if(GlobalClass.getUserName(MainActivity.this).length() == 0)
            {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable()
                {

                    public void run()
                    {

                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);

            }
            else
            {
                Intent intent=new Intent(MainActivity.this,navigation.class);
                startActivity(intent);
            }

        }
    }
}
