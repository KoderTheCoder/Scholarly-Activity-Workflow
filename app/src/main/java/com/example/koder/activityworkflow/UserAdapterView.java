package com.example.koder.activityworkflow;

/**
 * Created by Koder on 2/1/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Adapter takes data and tries to map it to a layout
public class UserAdapterView extends ArrayAdapter<User> {
    public UserAdapterView(Context context, ArrayList<User> values){
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }

        //get code reference to rowText
        TextView rowText = (TextView) convertView.findViewById(R.id.rowName2);
        //get code reference to rowImage
        TextView rowActivity = (TextView) convertView.findViewById(R.id.rowActivity2);
        TextView rowDate = (TextView) convertView.findViewById(R.id.rowDate2);
        ImageView profilePic = (ImageView)convertView.findViewById(R.id.list_pic);

        //gets current item from our arrayList
        User user = getItem(position);

        //change the rowText's text
        rowText.setText(user.getName());
        rowActivity.setText(user.getEmail());
        rowDate.setText("UID: " + user.getUserID());
        Picasso.with(convertView.getContext()).load(user.getPhotoURL()).placeholder(R.drawable.display_pic)
                .noFade().into(profilePic);


        return convertView;
    }
}
