package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.SocietiesAdapter;
import com.demo.incampus.DiffUtils.Categories.ViewModel.UserViewModel_Categories;
import com.demo.incampus.Model.Societies;
import com.demo.incampus.R;

import java.util.ArrayList;
import java.util.Objects;

public class SocietiesActivity extends AppCompatActivity {
    //Initiate Variable
    RecyclerView recyclerView;
    SocietiesAdapter adapter;
    public static String title="Arts";
    String user_id;
    UserViewModel_Categories userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societies);

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS=RL.getLayoutParams();
        paramS.height= (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //title
        String s = Objects.requireNonNull(getIntent().getExtras()).getString("title");
        TextView t = findViewById(R.id.aboutusTextView);
        t.setText(s);

        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id", "expires");

        title=s;

        //Recycler View Code
        recyclerView = findViewById(R.id.societiesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         userViewModel = ViewModelProviders.of(this).get(UserViewModel_Categories.class);

        adapter = new SocietiesAdapter(this , user_id);
        userViewModel.userPagedList.observe(this, userModels -> adapter.submitList(userModels));

        recyclerView.setAdapter(adapter);

        //OnClickListener for Back Imageview
        ImageView backImageView = findViewById(R.id.back_arrow);
        backImageView.setOnClickListener(v -> onBackPressed());

        //OnClickListener for Search ImageView
        ImageView searchImageView = findViewById(R.id.search);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SocietiesActivity.this).toBundle());
            }
        });

        setOnClickListener();

    }

    private void setOnClickListener() {
        adapter.setOnPostClickListener(position ->  {

            Societies post = userViewModel.userPagedList.getValue().snapshot().get(position);


            // Log.d(TAG, "onClick: " + position);
        });


        //TODO Tell moin to watch it again sometimes the data is not passed
    }

}
