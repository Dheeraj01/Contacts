package com.contacts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class    UserProfile extends AppCompatActivity {
    private static final String host = "api.linkedin.com";
    private static final String url1 = "https://" + host
            + "/v1/people/~:"
            + "(id,"
            + "first-name,"
            + "last-name)";
           // "(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
           private static final String url = "https://" + host + "/v1/people/~:"
                + "(first-name,last-name,email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    private ProgressDialog progress;
    private TextView user_name, user_email,phone_number;
    private ImageView profile_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        // Initialize the progressbar
        progress= new ProgressDialog(this);
        progress.setMessage("Retrieve data...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        user_email = (TextView) findViewById(R.id.email);
        user_name = (TextView) findViewById(R.id.name);
        phone_number = (TextView) findViewById(R.id.phone);
        profile_picture = (ImageView) findViewById(R.id.profile_picture);


    }


    public  void  showResult(JSONObject response){

        try {
            user_email.setText(response.get("emailAddress").toString());
            user_name.setText(response.get("formattedName").toString());
            phone_number.setText(response.get("phoneNumber").toString());

            Picasso.with(UserProfile.this).load(response.get("pictureUrl").toString()).into(profile_picture);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}