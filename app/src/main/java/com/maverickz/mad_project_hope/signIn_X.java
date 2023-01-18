package com.maverickz.mad_project_hope;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signIn_X extends AppCompatActivity {

    private Button btnCus;
    private Button btnRes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_x);

        btnRes = (Button) findViewById(R.id.btnres);
        btnCus = (Button) findViewById(R.id.btncus);

        btnRes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openHosActivity();
            }
        });

        btnCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDonActivity();
            }
        });
    }

    public void openHosActivity(){
        Intent intent = new Intent(this , sign_in_hospital_X.class);
        startActivity(intent);
    }

    public void openDonActivity(){
        Intent intent = new Intent(this , sign_in_donor_X.class);
        startActivity(intent);
    }
}

