package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Koder on 1/24/2018.
 */

public class AddActivityFragment extends Fragment {

    private DatabaseReference mDatabase;

    private Button btnAdd;
    private EditText etTextN;
    private EditText etTextH;
    private EditText etTextD;
    private EditText etTextL;
    private ListView listView;

    private ArrayList<Activity> arrayList = new ArrayList<Activity>();
    private ArrayAdapter<Activity> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View myView;
        myView = inflater.inflate(R.layout.add_activity, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("activity");

        adapter = new ArrayAdapter<>(myView.getContext(), android.R.layout.simple_list_item_1,arrayList);

        btnAdd = myView.findViewById(R.id.addAct);
        etTextN = myView.findViewById(R.id.EditeName);
        etTextH = myView.findViewById(R.id.hours);
        etTextD = myView.findViewById(R.id.date);
        etTextL = myView.findViewById(R.id.location);
        listView = myView.findViewById(R.id.dbListView);

        listView.setAdapter(adapter);

        final Activity activity = new Activity("","","",false,"");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = mDatabase.push().getKey();
                activity.activityName = etTextN.getText().toString();
                activity.hours = etTextH.getText().toString();
                activity.date = etTextD.getText().toString();
                activity.location = etTextL.getText().toString();

                mDatabase.setValue(activity);
            }
        });

        //this part doesnt work prob cos you need to get the id
        mDatabase.child("activity").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    String name = post.child("activityName").getValue(String.class);
                    String hours = post.child("hours").getValue(String.class);
                    String date = post.child("hours").getValue(String.class);
                    Boolean approval = post.child("approval").getValue(Boolean.class);
                    String location = post.child("location").getValue(String.class);
                    Log.w("TAG",name + " /" + hours + " /" + date + " /" + approval + " /" + location);
                }
                adapter.notifyDataSetChanged();
//                String string = dataSnapshot.getValue(String.class);
//                arrayList.add(string);
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return myView;
    }



}
