package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Koder on 1/24/2018.
 */

public class ViewActivitiesFragment extends Fragment {

    private static final String TAG = "ViewDatabase";

    private FirebaseAuth mAuth;
    MyAdapter adapter;
    AdminAdapterView adapterAdmin;
    ListView mListView;
    String name;
    ArrayList<Activity> activityArray;
    boolean approval;
    boolean adminView;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.view_activities, container, false);
        mListView = (ListView) myView.findViewById(R.id.listviewAct);
        activityArray = new ArrayList<>();

        if(getArguments() != null){
            approval = true;
        }

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFireDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mFireDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        //query
        Query filterQuery = myRef.child("activity").orderByChild("uid").equalTo(userID);
        filterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);

                //int childCount = (int)dataSnapshot.child("activity").getChildrenCount();
                //array.add(childCount + " Activities");
                int count = 0;
                activityArray.clear();

                if(approval){
                    for(DataSnapshot ds : dataSnapshot.child("activity").getChildren()){
                        Activity activity = ds.getValue(Activity.class);
                        if(activity.getApproval() == false){
                            activityArray.add(activity);
                            count++;
                        }
                    }
                    adapterAdmin = new AdminAdapterView(getActivity(), activityArray);
                    mListView.setAdapter(adapterAdmin);
                    adapterAdmin.notifyDataSetChanged();
                }else if(adminView){

                }else{
                    for(DataSnapshot ds : dataSnapshot.child("activity").getChildren()){
                        Activity activity = ds.getValue(Activity.class);
                        if(activity.getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            activityArray.add(activity);
                            count++;
                        }
                    }
                    adapter = new MyAdapter(getActivity(), activityArray);
                    mListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                TextView numActivities= (TextView)myView.findViewById(R.id.numberOfActivities);
                numActivities.setText(Integer.toString(count) + " Activities");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity item = (Activity)parent.getItemAtPosition(position);
                Intent nextScreen = new Intent(myView.getContext(), ViewActivity.class);
                nextScreen.putExtra("Activity", item);
                startActivity(nextScreen);
            }
        });

        return myView;
    }

    private void showData(DataSnapshot dataSnapshot) {


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(mAuthListener!=null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}
