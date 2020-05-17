package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.ManageEventsAdapter;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvent_Event;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvents_Data;
import com.demo.incampus.DiffUtils.ManageEvents.ViewModel.UserViewModel_ManageEvent;
import com.demo.incampus.Model.ManageEvents;
import com.demo.incampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManageEventsActivity extends AppCompatActivity {

    //Initiate Variable
    RecyclerView recyclerView;
    ManageEventsAdapter adapter;
    private  LiveData<PagedList<ManageEvent_Event>> manage_event_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS=RL.getLayoutParams();
        paramS.height= (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //Recycler View Code
        recyclerView = findViewById(R.id.manageEventsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel_ManageEvent homeViewModel = ViewModelProviders.of(this).get(UserViewModel_ManageEvent.class);

        adapter = new ManageEventsAdapter(this );

        manage_event_list = homeViewModel.getHomePagedList();
        manage_event_list.observe(this, manage_event_list -> adapter.submitList(manage_event_list));

        recyclerView.setAdapter(adapter);

        //add click listener
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent_Explore.class);
                startActivity(intent);
            }
        });

        //add click listener
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
