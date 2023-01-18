package com.maverickz.mad_project_hope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DonorRegistrationActivity extends AppCompatActivity {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-hope-1f587-default-rtdb.firebaseio.com/");


    //private CircleImageView profile_image;
    private TextInputEditText registerUserName,registerPassword,registerEmail,
            registerCity,registerMobile,registerWeight,registerHeight;

    private Spinner bloodGroupsSpinner;
    private Button registerButton;
    private TextView backButton;
//    private Uri resultUri;


//    private FirebaseAuth mAuth;
//    private DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DonorRegistrationActivity.this,sign_in_donor_X.class);
                startActivity(intent);
            }
        });

        //profile_image = findViewById(R.id.profile_image);
        registerUserName = findViewById(R.id.registerUserName);
        registerPassword = findViewById(R.id.registerPassword);
        registerEmail = findViewById(R.id.registerEmail);
        registerCity = findViewById(R.id.registerCity);
        registerMobile = findViewById(R.id.registerMobile);
        registerWeight = findViewById(R.id.registerWeight);
        registerHeight = findViewById(R.id.registerHeight);
        bloodGroupsSpinner = findViewById(R.id.bloodGroupsSpinner);
        registerButton = findViewById(R.id.registerButton);


//        mAuth = FirebaseAuth.getInstance() ;



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String email = registerEmail.getText().toString().trim(); //trim used to remove any white spaces
               final String userName = registerUserName.getText().toString().trim();
                final String password = registerPassword.getText().toString().trim();
                final String city = registerCity.getText().toString().trim();
                final String mobile = registerMobile.getText().toString().trim();
                final String weight = registerWeight.getText().toString().trim();
                final String height = registerHeight.getText().toString().trim();
                final String bloodGroup = bloodGroupsSpinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(userName)){
                    registerUserName.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    registerEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    registerPassword.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(city)){
                    registerCity.setError("City is required");
                    return;
                }
                if (TextUtils.isEmpty(mobile)){
                    registerMobile.setError("Mobile is required");
                    return;
                }
                if (TextUtils.isEmpty(weight)){
                    registerWeight.setError("Weight is required");
                    return;
                }
                if (TextUtils.isEmpty(height)){
                    registerHeight.setError("Height is required");
                    return;
                }
                if (bloodGroup.equals("Select Blood Group")){
                    Toast.makeText(DonorRegistrationActivity.this,"Select Blood Group",Toast.LENGTH_SHORT).show();

                }
                else {
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(email)){
                                Toast.makeText(DonorRegistrationActivity.this , "Email is Already Registered" , Toast.LENGTH_SHORT).show();
                                registerEmail.getText().clear();
                            }
                            else if(snapshot.hasChild(mobile)){
                                Toast.makeText(DonorRegistrationActivity.this , "Phone Number is Already Registered" , Toast.LENGTH_SHORT).show();
                                registerMobile.getText().clear();
                            }
                            else if(snapshot.hasChild(userName)){
                                Toast.makeText(DonorRegistrationActivity.this , "Username is Already Registered" , Toast.LENGTH_SHORT).show();
                                registerUserName.getText().clear();
                            }
                            else{
                                dbref.child("users").child(userName).child("name").setValue(userName);
                                dbref.child("users").child(userName).child("email").setValue(email);
                                dbref.child("users").child(userName).child("password").setValue(password);
                                dbref.child("users").child(userName).child("city").setValue(city);
                                dbref.child("users").child(userName).child("mobile").setValue(mobile);
                                dbref.child("users").child(userName).child("weight").setValue(weight);
                                dbref.child("users").child(userName).child("height").setValue(height);
                                dbref.child("users").child(userName).child("bloodGroup").setValue(bloodGroup);


                                Toast.makeText(DonorRegistrationActivity.this , "New Donor Registered Successfully" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DonorRegistrationActivity.this , sign_in_donor_X.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



//                    loader.setMessage("Registering you...");
//                    loader.setCanceledOnTouchOutside(false);
//                    loader.show();

//                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(!task.isSuccessful()){
//                                String error = task.getException().toString();
//                               Toast.makeText(DonorRegistrationActivity.this,"Error" + error,Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                 String currentUserId = mAuth.getCurrentUser().getUid();
//                                 userDatabaseRef = FirebaseDatabase.getInstance().getReference()
//                                         .child("users").child(currentUserId);
//                                HashMap userInfo =  new HashMap();
//                                userInfo.put("email",email);
//                                userInfo.put("id",currentUserId);
//                                userInfo.put("name",userName);
//                                userInfo.put("password",password );
//                                userInfo.put("city",city);
//                                userInfo.put("mobile",mobile);
//                                userInfo.put("weight",weight);
//                                userInfo.put("height",height);
//                                userInfo.put("bloodGroup",bloodGroup);
//                                userInfo.put("type","donor");
//                                userInfo.put("search","donor"+bloodGroup);
//
//                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
//                                    @Override
//                                    public void onComplete(@NonNull Task task) {
//                                   if(task.isSuccessful()){
//                                       Toast.makeText(DonorRegistrationActivity.this,"Data sent Successfully",Toast.LENGTH_SHORT).show();
//                                       Intent intent = new Intent(DonorRegistrationActivity.this, LoginActivity.class);
//                                       intent.putExtra("uid",currentUserId.toString());
//                                       startActivity(intent);
//                                       finish();
//                                   }
//                                   else {
//                                       Toast.makeText(DonorRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
//                                   }
//                                   finish();
//                                   //loader.dismiss();
//
//                                    }
//                                });
//
//
//
//
//                            }
//                        }
//                    });

//                    loader.dismiss();
                }
            }
        });




    }


}