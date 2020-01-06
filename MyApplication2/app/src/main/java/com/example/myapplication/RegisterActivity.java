package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText etname,etmobile,etemail,etpassword,etvehicleno,etvehmodel,etaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button button = findViewById(R.id.btnSignin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        final Button button1 = findViewById(R.id.btnRegister);

        etname = (EditText)findViewById(R.id.etName);
        etmobile = (EditText)findViewById(R.id.etMobile);
        etemail = (EditText)findViewById(R.id.etEmail);
        etpassword = (EditText)findViewById(R.id.etPassword);
        etvehicleno = (EditText)findViewById(R.id.etVehicleNo);
        etvehmodel = (EditText)findViewById(R.id.etVehModel);
        etaddress = (EditText)findViewById(R.id.etAddress);

    }

    public void onReg(View view) {
        String name = etname.getText().toString();
        String mobile = etmobile.getText().toString();
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        String vehno = etvehicleno.getText().toString();
        String vehmodel = etvehmodel.getText().toString();
        String address = etaddress.getText().toString();

        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, name, mobile,email,password,vehno,vehmodel,address);
    }
}
