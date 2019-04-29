package com.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.contacts.SMS.SMS;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
public class SecondActivity extends AppCompatActivity {
    Button Sign;
    EditText phonenumber;
String mobilenumber;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        setContentView(R.layout.activity_second);
        Sign=(Button)findViewById(R.id.main);
        phonenumber=(EditText)findViewById(R.id.editText);
        mobilenumber=extras.getString("Number");

        Toast.makeText(this, "SMS to"+mobilenumber,Toast.LENGTH_LONG).show();
        String response=SMS.sendCampaign(SMS.apiKey,SMS.secretKey,SMS.useType,mobilenumber,"1111","LazyIn");
        Toast.makeText(this,"response :"+response,Toast.LENGTH_SHORT).show();
    }
   public void go(View v)
    {

        Toast.makeText(this, "Go",Toast.LENGTH_LONG).show();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

         String phone = phonenumber.getText().toString().trim();
        //phone="+91"+phone;
        Toast.makeText(this,"OTP :"+phone,Toast.LENGTH_SHORT).show();


        if (phone.equals("1111"))
        {
            Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
            intent.putExtra("MobileNumber",mobilenumber);
            startActivity(intent);
            finish();
        }
        /*Intent intent=new Intent(SecondActivity.this,Signup.class);
        startActivity(intent);*/
    }

}
