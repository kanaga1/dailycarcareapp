package com.example.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forget extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectionReceiverListener  {
    Button button;
    EditText email;
    String username;
    ConnectivityReceiver myReceiver;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        button = findViewById(R.id.btnforget);
        checkConnection();
        button.setOnClickListener(this);
    }

    public void submitform() {
        email = ( EditText )findViewById(R.id.femail);

        username = email.getText().toString();



        if(GlobalClass.getUserName(forget.this).length() == 0)
        {
            if (username.equals("")) {
                Toast.makeText(this, "Please Enter your email...", Toast.LENGTH_SHORT).show();

            }


            else{
                String type = "forget";
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, username);
                email.setText("");
            }
        }

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
    @Override
    public void onClick(View v) {
        if (v == button) {
            submitform();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(forget.this, HomeActivity.class);
        startActivity(intent);
    }
}
