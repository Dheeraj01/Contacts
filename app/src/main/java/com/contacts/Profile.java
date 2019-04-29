package com.contacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Profile extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv = (TextView) findViewById(R.id.details);
        Bundle extras = getIntent().getExtras();
       final String value=extras.getString("number");

      //  Toast.makeText(getApplicationContext(), " "+value, Toast.LENGTH_SHORT).show();

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);
        //Value event listener for realtime data update
       // Firebase ref1 = ref.child("Person/Details");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //     Map<String, Object> value = (Map<String, Object>) snapshot.child("Details").child("8093394133").getValue(Details.class);
                //  String s=value.get("firstname").toString();
                //     namearray.add(s);
                //  Details person = postSnapshot.getValue(Details.class);
                //           Toast.makeText(getContext(), value.get("firstname").toString(), Toast.LENGTH_LONG).show();


                /*for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    tv.setText(String.valueOf(postSnapshot.child("Details").child(value).getValue(Details.class).getFirstname())+"\n"+
                             String.valueOf(postSnapshot.child("Details").child(value).getValue(Details.class).getDesignation())+"\n"+
                             String.valueOf(postSnapshot.child("Details").child(value).getValue(Details.class).getMobilenumber())+"\n"+
                             String.valueOf(postSnapshot.child("Details").child(value).getValue(Details.class).getCompanyname())+"\n"+
                             String.valueOf(postSnapshot.child("Details").child(value).getValue(Details.class).getEmail())+"\n");
                     // Details person = postSnapshot.child("Details").child(value).getValue(Details.class);
                    //namearray.add(person.getFirstname().toString());
                }*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
