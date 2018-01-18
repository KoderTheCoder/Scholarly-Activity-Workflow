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

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView signup;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        signup = (TextView) findViewById(R.id.link_signup);
        login = (Button) findViewById(R.id.btn_login);

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(Login.this, Signup.class);
                startActivityForResult(nextScreen, 1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()) {
                    //Intent nextScreen = new Intent(Login.this, user_profile.class);
                    //startActivityForResult(nextScreen, 1);
                }else{
                    Toast.makeText(Login.this, "Error: Please fill out all text fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
