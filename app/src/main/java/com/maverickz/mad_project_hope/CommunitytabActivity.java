package com.maverickz.mad_project_hope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class CommunitytabActivity extends AppCompatActivity {

    private ImageView choose_communitywall, choose_communityprofile;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communitytab);

        choose_communitywall = findViewById(R.id.choose_communitywall);
        choose_communityprofile = findViewById(R.id.choose_communityprofile);
        backbtn = findViewById(R.id.backcommunity);

        choose_communitywall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommunitytabActivity.this, community_all.class);
                startActivity(i);
                finish();
            }
        });

        choose_communityprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommunitytabActivity.this, Post_Wall_New_Activity.class);
                startActivity(i);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(CommunitytabActivity.this, HomeActivity.class);
                startActivity(i4);
                finish();
            }
        });


    }
}