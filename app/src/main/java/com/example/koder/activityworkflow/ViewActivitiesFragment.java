package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ListView mListView;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView;
        myView = inflater.inflate(R.layout.view_activities, container, false);

        mListView = (ListView) myView.findViewById(R.id.listviewAct);

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFireDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFireDatabase.getReference("activity");
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        //query
        Query filterQuery = myRef.orderByChild("uid").equalTo(userID);
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
                ArrayList<String> array = new ArrayList<>();

                int childCount = (int)dataSnapshot.getChildrenCount();
                int counter =0;

                array.add("Activities Available: "+childCount);


                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    counter++;

                    array.add("");
                    array.add("          Activity "+counter);


                    Activity activity = new Activity();
                    activity.setActivityName(ds.getValue(Activity.class).getActivityName());  //set name
                    activity.setDate(ds.getValue(Activity.class).getDate());  //set date
                    activity.setHours(ds.getValue(Activity.class).getHours());  //set hours
                    activity.setLocation(ds.getValue(Activity.class).getLocation());  //set location
                    activity.setPrice(ds.getValue(Activity.class).getPrice());  //set price

                    //display information in log
                    Log.d("TAG","showData: name: "+activity.getActivityName());
                    Log.d("TAG","showData: date: "+activity.getDate());
                    Log.d("TAG","showData: hours: "+activity.getHours());
                    Log.d("TAG","showData: location: "+activity.getLocation());
                    Log.d("TAG","showData: price: "+activity.getPrice());


//
//                    array.add(ds.getValue(Activity.class).getActivityName());
//                    array.add(ds.getValue(Activity.class).getDate());
//                    array.add(ds.getValue(Activity.class).getHours());
//                    array.add(ds.getValue(Activity.class).getLocation());

                    if(activity.getActivityName() ==null){
                        array.add("n/a");
                    }else
                        array.add("Activity Name: "+activity.getActivityName());
                    if(activity.getDate() ==null){
                        array.add("n/a");
                    }else
                        array.add("Date (DD-MM-YYYY): "+activity.getDate());
                    if(activity.getHours()==null){
                        array.add("n/a");
                    }else
                        array.add("Hours spent: "+activity.getHours()+" hour(s)");
                    if(activity.getLocation()==null){
                        array.add("n/a");
                    }else
                        array.add("Location: "+activity.getLocation());
                    if(activity.getPrice()==null){
                        array.add("n/a");
                    }else
                        array.add("Amount Spent: $ "+activity.getPrice());


                    ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,array);
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
