package com.example.koder.activityworkflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Koder on 1/7/2018.
 */

public class Signup extends AppCompatActivity {

    EditText firstname;
    EditText lastname;
    EditText email;
    EditText mob_number;
    EditText password1;
    EditText password2;
    Button createAccountBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firstname = (EditText)findViewById(R.id.input_firstname);
        lastname = (EditText)findViewById(R.id.input_lastname);
        email = (EditText)findViewById(R.id.input_email);
        mob_number = (EditText)findViewById(R.id.input_mobile);
        password1 = (EditText)findViewById(R.id.input_password);
        password2 = (EditText)findViewById(R.id.input_reEnterPassword);
        createAccountBtn = (Button)findViewById(R.id.btn_signup);

        TextView login = (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !mob_number.getText().toString().isEmpty() && !password1.getText().toString().isEmpty() && !password2.getText().toString().isEmpty()){
                    //Create account
                }else{
                    Toast.makeText(Signup.this, "Error: Please fill out all text fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
