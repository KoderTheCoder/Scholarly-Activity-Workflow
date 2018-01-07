package com.example.koder.activityworkflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

    TextView signup = (TextView) findViewById(R.id.link_signup);
    signup.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent nextScreen = new Intent(MainActivity.this, signup.class);
        startActivityForResult(nextScreen, 1);
      }
    });

  }
}
