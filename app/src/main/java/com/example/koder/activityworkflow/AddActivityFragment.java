package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private EditText etTextP;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View myView;
        myView = inflater.inflate(R.layout.add_activity, container, false);

        //database
        mDatabase = FirebaseDatabase.getInstance().getReference("activity");

        mAuth = FirebaseAuth.getInstance();

        //getting user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        final String uid = user.getUid();

        btnAdd = myView.findViewById(R.id.addAct);
        etTextN = myView.findViewById(R.id.EditeName);
        etTextH = myView.findViewById(R.id.hours);
        etTextD = myView.findViewById(R.id.date);
        etTextL = myView.findViewById(R.id.location);
        etTextP = myView.findViewById(R.id.price);

        //Activity
        final Activity activity = new Activity("","","","",false,"","");

        //Button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = mDatabase.push().getKey();
                activity.setActivityName(etTextN.getText().toString());
                activity.setUID(uid);
                activity.setHours(etTextH.getText().toString());
                activity.setDate(etTextD.getText().toString());
                activity.setLocation(etTextL.getText().toString());
                activity.setPrice(etTextP.getText().toString());
                activity.setUsername(mAuth.getCurrentUser().getDisplayName());
                activity.setEmail(mAuth.getCurrentUser().getEmail());

                try{
                    mDatabase.child(id).setValue(activity);
                    Snackbar.make(myView, "Activity succesfully added", Snackbar.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(myView.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return myView;
    }



}
