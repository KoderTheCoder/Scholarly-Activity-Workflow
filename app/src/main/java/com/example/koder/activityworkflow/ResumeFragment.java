package com.example.koder.activityworkflow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Koder on 1/24/2018.
 */

public class ResumeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView;

        myView = inflater.inflate(R.layout.resume, container, false);
        return myView;
    }
}
