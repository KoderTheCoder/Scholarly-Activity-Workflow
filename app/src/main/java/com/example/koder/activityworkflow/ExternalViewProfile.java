package com.example.koder.activityworkflow;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Koder on 2/1/2018.
 */

public class ExternalViewProfile extends AppCompatActivity{
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference myRef;

    private ImageView displayPic;
    private TextView username;
    private TextView email;
    private TextView activities;
    private TextView hours;
    private int activityCount;
    private int activityHours;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userID = getIntent().getStringExtra("userID");

        mAuth = FirebaseAuth.getInstance();
        mFireDatabase = FirebaseDatabase.getInstance();
        myRef = mFireDatabase.getReference();

        displayPic = (ImageView)findViewById(R.id.ivProfile);
        username = (TextView)findViewById(R.id.tvName);
        email = (TextView)findViewById(R.id.profile_email);
        activities = (TextView)findViewById(R.id.tv1);
        hours = (TextView)findViewById(R.id.tv2);
        displayPic = (ImageView)findViewById(R.id.ivProfile);

        activityCount = 0;
        activityHours = 0;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.child("activity").getChildren()){
                    Activity activity = ds.getValue(Activity.class);
                    if(activity.getUID().equals(userID)){
                        activityCount++;
                        activityHours += Integer.parseInt(activity.getHours());
                    }
                }
                user = dataSnapshot.child("users").child(userID).getValue(User.class);
                username.setText(user.getName());
                email.setText(user.getEmail());
                activities.setText(Integer.toString(activityCount));
                hours.setText(Integer.toString(activityHours));
                Picasso.with(ExternalViewProfile.this).load(user.getPhotoURL()).placeholder(R.drawable.display_pic)
                        .noFade().into(displayPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
