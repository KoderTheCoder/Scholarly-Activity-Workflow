package com.example.koder.activityworkflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Koder on 1/31/2018.
 */

public class ViewActivity extends AppCompatActivity{
    private Activity activity;
    private String activityID;
    private TextView username;
    private TextView email;
    private TextView activityName;
    private TextView date;
    private TextView hours;
    private TextView location;
    private TextView price;
    private TextView approved;
    private TextView category;
    private Button approvedBtn;
    private Button rejectBtn;
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        mFireDatabase = FirebaseDatabase.getInstance();
        setMyRef(mFireDatabase.getReference());
        mAuth = FirebaseAuth.getInstance();

        //get activity from view_activities
        setActivity((Activity)getIntent().getSerializableExtra("Activity"));
        //get code references to textview objects
        setUsername((TextView)findViewById(R.id.viewActivity_name));
        setEmail((TextView)findViewById(R.id.viewActivity_email));
        setActivityName((TextView)findViewById(R.id.viewActivity_activityName));
        setDate((TextView)findViewById(R.id.viewActivity_date));
        setHours((TextView)findViewById(R.id.viewActivity_hours));
        setLocation((TextView)findViewById(R.id.viewActivity_location));
        setPrice((TextView)findViewById(R.id.viewActivity_price));
        setApproved((TextView)findViewById(R.id.viewActivity_approved));
        setApprovedBtn((Button)findViewById(R.id.approveButton));
        setCategory((TextView) findViewById(R.id.category_name));
        rejectBtn = (Button)findViewById(R.id.rejectButton);

        //set the text
        getUsername().setText(getActivity().getUsername());
        getEmail().setText(getActivity().getEmail());

        getActivityName().setText(getActivity().getActivityName());
        getCategory().setText(getActivity().getCategory());
        getDate().setText(getActivity().getDate());
        getHours().setText(getActivity().getHours());
        getLocation().setText(getActivity().getLocation());
        getPrice().setText("$"+ getActivity().getPrice());
        getApproved().setText(getActivity().getApproval().toString());
        if(getActivity().getApproval() == true){
            getApproved().setTextColor(getResources().getColor(R.color.green));
        }

        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.child("admins").getChildren()) {
                            if(snapshot.getValue(User.class).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                if(!getActivity().getApproval()) {
                                    getApprovedBtn().setVisibility(View.VISIBLE);
                                    rejectBtn.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                        for (DataSnapshot snapshot : dataSnapshot.child("activity").getChildren()) {
                            if(snapshot.getValue(Activity.class).getActivityName().equals(getActivity().getActivityName())){
                                setActivityID(snapshot.getKey());
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        getApprovedBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyRef().child("activity").child(getActivityID()).child("approval").setValue(true);
                getApprovedBtn().setVisibility(View.INVISIBLE);
                rejectBtn.setVisibility(View.INVISIBLE);
                getApproved().setText(R.string.approved_text);
                getApproved().setTextColor(getResources().getColor(R.color.green));
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyRef().child("activity").child(getActivityID()).removeValue();
                Toast.makeText(ViewActivity.this, "Activity Removed", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        getUsername().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfile = new Intent(ViewActivity.this, ExternalViewProfile.class);
                viewProfile.putExtra("userID", getActivity().getUID());
                startActivity(viewProfile);
            }
        });
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public void setMyRef(DatabaseReference myRef) {
        this.myRef = myRef;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(TextView username) {
        this.username = username;
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(TextView email) {
        this.email = email;
    }

    public TextView getActivityName() {
        return activityName;
    }

    public void setActivityName(TextView activityName) {
        this.activityName = activityName;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getHours() {
        return hours;
    }

    public void setHours(TextView hours) {
        this.hours = hours;
    }

    public TextView getLocation() {
        return location;
    }

    public void setLocation(TextView location) {
        this.location = location;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public TextView getApproved() {
        return approved;
    }

    public void setApproved(TextView approved) {
        this.approved = approved;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public Button getApprovedBtn() {
        return approvedBtn;
    }

    public void setApprovedBtn(Button approvedBtn) {
        this.approvedBtn = approvedBtn;
    }
}
