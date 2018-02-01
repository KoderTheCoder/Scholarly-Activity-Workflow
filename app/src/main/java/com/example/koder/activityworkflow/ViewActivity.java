package com.example.koder.activityworkflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by Koder on 1/31/2018.
 */

public class ViewActivity extends AppCompatActivity{
    Activity activity;
    String activityID;
    TextView username;
    TextView email;
    TextView activityName;
    TextView date;
    TextView hours;
    TextView location;
    TextView price;
    TextView approved;
    Button approvedBtn;
    Button rejectBtn;
    FirebaseDatabase mFireDatabase;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        mFireDatabase = FirebaseDatabase.getInstance();
        myRef = mFireDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        //get activity from view_activities
        activity = (Activity)getIntent().getSerializableExtra("Activity");
        //get code references to textview objects
        username = (TextView)findViewById(R.id.viewActivity_name);
        email = (TextView)findViewById(R.id.viewActivity_email);
        activityName = (TextView)findViewById(R.id.viewActivity_activityName);
        date = (TextView)findViewById(R.id.viewActivity_date);
        hours = (TextView)findViewById(R.id.viewActivity_hours);
        location = (TextView)findViewById(R.id.viewActivity_location);
        price = (TextView)findViewById(R.id.viewActivity_price);
        approved = (TextView)findViewById(R.id.viewActivity_approved);
        approvedBtn = (Button)findViewById(R.id.approveButton);
        rejectBtn = (Button)findViewById(R.id.rejectButton);

        //set the text
        username.setText(activity.getUsername());
        email.setText(activity.getEmail());
        activityName.setText(activity.getActivityName());
        date.setText(activity.getDate());
        hours.setText(activity.getHours());
        location.setText(activity.getLocation());
        price.setText("$"+activity.getPrice());
        approved.setText(activity.getApproval().toString());
        if(activity.getApproval() == true){
            approved.setTextColor(getResources().getColor(R.color.green));
        }

        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.child("admins").getChildren()) {
                            if(snapshot.getValue(User.class).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                if(!activity.getApproval()) {
                                    approvedBtn.setVisibility(View.VISIBLE);
                                    rejectBtn.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                        for (DataSnapshot snapshot : dataSnapshot.child("activity").getChildren()) {
                            if(snapshot.getValue(Activity.class).getActivityName().equals(activity.getActivityName())){
                                activityID = snapshot.getKey();
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        approvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("activity").child(activityID).child("approval").setValue(true);
                approvedBtn.setVisibility(View.INVISIBLE);
                rejectBtn.setVisibility(View.INVISIBLE);
                approved.setText(R.string.approved_text);
                approved.setTextColor(getResources().getColor(R.color.green));
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("activity").child(activityID).removeValue();
                Toast.makeText(ViewActivity.this, "Activity Removed", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfile = new Intent(ViewActivity.this, ExternalViewProfile.class);
                viewProfile.putExtra("userID", activity.getUID());
                startActivity(viewProfile);
            }
        });
    }
}
