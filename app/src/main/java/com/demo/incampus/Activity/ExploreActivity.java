package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.ExplorePostsAdapter;
import com.demo.incampus.Adapter.ExploreStatusAdapter;
import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response.Event;
import com.demo.incampus.DiffUtils.Explore.ViewModel.UserViewModel_Explore_Event;
import com.demo.incampus.Model.ExplorePosts;
import com.demo.incampus.Model.ExploreStatus;
import com.demo.incampus.R;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    //initialize variables
    RecyclerView contentRecyclerView, imageRecyclerView;
    ExplorePostsAdapter explorePostsAdapter;
    ExploreStatusAdapter exploreStatusAdapter;
    ArrayList<ExplorePosts> explorePosts;
    ArrayList<ExploreStatus> exploreStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        //rel_layout covering the amount of screen
        RelativeLayout REL = findViewById(R.id.relLayoutMain);
        ViewGroup.LayoutParams params = REL.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        params.height = (int) (height * 0.15);
        REL.setLayoutParams(params);

        //contentRecycler View Code
        explorePosts = new ArrayList<>();
        contentRecyclerView = findViewById(R.id.contentRecyclerview);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel_Explore_Event viewModel = ViewModelProviders.of(this).get(UserViewModel_Explore_Event.class);


        explorePostsAdapter = new ExplorePostsAdapter(this);


        LiveData<PagedList<Event>> pagedList = viewModel.getEventsPagedList();
        pagedList.observe(this, new Observer<PagedList<Event>>() {
            @Override
            public void onChanged(PagedList<Event> events) {
                explorePostsAdapter.submitList(events);
            }
        });

        contentRecyclerView.setAdapter(explorePostsAdapter);


        //imageRecyclerView code
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        exploreStatusList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(linearLayoutManager);

        exploreStatusList.add(new ExploreStatus(R.drawable.ic_arts, "Arts"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_tech, "Technology"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_business, "Business"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_music, "Dance and\nMusic"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_entertainment, "Entertainment"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_education, "Educational"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_politics, "Politics"));
        exploreStatusList.add(new ExploreStatus(R.drawable.ic_sports, "Sports"));


        exploreStatusAdapter = new ExploreStatusAdapter(this, exploreStatusList);
        imageRecyclerView.setAdapter(exploreStatusAdapter);
    }

    //move to home activity
    public void home(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to messages activity
    public void messages(View view) {
        Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to notifications activity
    public void notifications(View view) {
        Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to search activity
    public void search(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to home activity on back pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
