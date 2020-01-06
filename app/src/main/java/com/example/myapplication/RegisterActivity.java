package com.example.myapplication;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity<onActivityCreated> extends AppCompatActivity implements View.OnClickListener , ConnectivityReceiver.ConnectionReceiverListener {
    String getvalue="";
    EditText etname,etmobile,etemail,etpassword,etvehicleno,etvehmodel,etaddress;
    Button button1;
    ConnectivityReceiver myReceiver;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_register);
            checkConnection();
            button1 = findViewById(R.id.btnRegister);
            Intent intent = new Intent();
            getvalue=intent.getStringExtra("key");
            etname = ( EditText ) findViewById(R.id.etName);
            etmobile = ( EditText ) findViewById(R.id.etMobile);
            etemail = ( EditText ) findViewById(R.id.etEmail);
            etpassword = ( EditText ) findViewById(R.id.etPassword);
//            etvehicleno = ( EditText ) findViewById(R.id.etVehicleNo);
//            etvehmodel = ( EditText ) findViewById(R.id.etVehModel);
//            etaddress = ( EditText ) findViewById(R.id.etAddress);

            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

            awesomeValidation.addValidation(this, R.id.etName, "[a-zA-Z 0-9]+", R.string.nameerror);
            awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        awesomeValidation.addValidation(this, R.id.etMobile, "^[0-9]{2}[0-9]{8}$", R.string.mobileerror);

        button1.setOnClickListener(this);
        }



    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {

            String name = etname.getText().toString();
            String mobile = etmobile.getText().toString();
            String email = etemail.getText().toString();
            String password = etpassword.getText().toString();
//
//            String vehno = etvehicleno.getText().toString();
//            String vehmodel = etvehmodel.getText().toString();
//            String address = etaddress.getText().toString();
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (Character.isWhitespace(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
            };
//            etvehmodel.setFilters(new InputFilter[] { filter });
//            etvehicleno.setFilters(new InputFilter[] { filter });
//            etaddress.setFilters(new InputFilter[] { filter });
            etname.setFilters(new InputFilter[] { filter });
            etemail.setFilters(new InputFilter[] { filter });
            String type = "register";
            if(name.equals("")||mobile.equals("")||email.equals("")||password.equals(""))
            {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show();
            }
            else{
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, name, mobile,email,password);
//



            }

            //process the data further
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

        if (v == button1) {
            submitForm();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
