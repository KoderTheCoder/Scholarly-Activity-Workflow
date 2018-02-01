package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Koder on 2/1/2018.
 */

public class AdminViewUsersFragment extends Fragment {
    private static final String TAG = "ViewDatabase";

    private FirebaseAuth mAuth;
    private MyAdapter adapter;
    private UserAdapterView userAdapter;
    private ListView mListView;
    private String name;
    private ArrayList<User> userArray;
    private boolean approval;
    private boolean adminView;
    private TextView heading;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.view_activities, container, false);
        mListView = (ListView) myView.findViewById(R.id.listviewAct);
        userArray = new ArrayList<>();
        heading = (TextView)myView.findViewById(R.id.activitiyTextV);
        heading.setText("Users");

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFireDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mFireDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        Query filterQuery = myRef.child("users").orderByChild("name");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);

                //int childCount = (int)dataSnapshot.child("activity").getChildrenCount();
                //array.add(childCount + " Activities");
                int count = 0;
                userArray.clear();

                for(DataSnapshot ds : dataSnapshot.child("users").getChildren()){
                    User user = ds.getValue(User.class);
                    userArray.add(user);
                    count++;
                }
                userAdapter = new UserAdapterView(getActivity(), userArray);
                mListView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();

                TextView numActivities= (TextView)myView.findViewById(R.id.numberOfActivities);
                numActivities.setText(Integer.toString(count) + " Users");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User item = (User)parent.getItemAtPosition(position);
                Intent viewProfile = new Intent(myView.getContext(), ExternalViewProfile.class);
                viewProfile.putExtra("userID", item.getUserID());
                startActivity(viewProfile);
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
