package com.maverickz.mad_project_hope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectRegistrationActivity extends AppCompatActivity {

    private Button donorButton;
    private Button hospitalButton ;
    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_registration);

        //////////////////////////Donor reg activity//////////////
        donorButton = findViewById(R.id.donorButton);
        donorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this,DonorRegistrationActivity.class);
                startActivity(intent);


            }
        });

        //////////////////////////hospital reg activity//////////////
        hospitalButton = findViewById(R.id.hospitalButton);
        hospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, HospitalRegistrationActivity.class);
                startActivity(intent);
            }
        });

        //////////////////////////Back Button activity//////////////
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}