package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

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
    private Spinner spinner1;
    private String category;

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
        final Activity activity = new Activity("","","","",false,"","","");

        Spinner spinner1 = (Spinner) myView.findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(myView.getContext(), R.array.category_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = parent.getItemAtPosition(0).toString();
            }
        });



        //Button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etTextN.getText().toString().isEmpty() || etTextH.getText().toString().isEmpty() || etTextD.getText().toString().isEmpty() || etTextL.getText().toString().isEmpty() || etTextP.getText().toString().isEmpty()){
                    Toast.makeText(myView.getContext(), "Error: Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else{
                    String id = mDatabase.push().getKey();
                    activity.setActivityName(etTextN.getText().toString());
                    activity.setUID(uid);
                    activity.setHours(etTextH.getText().toString());
                    activity.setDate(etTextD.getText().toString());
                    activity.setLocation(etTextL.getText().toString());
                    activity.setPrice(etTextP.getText().toString());
                    activity.setUsername(mAuth.getCurrentUser().getDisplayName());
                    activity.setEmail(mAuth.getCurrentUser().getEmail());
                    activity.setCategory(category);

                    try{
                        mDatabase.child(id).setValue(activity);
                        Toast.makeText(myView.getContext(), "Activity Successfully added", Toast.LENGTH_SHORT).show();
                        etTextN.setText("");
                        etTextH.setText("");
                        etTextD.setText("");
                        etTextL.setText("");
                        etTextP.setText("");
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return myView;
    }

}
