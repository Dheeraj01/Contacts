package com.contacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FirstActivity extends Activity implements SurfaceHolder.Callback,View.OnClickListener,LocationListener{
    Location location1;
    String path;
    private MediaPlayer mp;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    boolean pausing = false;
    public static String filepath;
    Button Sign;
    Context context = FirstActivity.this;
    final Activity thisActivity = this;
    public static final String PACKAGE = "com.contacts";

    public static CallbackManager callbackManager;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    String mobilenumber;
    LoginButton loginButton;
   static {
    try
    {
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
    }
catch(Exception e)
    {

    }

}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Sign=(Button)findViewById(R.id.button2);
        Sign.setOnClickListener(this);

        mPreview = (SurfaceView)findViewById(R.id.video_surface);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean Login = sharedpreferences.getBoolean("Login",false);
        mobilenumber=sharedpreferences.getString("MobileNumber", null);
        if (Login)
        {
            Toast.makeText(this, "else "+mobilenumber+" :: "+Login,Toast.LENGTH_LONG).show();

            Intent intent=new Intent(FirstActivity.this,ThirdActivity.class);
            intent.putExtra("MobileNumber",mobilenumber);
            startActivity(intent);
            finish();
        }else
        {
            Toast.makeText(this, "else "+mobilenumber+" :: "+Login,Toast.LENGTH_LONG).show();

        }
        //  int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       // holder.setFixedSize(width+10, height);
        holder.setSizeFromLayout();
        mp = new MediaPlayer();


        try{
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.people_video);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        }
        catch(Exception e){}


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // holder.setFixedSize(width+10, height);
         holder.setSizeFromLayout();
        try{
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.people_video);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        }
        catch(Exception e){}

    }

    protected void onPause(){
        super.onPause();
        mp.release();

        holder.removeCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
       mp.setDisplay(holder);
       try {
                       mp.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        mp.setLooping(true);


     }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub


    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(context);
        //  new LongOperation().execute("");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bluecolor1)));
        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
        callbackManager = CallbackManager.Factory.create();


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(FirstActivity.this,MainActivity.class);

                        startActivity(intent);
                        finish();
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "can", Toast.LENGTH_LONG).show();

                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "err"+exception, Toast.LENGTH_LONG).show();

                        // App code
                    }
                });
        final EditText email = (EditText) dialog.findViewById(R.id.email);
        final EditText password = (EditText) dialog.findViewById(R.id.password);
        final Button login = (Button) dialog.findViewById(R.id.button3);
        // if button is clicked, close the custom dialog
        Firebase.setAndroidContext(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Creating firebase object

                Firebase ref = new Firebase(Config.FIREBASE_URL);

                //Getting values to store
                final String email1 = email.getText().toString().trim();
                final String password1 = password.getText().toString().trim();

                //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            try {
                               Details person = postSnapshot.child("Details").child(email1).getValue(Details.class);
                                String password11=person.getPassword();
                                if(password1.equals(password11))
                                {
                                  //  location();
                                    String string = "Firstname: "+person.getFirstname()+"\nLastname: "+person.getLastname()+"\n\n";
                                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
                                    Firebase ref = new Firebase(Config.FIREBASE_URL);
                                   // DatabaseReference hopperRef = ref.child("Person").child("Details").child(email1);
                            //       Map<String, Object> userNicknameUpdates = new HashMap<String, Object>();
                           //         userNicknameUpdates.put("latitude", location1.getLatitude());
                                   // userNicknameUpdates.put("longitude", location1.getLongitude());
                              //      ref.child("Person").child("Details").child(email1).updateChildren(userNicknameUpdates);

/*
ref.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Details details = new Details();


        if (location1 != null) {
            details.setLatitude(location1.getLatitude());
            details.setLongitude(location1.getLongitude());
        }
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            snapshot.getRef().child("Details").child(email1).setValue(details);
        }

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
});
                                    //Storing values to firebase
*/
                                    Intent intent =new Intent(FirstActivity.this,MainActivity.class);
                                    intent.putExtra("username",email1);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();

                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Wrong Input", Toast.LENGTH_LONG).show();
                            }


                            //Displaying it on textview
                            // textViewPersons.setText(string);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });


            }
        });
        final Button signup = (Button) dialog.findViewById(R.id.button5);
        // if button is clicked, close the custom dialog
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               Intent intent =new Intent(FirstActivity.this,Signup.class);
               startActivity(intent);
                finish();

            }
        });

        dialog.show();
        Display display =((WindowManager)getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();
        dialog.getWindow().setLayout((6*width)/7,((3)*height)/5);
}

    void location()
    {


        try {
            LocationManager locationManager
                    = (LocationManager) getSystemService(LOCATION_SERVICE);


            Criteria criteria = new Criteria();


            String provider =
                    locationManager.getBestProvider(criteria, true);


            location1 =
                    locationManager.getLastKnownLocation(provider);



            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            Toast.makeText(this, "Latitude:" +  location1.getLatitude()  + ", Longitude:"+ location1.getLongitude(),Toast.LENGTH_LONG).show();

        }
        catch (SecurityException e)
        {
            Toast.makeText(this, "Please on your GPS",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}