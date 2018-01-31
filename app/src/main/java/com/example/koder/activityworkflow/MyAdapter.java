package com.example.koder.activityworkflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Adapter takes data and tries to map it to a layout
public class MyAdapter extends ArrayAdapter<Activity> {
    public MyAdapter(Context context, ArrayList<Activity> values){
        //context(current activity)
        //then which row layout to use
        //which part of the row layout can we change the text
        //and the values to put in that part we want to change
        super(context, R.layout.view_activities, values);
    }

    //this is called for each row of our list rendered to the screen
    //so each view it builds is a single row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //converts a xml layout file into a collection of class objects
        LayoutInflater inflater = LayoutInflater.from(getContext());

        //get row layout and build it into a View object
        //parent is which group of existing viewgroups should we add this
        //and false, we do not want to automatically add it to this parent group
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);
        }

        //get code reference to rowText
        TextView rowText = (TextView) convertView.findViewById(R.id.rowName);
        //get code reference to rowImage
        TextView rowActivity = (TextView) convertView.findViewById(R.id.rowActivity);
        TextView rowDate = (TextView) convertView.findViewById(R.id.rowDate);

        //gets current item from our arrayList
        Activity text = getItem(position);

        //change the rowText's text
        rowText.setText(text.getUsername());
        rowActivity.setText(text.getActivityName());
        rowDate.setText(text.getDate());


        return convertView;
    }
}