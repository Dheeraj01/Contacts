package com.contacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThirdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private Bundle extras;
    private static final String TAG = "ThirdActivity";
    LinearLayout linearLayout1;
    ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5;
    ScrollView scrollView1;
    String mobilenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        // scrollView1 =(ScrollView) findViewById(R.id.scrollView1);

        extras = getIntent().getExtras();
        mobilenumber=extras.getString("MobileNumber");


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("MobileNumber", mobilenumber);
        editor.putBoolean("Login",true);
        editor.commit();
        Toast.makeText(ThirdActivity.this,"Thanks",Toast.LENGTH_LONG).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);



         /*   URL url = new URL("http://putatoe.herokuapp.com/api/v1/offers");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String couponResponse=convert(in);
                Log.e("couponResponse", couponResponse);
                Toast.makeText(ThirdActivity.this,"couponResponse in thirdActivity 1"+couponResponse,Toast.LENGTH_LONG).show();

            } finally {
                urlConnection.disconnect();
            }*/
           String couponResponse= URLResponse.geturlresponse("http://putatoe.herokuapp.com/api/v1/offers");
            Toast.makeText(ThirdActivity.this,"couponResponse in thirdActivity 1"+couponResponse,Toast.LENGTH_LONG).show();

            Log.e(TAG,"couponResponse is "+couponResponse);

            showCouponimage(couponResponse);

        }catch (Exception ex)
        {
            Toast.makeText(ThirdActivity.this,"Exception in thirdActivity 1"+ex,Toast.LENGTH_LONG).show();

        }
     }

     void showCouponimage(String couponResponse) throws Exception
     {

         JSONArray jsonObj = new JSONArray(couponResponse);
         Picasso.with(ThirdActivity.this).load(jsonObj.getJSONObject(0).getString("image")).into(imageButton1);

         imageButton1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ThirdActivity.this,"1",Toast.LENGTH_LONG).show();

             }
         });

         Picasso.with(ThirdActivity.this).load(jsonObj.getJSONObject(1).getString("image")).into(imageButton2);

         imageButton2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ThirdActivity.this,"2",Toast.LENGTH_LONG).show();

             }
         });

         Picasso.with(ThirdActivity.this).load(jsonObj.getJSONObject(2).getString("image")).into(imageButton3);

         imageButton3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ThirdActivity.this,"3",Toast.LENGTH_LONG).show();

             }
         });

         Picasso.with(ThirdActivity.this).load(jsonObj.getJSONObject(1).getString("image")).into(imageButton4);

         imageButton4.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ThirdActivity.this,"4",Toast.LENGTH_LONG).show();

             }
         });

         Picasso.with(ThirdActivity.this).load(jsonObj.getJSONObject(2).getString("image")).into(imageButton5);

         imageButton5.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ThirdActivity.this,"5",Toast.LENGTH_LONG).show();

             }
         });


     }

    void create_img1(String ss, int ID)
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200, 200);
        parms.gravity = Gravity.CENTER;
        parms.setMargins(20, 20, 20, 20);
        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(parms);

        int id = getResources().getIdentifier(ss, "id", getPackageName());
        imageView.setImageResource(id);
        linearLayout.addView(imageView);
        imageView.setId(ID);
    }



    public String convert(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.third, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home1) {
            // Handle the camera action
        } else if (id == R.id.address) {

        } else if (id == R.id.contact_us) {

        } else if (id == R.id.share) {

        } else if (id == R.id.account) {

        } else if (id == R.id.logout) {

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("MobileNumber", mobilenumber);
            editor.putBoolean("Login",false);
            editor.commit();
            Intent intent=new Intent(ThirdActivity.this,FirstActivity.class);
            startActivity(intent);
            Toast.makeText(ThirdActivity.this,"LOGOUT",Toast.LENGTH_LONG).show();

            finish();
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getCouponImage()
    {
        String response = "";
        String AJAX_Function = null;

        AJAX_Function = "var callback = arguments[arguments.length - 1];" + "var xmlhttp = new XMLHttpRequest();"
                + "xmlhttp.onreadystatechange = function() {" + "if (xmlhttp.readyState == 4) {"
                + "		callback(xmlhttp.responseText);" + "	}" + "};"
                + "xmlhttp.open('GET','http://putatoe.herokuapp.com/api/v1/offers',true);"

             /*   + "xmlhttp.setRequestHeader(\"Accept-Encoding\",\"gzip,deflate,br\");"
                + "xmlhttp.setRequestHeader(\"Accept-Language\",\"en-US,en;q=0.9\");"
              */ // + "xmlhttp.setRequestHeader(\"Accept\",\"application/json, text/javascript, */*; q=0.01\");"
                /*+ "xmlhttp.setRequestHeader(\"content-type\",\"application/json; charset=UTF-8\");"
             */
                + "xmlhttp.send();";



        //	pRobot.waitForPresenceOfElement("#waiting for ajax response",ElementsType.BY_CLASSNAME, 5,false, 1);

    }

}
