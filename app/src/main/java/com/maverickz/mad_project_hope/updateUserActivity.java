package com.maverickz.mad_project_hope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateUserActivity extends AppCompatActivity {



    private TextView name;
    private EditText weight;
    private EditText height;
    private String textname,textweight,textheight;
    private Button updateButton;
//    private FirebaseAuth fauth;
//    private FirebaseDatabase fb;
//    private FirebaseUser user;


    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-hope-1f587-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

//        Intent data = getIntent();
//        String uname = data.getStringExtra("uname");
//        String uweight = data.getStringExtra("uweight");
//        String uheight = data.getStringExtra("uheight");

//        fauth = FirebaseAuth.getInstance();
//        fb = FirebaseDatabase.getInstance();
//        user = fauth.getCurrentUser();

        name = findViewById(R.id.editname);
        weight = findViewById(R.id.editweight);
        height = findViewById(R.id.editheight);
        updateButton = findViewById(R.id.updateButton);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        final String id = sharedPreferences.getString(KEY_NAME,null);

        if(id.isEmpty()){

        }else {
            dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //check email
                    if (snapshot.hasChild(id)) {
//                        type.setText(snapshot.child("type").getValue().toString());
                        name.setText(snapshot.child(id).child("name").getValue(String.class));
//                        name.setText(snapshot.child(id).child("name").getValue(String.class));
                        weight.setText(snapshot.child(id).child("weight").getValue(String.class));
                        height.setText(snapshot.child(id).child("weight").getValue(String.class));
//                        password.setText(snapshot.child(id).child("password").getValue(String.class));
//                        mobileNumber.setText(snapshot.child(id).child("mobile").getValue(String.class));
//                        height.setText(snapshot.child(id).child("height").getValue(String.class));
//                        weight.setText(snapshot.child(id).child("weight").getValue(String.class));
//                        type.setText(snapshot.child("type").getValue().toString());
//                        city.setText(snapshot.child(id).child("city").getValue(String.class));
                    } else {
                        Toast.makeText(updateUserActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


//        name.setText(uname);
//        weight.setText(uweight);
//        height.setText(uheight);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || weight.getText().toString().isEmpty() || height.getText().toString().isEmpty()) {
                    Toast.makeText(updateUserActivity.this, "fields cannot be empty", Toast.LENGTH_SHORT).show();

                } else {

//                    String name2 = name.getText().toString();
                    String weight2 = weight.getText().toString();
                    String height2 = height.getText().toString();

                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                dbref.child("users").child(id).child("weight").setValue(weight2);
                                dbref.child("users").child(id).child("height").setValue(height2);


                                Toast.makeText(updateUserActivity.this , "New Data Stored Successfully" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(updateUserActivity.this , ProfileActivity.class));
                                finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



//        authProfile = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = authProfile.getCurrentUser();

//        showProfile(firebaseUser);


    }

//    private void showProfile(FirebaseUser firebaseUser){
//        String userIDofRegistered = firebaseUser.getUid();
//
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
//
//        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User readWriteDetails = snapshot.getValue(User.class);
//                if (readWriteDetails != null){
//
//                    textname = firebaseUser.getDisplayName();
////                    textweight = readWriteDetails.getRegisterPassword();
////                    textheight = readWriteDetails.getRegisterEmail();
////                    registerCity = readWriteDetails.getRegisterCity();
////                    registerMobile = readWriteDetails.getRegisterMobile();
//                    textweight = readWriteDetails.getRegisterWeight();
//                    textheight = readWriteDetails.getRegisterHeight();
//
//                    name.setText(textname);
//                    weight.setText(textweight);
//                    height.setText(textheight);
//
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}