package com.maverickz.mad_project_hope;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
//    private Toolbar toolbar;
    private TextView type,name,email,password,mobileNumber,height,weight,city;
    private Button backButton, editbutton, logoutbtn, deleteaccbtn;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-hope-1f587-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        type = findViewById(R.id.type);
        city = findViewById(R.id.city);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobileNumber = findViewById(R.id.mobileNumber);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        backButton = findViewById(R.id.backButton);
        editbutton = findViewById(R.id.EditprofButton);
        logoutbtn = findViewById(R.id.logout);
        deleteaccbtn = findViewById(R.id.deleteAcc);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        final String id = sharedPreferences.getString(KEY_NAME,null);
//        Toast.makeText(ProfileActivity.this, id, Toast.LENGTH_SHORT).show();

        if(id.isEmpty()){

        }else {
            dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //check email
                    if (snapshot.hasChild(id)) {
//                        type.setText(snapshot.child("type").getValue().toString());
                        name.setText(snapshot.child(id).child("name").getValue(String.class));
                        password.setText(snapshot.child(id).child("password").getValue(String.class));
                        mobileNumber.setText(snapshot.child(id).child("mobile").getValue(String.class));
                        height.setText(snapshot.child(id).child("height").getValue(String.class));
                        weight.setText(snapshot.child(id).child("weight").getValue(String.class));
//                        type.setText(snapshot.child("type").getValue().toString());
                        city.setText(snapshot.child(id).child("city").getValue(String.class));
                    } else {
                        Toast.makeText(ProfileActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

//        dbref.child("users").child(id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 if(snapshot.exists()){
//                     type.setText(snapshot.child("type").getValue().toString());
//                     name.setText(snapshot.child("name").getValue().toString());
//                     password.setText(snapshot.child("password").getValue().toString());
//                     mobileNumber.setText(snapshot.child("mobile").getValue().toString());
//                     height.setText(snapshot.child("height").getValue().toString());
//                     weight.setText(snapshot.child("weight").getValue().toString());
//                     type.setText(snapshot.child("type").getValue().toString());
//                     city.setText(snapshot.child("city").getValue().toString());
//
//
//                 }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentupd = new Intent(ProfileActivity.this,updateUserActivity.class);
                startActivity(intentupd);
                finish();

            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intents= new Intent(ProfileActivity.this,First_Page_X.class);
                startActivity(intents);
                Toast.makeText(ProfileActivity.this,"Log out successfully",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        deleteaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Do you really want to delete this Account? This process cannot be undone...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbref.child("users").child(id).removeValue();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Toast.makeText(ProfileActivity.this,"Account deleted successfully",Toast.LENGTH_SHORT).show();
                        Intent intentdel = new Intent(ProfileActivity.this,First_Page_X.class);
                        startActivity(intentdel);
                        finish();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

//                dbref.child("users").child(id).removeValue();
//                Intent intentupd = new Intent(ProfileActivity.this,updateUserActivity.class);
//                startActivity(intentupd);
//                finish();

            }
        });


    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//
//    }
}