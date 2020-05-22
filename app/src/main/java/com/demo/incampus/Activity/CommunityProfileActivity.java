package com.demo.incampus.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.demo.incampus.Adapter.CommunityProfileAdapter;
import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response.Community_profile;
import com.demo.incampus.DiffUtils.CommunityProfile.ViewModel.UserViewModel_Community_Profile;
import com.demo.incampus.R;

public class CommunityProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommunityProfileAdapter communityProfileAdapter;
    private LiveData<PagedList<Community_profile>> homePagedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_profile);

        recyclerView  = findViewById(R.id.recyclerView);

        communityProfileAdapter = new CommunityProfileAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel_Community_Profile homeViewModel = ViewModelProviders.of(this).get(UserViewModel_Community_Profile.class);

        homePagedList = homeViewModel.getEventsPagedList();
        homePagedList.observe(this, homes -> communityProfileAdapter.submitList(homes));

        //setting the adapter
        recyclerView.setAdapter(communityProfileAdapter);


    }
}
