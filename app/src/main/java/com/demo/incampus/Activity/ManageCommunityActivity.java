package com.demo.incampus.Activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Adapter.ViewPagerAdapter;
import com.demo.incampus.Fragment.ManageCommunityAdminFragment;
import com.demo.incampus.Fragment.ManageCommunityMembersFragment;
import com.demo.incampus.Model.NonSwipeableViewPager;
import com.demo.incampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ManageCommunityActivity extends AppCompatActivity {

    //Initiate Variable
    TabLayout tabLayout;
    NonSwipeableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_community);

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS=RL.getLayoutParams();
        paramS.height= (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //Fragment Code
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ManageCommunityAdminFragment(), "ADMIN");
        viewPagerAdapter.AddFragment(new ManageCommunityMembersFragment(), "MEMBERS");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //back pressed
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //add click listener
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateCommunityActivity.class);
                startActivity(intent);
            }
        });
    }
}
