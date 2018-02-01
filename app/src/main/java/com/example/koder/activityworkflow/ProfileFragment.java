package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Koder on 1/24/2018.
 */


public class ProfileFragment extends Fragment {
    View myView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView displayPic;
    TextView username;
    TextView email;
    TextView activities;
    TextView hours;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //set the user display pic
        if(user.getPhotoUrl() != null){
            displayPic = (ImageView)myView.findViewById(R.id.ivProfile);
            Picasso.with(myView.getContext()).load(user.getPhotoUrl()).placeholder(R.drawable.display_pic)
                    .noFade().into(displayPic);
        }

        //set the username
        username = (TextView)myView.findViewById(R.id.tvName);
        username.setText(user.getDisplayName());

        //set the email
        email = (TextView)myView.findViewById(R.id.profile_email);
        email.setText(user.getEmail());

        activities = (TextView)myView.findViewById(R.id.tv1);
        hours = (TextView)myView.findViewById(R.id.tv2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int activityCount = 0;
                int activityHours = 0;
                for(DataSnapshot ds : dataSnapshot.child("activity").getChildren()){
                    Activity activity = ds.getValue(Activity.class);
                    if(activity.getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        activityCount++;
                        activityHours += Integer.parseInt(activity.getHours());
                    }
                }
                activities.setText(Integer.toString(activityCount));
                hours.setText(Integer.toString(activityHours));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ViewActivitiesFragment()).commit();
            }
        });
        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ViewActivitiesFragment()).commit();
            }
        });
        return myView;
    }
}
