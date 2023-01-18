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
import androidx.annotation.Nullable;
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

public class HospitalRegistrationActivity extends AppCompatActivity {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-hope-1f587-default-rtdb.firebaseio.com/");

    //private CircleImageView profile_image;
    private TextInputEditText registerHospitalName, registerHospitalPassword, registerHospitalEmail;

    private Spinner bloodGroupsSpinner;
    private Button registerButton;
    private TextView backButton;
//    private Uri resultUri;
//    private ProgressDialog loader;
//
//    private FirebaseAuth mAuth;
//    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalRegistrationActivity.this, sign_in_hospital_X.class);
                startActivity(intent);
            }
        });

        // profile_image = findViewById(R.id.profile_image);
        registerHospitalName = findViewById(R.id.registerHospitalName);
        registerHospitalPassword = findViewById(R.id.registerHospitalPassword);
        registerHospitalEmail = findViewById(R.id.registerHospitalEmail);


        registerButton = findViewById(R.id.registerButton);
//        loader = new ProgressDialog(this);

//        mAuth = FirebaseAuth.getInstance() ;

//        profile_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,1);
//
//            }
//        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = registerHospitalEmail.getText().toString().trim();
                final String hospitalName = registerHospitalName.getText().toString().trim();//trim used to remove any white spaces
                final String password = registerHospitalPassword.getText().toString().trim();


                if (TextUtils.isEmpty(hospitalName)) {
                    registerHospitalName.setError("Hospital Name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    registerHospitalEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    registerHospitalPassword.setError("Password is required");
                    return;
                } else {
                    dbref.child("hospital").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email)) {
                                Toast.makeText(HospitalRegistrationActivity.this, "Email is Already Registered", Toast.LENGTH_SHORT).show();
                                registerHospitalEmail.getText().clear();
                            } else if (snapshot.hasChild(hospitalName)) {
                                Toast.makeText(HospitalRegistrationActivity.this, "Hospital is Already Registered", Toast.LENGTH_SHORT).show();
                                registerHospitalName.getText().clear();
                            } else {

                                dbref.child("hospital").child(hospitalName).child("email").setValue(email);
                                dbref.child("hospital").child(hospitalName).child("hospitalName").setValue(hospitalName);
                                dbref.child("hospital").child(hospitalName).child("password").setValue(password);


                                dbref.child("hospital").child(hospitalName).child("AB+").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("AB-").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("O+").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("O-").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("A+").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("A-").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("B+").setValue("0 pints");
                                dbref.child("hospital").child(hospitalName).child("B-").setValue("0 pints");




                                Toast.makeText(HospitalRegistrationActivity.this, "New Hospital Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(HospitalRegistrationActivity.this, sign_in_hospital_X.class));
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
//                                Toast.makeText(HospitalRegistrationActivity.this,"Error" + error,Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                String currentUserId = mAuth.getCurrentUser().getUid();
//
//                                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
//                                        .child("users").child(currentUserId);
//                                HashMap userInfo =  new HashMap();
//                                userInfo.put("email",email);
//                                userInfo.put("id",currentUserId);
//                                userInfo.put("name",hospitalName);
//                                userInfo.put("password",password );
//                                userInfo.put("type","hospital");
//
//
//                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
//                                    @Override
//                                    public void onComplete(@NonNull Task task) {
//                                        if(task.isSuccessful()){
//                                            Toast.makeText(HospitalRegistrationActivity.this,"Data sent Successfully",Toast.LENGTH_SHORT).show();
//                                        }
//                                        else {
//                                            Toast.makeText(HospitalRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
//                                        }
//                                        finish();
//                                        //loader.dismiss();
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
//                    Intent intent = new Intent(HospitalRegistrationActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    loader.dismiss();

                }
            }
        });


    }
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode ==1 && resultCode == RESULT_OK && data !=null){
//            resultUri=data.getData();
//            //profile_image.setImageURI(resultUri);
//        }
//    }
