package com.contacts;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Signup extends AppCompatActivity implements LocationListener {
    RadioGroup gender;
    RadioButton male, female, other;
    String gender1;
    String mobilenumber1;
    Location location1;
    LocationManager service;
    private EditText firstname, lastname, password1, repassword1, mobilenumber, companyname, designation, dob, email;
    private TextView textViewPersons;
    private Button signin;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Firebase.setAndroidContext(this);
        signin = (Button) findViewById(R.id.signin);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
       // password1 = (EditText) findViewById(R.id.password1);
        //repassword1 = (EditText) findViewById(R.id.repassword1);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);

        email = (EditText) findViewById(R.id.email);
        //gender=(RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), male.getText().toString(), Toast.LENGTH_LONG).show();
                gender1 = male.getText().toString();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), female.getText().toString(), Toast.LENGTH_LONG).show();
                gender1 = female.getText().toString();
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), other.getText().toString(), Toast.LENGTH_LONG).show();
                gender1 = other.getText().toString();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object

                Firebase ref = new Firebase(Config.FIREBASE_URL);

                //Getting values to store
                String firstname1 = firstname.getText().toString().trim();
                String lastname1 = lastname.getText().toString().trim();
                String gender11 = gender1;
                String email1 = email.getText().toString().trim();
              //  String password11 = password1.getText().toString().trim();
                String mobilenumber1 = mobilenumber.getText().toString().trim();

                //Creating Person object

                Details details = new Details();
                //Adding values
                //   person.setName(name);
                // person.setAddress(address);
                details.setFirstname(firstname1);
                details.setLastname(lastname1);
                details.setGender(gender11);
                details.setEmail(email1);
              //  details.setPassword(password11);
                details.setMobilenumber(mobilenumber1);
                details.setWallet(50.50);
                Toast.makeText(Signup.this, "CHECK012", Toast.LENGTH_LONG).show();

               // location();
                int hold = 0;
                try {
                    Toast.makeText(Signup.this, "CHECK0", Toast.LENGTH_LONG).show();

                    service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Toast.makeText(Signup.this, "CHECK11", Toast.LENGTH_LONG).show();

                    boolean enabled = service
                            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    Toast.makeText(Signup.this, "CHECK!"+enabled, Toast.LENGTH_LONG).show();


// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
                    if (enabled) {
                        Toast.makeText(Signup.this, "CHECK!", Toast.LENGTH_LONG).show();

                        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 10, Signup.this);

                    }


                    location1 = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    hold = 0;
                    if (location1 == null) {
                        Toast.makeText(Signup.this, "CHECK2", Toast.LENGTH_LONG).show();

                        hold = 1;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);

                        location1 = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    // Toast.makeText(Signup.this, "Please "+location1,Toast.LENGTH_LONG).show();


                } catch (SecurityException e) {
                    Toast.makeText(Signup.this, "Please on your GPS", Toast.LENGTH_LONG).show();

                }

                if (location1 != null) {
                    details.setLatitude(location1.getLatitude());
                    details.setLongitude(location1.getLongitude());
                    Toast.makeText(Signup.this, "Latitude:" + location1.getLatitude() + ", Longitude:" + location1.getLongitude(), Toast.LENGTH_LONG).show();

                } else {
                 //   Toast.makeText(Signup.this, "Latitude1:" + location1.getLatitude() + ", Longitude:" + location1.getLongitude(), Toast.LENGTH_LONG).show();

                }


                //Storing values to firebase
                ref.child("Person").child("Details").child(mobilenumber1).setValue(details);
                //LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


                //   Criteria criteria = new Criteria();


                //    String provider = locationManager.getBestProvider(criteria, true);

                if (hold != 1) {
                    Intent intent = new Intent(Signup.this, SecondActivity.class);
                    intent.putExtra("Number",mobilenumber1);
                    startActivity(intent);
                    finish();
                }
            }
        });


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
            Toast.makeText(this, "Please on your GPS11",Toast.LENGTH_LONG).show();

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

// generateHashkey();

   /* public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                ((TextView) findViewById(R.id.editText11)).setText(info.packageName);
                ((TextView) findViewById(R.id.editText12)).setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }
    */
