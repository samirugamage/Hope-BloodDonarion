package com.maverickz.mad_project_hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_in_donor_X extends AppCompatActivity {



    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-hope-1f587-default-rtdb.firebaseio.com/");
    SharedPreferences  sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_donor_x);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText un = findViewById(R.id.untxt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText pw = findViewById(R.id.txtpassword);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Button loginBtn = findViewById(R.id.loginbtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name= sharedPreferences.getString(KEY_NAME,null);

        if(name!=null){
            Intent intent = new Intent(sign_in_donor_X.this,HomeActivity.class);
            startActivity(intent);
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String untxt = un.getText().toString();
                final String pwtxt = pw.getText().toString();

                if(untxt.isEmpty() || pwtxt.isEmpty()){
                    Toast.makeText(sign_in_donor_X.this , "Please Enter Your Email or Password" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check email
                            if(snapshot.hasChild(untxt)){
                                //get pw
                                final String getPW = snapshot.child(untxt).child("password").getValue(String.class);

                                if(getPW.equals(pwtxt)){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY_NAME,untxt);
                                    editor.apply();
                                    Toast.makeText(sign_in_donor_X.this , "Successfully Logged In" , Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(sign_in_donor_X.this , HomeActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(sign_in_donor_X.this , "Wrong Passoword" , Toast.LENGTH_SHORT).show();
                                    pw.getText().clear();
                                }
                            }
                            else{
                                Toast.makeText(sign_in_donor_X.this , "User Not Found" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });




    }
}