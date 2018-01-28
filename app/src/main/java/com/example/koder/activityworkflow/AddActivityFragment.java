package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
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

/**
 * Created by Koder on 1/24/2018.
 */

public class AddActivityFragment extends Fragment {

    private DatabaseReference mDatabase;

    private Button btnAdd;
    private EditText etText;
    private ListView listView;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View myView;
        myView = inflater.inflate(R.layout.add_activity, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("activity");

        adapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1,arrayList);

        btnAdd = (Button) myView.findViewById(R.id.addAct);
        etText = (EditText) myView.findViewById(R.id.EditeName);
        listView = (ListView) myView.findViewById(R.id.dbListView);

        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = mDatabase.push().getKey();
                //mDatabase.push().setValue(etText.getText().toString());
                mDatabase.child(id).setValue("gello");
            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();

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
