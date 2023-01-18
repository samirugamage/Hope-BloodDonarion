package com.maverickz.mad_project_hope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class register_X extends AppCompatActivity {


    private Button btnRes;
    private Button btnCus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_x);

        btnRes = (Button) findViewById(R.id.btnres);
        btnCus = (Button) findViewById(R.id.btncus);

        btnRes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openHosActivity();
            }
        });

        btnCus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDonActivity();
            }
        });
    }

    public void openHosActivity(){
        Intent intent = new Intent(this , HospitalRegistrationActivity.class);
        startActivity(intent);
    }

    public void openDonActivity(){
        Intent intent = new Intent(this , DonorRegistrationActivity.class);
        startActivity(intent);
    }

    }
