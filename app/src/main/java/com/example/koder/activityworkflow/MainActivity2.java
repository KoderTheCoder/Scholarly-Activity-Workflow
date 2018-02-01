package com.example.koder.activityworkflow;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    TextView username;
    TextView email;
    private ArrayList<Activity> activityArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AddActivityFragment()).commit();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();

        //Add the username and email to the navigation drawer
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.username);
        username.setText(mAuth.getCurrentUser().getDisplayName());
        email = (TextView) headerView.findViewById(R.id.userEmail);
        email.setText(mAuth.getCurrentUser().getEmail());

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.child("activity").getChildren()){
                    Activity activity = ds.getValue(Activity.class);
                    activityArray.add(activity);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        final MenuItem item = menu.findItem(R.id.approve_activities_button);
        final MenuItem item2 = menu.findItem(R.id.view_users_button);
        final MenuItem item3 = menu.findItem(R.id.view_all_activities);
        final MenuItem item4 = menu.findItem(R.id.generate_summary);

        FirebaseDatabase.getInstance().getReference().child("admins")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(!(snapshot.getValue(User.class).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                                item.setVisible(false);
                                item2.setVisible(false);
                                item3.setVisible(false);
                                item4.setVisible(false);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_button) {
            signOut();
            return true;
        }else if(id == R.id.approve_activities_button){
            Bundle bundle = new Bundle();
            bundle.putString("Approval", "TRUE");
            ViewActivitiesFragment frag = new ViewActivitiesFragment();
            frag.setArguments(bundle);
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
        }else if(id == R.id.view_users_button){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AdminViewUsersFragment()).commit();
        }else if(id == R.id.view_all_activities){
            Bundle bundle = new Bundle();
            bundle.putString("AdminView", "TRUE");
            ViewActivitiesFragment frag = new ViewActivitiesFragment();
            frag.setArguments(bundle);
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
        }else if(id == R.id.generate_summary){
            new GenerateSummary().writeToFile(summary());
            Snackbar.make(getCurrentFocus(),"Activity Summary Generated", Snackbar.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AddActivityFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ViewActivitiesFragment()).commit();
        } else if (id == R.id.nav_manage) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ResumeFragment()).commit();
        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }

    private String summary(){
        String text;
        Collections.reverse(activityArray);
        text = "Total Activities: " + Integer.toString(activityArray.size()) +"\n";
        for(Activity act : activityArray){
            if(act.getApproval()){
                text += "**************************\n\n";
                text += "Activity: " + act.getCategory() +"\n";
                text += "Description: " + act.getActivityName() +"\n";
                text += "Completed by: " + act.getUsername() +"\n";
                text += "Email: " + act.getEmail() +"\n";
                text += "Date: " + act.getDate() +"\n";
                text += "Hours: " + act.getHours() +"\n";
                text += "Location: " + act.getLocation() +"\n";
                text += "Price: $" + act.getPrice() +"\n\n";
            }
        }
        return text;
    }
}
