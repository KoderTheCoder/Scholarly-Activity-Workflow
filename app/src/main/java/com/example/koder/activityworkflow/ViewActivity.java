package com.example.koder.activityworkflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Koder on 1/31/2018.
 */

public class ViewActivity extends AppCompatActivity{
    String activityID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
    }
}
