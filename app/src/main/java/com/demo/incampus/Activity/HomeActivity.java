package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Adapter.HomeAdapter;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response.Post;
import com.demo.incampus.DiffUtils.HomeActivity.ViewModel.UserViewModel_Home_Post;
import com.demo.incampus.Model.Home;
import com.demo.incampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Initiate Variable
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    long backPressedTime;
    Toast backPressedToast;
    int menuItemId;
    private Context context;
    private DrawerLayout drawerLayout;
    public static String user_id;
    private LiveData<PagedList<Post>> homePagedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=this;

        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id", "expires");

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //Tool Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.myprofile);

        //Drawer Code
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (menuItemId != -1) {
                    handleItemClick();
                }
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Amount of screen the drawer covers
        NavigationView navigationView = findViewById(R.id.navView);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.84);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = width;
        navigationView.setLayoutParams(params);

        //set navigation view listener
        navigationView.setNavigationItemSelectedListener(this);

        //open profile from navigation drawer [profile photo + name]
        ImageView headerImage = navigationView.getHeaderView(0).findViewById(R.id.profilePhoto);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("showFollow" , "false");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.user_profile_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("showFollow" , "false");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        //set image in header Nav Drawer using glide
        String url = "https://incampus-testing.s3.ap-south-1.amazonaws.com/056b278b-a2dc-4f3b-a38d-34b590cc4b62orig.jpg";

        Glide.with(HomeActivity.this)
                .load(url)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .error(R.color.colorAccent)
                .into(headerImage);

        //Recycler View Code
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel_Home_Post homeViewModel = ViewModelProviders.of(this).get(UserViewModel_Home_Post.class);

        //creating the Adapter
        homeAdapter = new HomeAdapter(this);

        //observing the homePagedList from view modexl
        homePagedList = homeViewModel.getHomePagedList();
        homePagedList.observe(this, homes -> homeAdapter.submitList(homes));

        //setting the adapter
        recyclerView.setAdapter(homeAdapter);

        //logout
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            dialogBox();
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        //about us
        TextView aboutus = findViewById(R.id.aboutus);
        aboutus.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        setOnClickListener();

    }

    //navigation drawer item selected listener
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItemId = menuItem.getItemId();
        drawerLayout.closeDrawers();
        return true;
    }

    //function called after drawer closes
    private void handleItemClick() {
        Intent intent;
        switch (menuItemId) {
            case R.id.home:
                break;
            case R.id.explore:
                intent = new Intent(this, ExploreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.messages:
                intent = new Intent(this, MessagesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.community:
                intent = new Intent(this, ManageCommunityActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.events:
                intent = new Intent(this, ManageEventsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
        menuItemId = -1;
    }

    //alert dialog box code
    public void dialogBox() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.layout_dialogbox_logout, null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        FloatingActionButton positive = view.findViewById(R.id.positive);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                alertD.dismiss();
            }
        });

        FloatingActionButton negative = view.findViewById(R.id.negative);
        negative.setOnClickListener(v -> alertD.cancel());
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertD.setView(view);
        alertD.show();
    }

    //move to messages activity
    public void messages(View view) {
        Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to explore activity
    public void explore(View view) {
        Intent intent = new Intent(getApplicationContext(), ExploreActivity.class);
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

    //create a post activity
    public void add(View view) {
        Intent intent = new Intent(getApplicationContext(), CreatePost_Home.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }


    private void setOnClickListener() {
        homeAdapter.setOnPostClickListener(position ->  {
            Intent intent = new Intent(context, PostDetailedActivity.class);

            Post post = homePagedList.getValue().snapshot().get(position);
            intent.putExtra("communityName", post.getCommunityName());
            intent.putExtra("title", post.getName());
            intent.putExtra("userName", post.getUserName());
            intent.putExtra("content", post.getContent());
            intent.putExtra("time", post.getCreated_at());

            intent.putExtra("hearts", post.getUpvotes());
            intent.putExtra("profileImageUrl", post.getUserPicUrl());
            intent.putExtra("postId",post.getPost_id());
            context.startActivity(intent);
            // Log.d(TAG, "onClick: " + position);
        });


    //TODO Tell moin to watch it again sometimes the data is not passed
    }

    //Exit app functionality
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backPressedToast.cancel();
                finishAffinity();
                System.exit(0);
                return;
            } else {
                backPressedToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                backPressedToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }

    }


}
