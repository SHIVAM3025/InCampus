package com.demo.incampus.Activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.incampus.Adapter.ProfilePostsAdapter;
import com.demo.incampus.DiffUtils.Profile.CommunityNumber.Community_Number_Data;
import com.demo.incampus.DiffUtils.Profile.FollowNumber.Followers_Number_Data;
import com.demo.incampus.DiffUtils.Profile.MainProfile.MainProfileData;
import com.demo.incampus.DiffUtils.Profile.ViewModel.UserViewModel_Profile_Posts;
import com.demo.incampus.Model.ProfilePostModel;
import com.demo.incampus.R;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    //Initiate Variable
    boolean following;
    /* TO BE REMOVED IN NEXT VERSION*/
    RecyclerView recyclerView;
    ProfilePostsAdapter adapter;
    public static String user_id = "101";
    JsonObject community_number, following_number, followers_number, profile_show;
    ArrayList<ProfilePostModel> profileList;
    private CircleImageView profileImage;
    private TextView name, college_name;
    TextView textView_community_count, textView_following_number, textView_followers_number;
    private Button follow_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getIntent().getStringExtra("user_id") != null)
        {
            user_id = getIntent().getStringExtra("user_id");
        }else
        {
            user_id="101";
        }

        //Relative layout covering the amount of screen
        RelativeLayout pinkrel = findViewById(R.id.mainRelLayout);
        RelativeLayout rlp = findViewById(R.id.rlp);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams params[] = {rlp.getLayoutParams(), pinkrel.getLayoutParams()};
        params[0].height = (int) (height * 0.33);
        params[1].height = (int) (height * 0.45);
        rlp.setLayoutParams(params[0]);
        pinkrel.setLayoutParams(params[1]);


        //textView initialization
        textView_community_count = findViewById(R.id.community_count);
        textView_following_number = findViewById(R.id.following_count);
        textView_followers_number = findViewById(R.id.followers_count);
        profileImage = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        college_name = findViewById(R.id.college_name);


        community_number = new JsonObject();
        followers_number = new JsonObject();
        following_number = new JsonObject();
        profile_show = new JsonObject();

        //OnClickListener for Back Imageview
        ImageView backImageView = findViewById(R.id.back_arrow);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //On click listener for edit
        ImageView edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);

                //setting flag
                intent.putExtra("flag", 1);

                startActivity(intent);
            }
        });
        //follow button
        final Button follow = findViewById(R.id.follow);

        if(getIntent().getStringExtra("showFollow") !=null)
        {
            follow.setVisibility(View.GONE);
        }


        follow.setText("FOLLOW");
        follow.getBackground().setTint(getResources().getColor(R.color.colorAccent));
        following = true;
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (following) {
                    follow.setText("FOLLOWING");
                    follow.getBackground().setTint(getResources().getColor(R.color.colorPrimary));
                    following = false;
                } else {
                    follow.setText("FOLLOW");
                    follow.getBackground().setTint(getResources().getColor(R.color.colorAccent));
                    following = true;
                }
            }
        });


        community_number.addProperty("query", "query MyQuery {\n" +
                "  Community_members_aggregate(where: {user_id: {_eq: \"" + user_id + "\"}}) {\n" +
                "    aggregate {\n" +
                "      count\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        followers_number.addProperty("query", "query MyQuery {\n" +
                "  UserFollow_aggregate(where: {userid_to: {_eq: \"" + user_id + "\"}}) {\n" +
                "    aggregate {\n" +
                "      count\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        following_number.addProperty("query", "query MyQuery {\n" +
                "  UserFollow_aggregate(where: {userid_from: {_eq: \"" + user_id + "\"}}) {\n" +
                "    aggregate {\n" +
                "      count\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        profile_show.addProperty("query", "query MyQuery {\n" +
                "  User(where: {user_id: {_eq: \"" + user_id + "\"}}) {\n" +
                "    name\n" +
                "    university\n" +
                "    pic_url\n" +
                "  }\n" +
                "}\n");


        GraphqlClient.getApi().community_numbers(community_number).enqueue(new Callback<Community_Number_Data>() {
            @Override
            public void onResponse(Call<Community_Number_Data> call, Response<Community_Number_Data> response) {
                if (response.body() != null) {
                    textView_community_count.setText(response.body().getData().getAggregate().getCount().getCount_number());
                    //Toast.makeText(ProfileActivity.this, response.body().getData().getAggregate().getCount().getCount_number(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Community_Number_Data> call, Throwable t) {

            }
        });

        GraphqlClient.getApi().graphql_follow_count(followers_number).enqueue(new Callback<Followers_Number_Data>() {
            @Override
            public void onResponse(Call<Followers_Number_Data> call, Response<Followers_Number_Data> response) {

                if (response.body() != null) {
                    textView_followers_number.setText(response.body().getData().getAggregate().getCount().getCount_number());
                    //Toast.makeText(ProfileActivity.this, response.body().getData().getAggregate().getCount().getCount_number(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Followers_Number_Data> call, Throwable t) {

            }
        });

        GraphqlClient.getApi().graphql_follow_count(following_number).enqueue(new Callback<Followers_Number_Data>() {
            @Override
            public void onResponse(Call<Followers_Number_Data> call, Response<Followers_Number_Data> response) {
                if (response.body() != null) {
                    textView_following_number.setText(response.body().getData().getAggregate().getCount().getCount_number());
                    //Toast.makeText(ProfileActivity.this, response.body().getData().getAggregate().getCount().getCount_number(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Followers_Number_Data> call, Throwable t) {

                Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });

        GraphqlClient.getApi().profile_show(profile_show).enqueue(new Callback<MainProfileData>() {
            @Override
            public void onResponse(Call<MainProfileData> call, Response<MainProfileData> response) {
                if (response.body() != null) {

                    Glide.with(getApplicationContext())
                            .load(response.body().getData().getUser().get(0).getPic_url())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(profileImage);

                    name.setText(response.body().getData().getUser().get(0).getName());
                    college_name.setText(response.body().getData().getUser().get(0).getUniversity());
                    //Toast.makeText(ProfileActivity.this, response.body().getData().getUser().get(0).getName(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MainProfileData> call, Throwable t) {

                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        /* TO BE REMOVED IN NEXT VERSION*/
        //Recycler View Code
        profileList = new ArrayList<>();
        recyclerView = findViewById(R.id.profileRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel_Profile_Posts userViewModel = ViewModelProviders.of(this).get(UserViewModel_Profile_Posts.class);


        adapter = new ProfilePostsAdapter( this);

        userViewModel.userPagedList.observe(this, userModels -> adapter.submitList(userModels));
        recyclerView.setAdapter(adapter);

        /*TO BE ADDED IN NEXT VERSION*/
 /*       //Fragment Code
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NonSwipeableViewPager viewPager = findViewById(R.id.profileViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ProfilePostsFragment(),"POSTS");
        viewPagerAdapter.AddFragment(new ProfileActivityFragment(),"ACTIVITY");
        viewPagerAdapter.AddFragment(new ProfileAboutFragment(),"ABOUT");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);  */

    }
}
