package com.maverickz.mad_project_hope;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav_view;

    //private String uid;

    Intent data = getIntent();
    String uid = data.getStringExtra("uid");

    private TextView nav_fullname,nav_email,nav_bloodgroup,nav_type;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

//    private FirebaseUser user;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hope");

        drawerLayout =  findViewById(R.id.drawerLayout);

        nav_view  = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(MainActivity.this,drawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        nav_view.setNavigationItemSelectedListener(this);

        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_bloodgroup = nav_view.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);
        nav_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            finish();
            startActivity(getIntent());
        }

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //uid = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        if(!uid.isEmpty()){
            userRef.child(this.uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                        String name = snapshot.child("name").getValue().toString();
                        nav_fullname.setText(name);

                        String email = snapshot.child("email").getValue().toString();
                        nav_email.setText(email);

                        String bloodGroup = snapshot.child("bloodGroup").getValue().toString();
                        nav_bloodgroup.setText(bloodGroup);

                        String type = snapshot.child("type").getValue().toString();
                        nav_type.setText(type);

                    }
          }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent =  new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent2 =  new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}