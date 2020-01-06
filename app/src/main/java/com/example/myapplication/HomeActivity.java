package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectionReceiverListener  {
    EditText UsernameEt, PasswordEt;
    ConnectivityReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();

                setContentView(R.layout.activity_home);

//        myReceiver =new ConnectivityReceiver();
//
//        IntentFilter intentFilter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        myReceiver = new ConnectivityReceiver();
//        registerReceiver(myReceiver,intentFilter);


                UsernameEt = ( EditText ) findViewById(R.id.etusername);
                UsernameEt.setSelection(0);
                final Button button = findViewById(R.id.btnsignup);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
                final Button button1 = findViewById(R.id.btnlogin);


                PasswordEt = ( EditText ) findViewById(R.id.etpassword);





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
    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();

        if(GlobalClass.getUserName(HomeActivity.this).length() == 0)
        {
            if (username.equals("") || password.equals("")) {
                Toast.makeText(this, "Please Enter Valid Fields...", Toast.LENGTH_SHORT).show();
            }


            else{
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, username, password);

            }
        }
        else
        {
            Intent intent=new Intent(HomeActivity.this,navigation.class);
            startActivity(intent);
        }
    }
    public void perform_action(View view) {

            Intent intent=new Intent(HomeActivity.this,forget.class);
            startActivity(intent);
        }

}
