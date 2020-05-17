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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.MessagesAdapter;
import com.demo.incampus.Model.Messages;
import com.demo.incampus.R;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    //initialize variables
    RecyclerView recyclerView;
    MessagesAdapter adapter;
    List<Messages> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayoutMain);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //Recycler View Code
        messagesList = new ArrayList<>();
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messagesList.add(new Messages("bond_007", "Hello Everyone!!", "1 hr ago", R.drawable.scene));
        messagesList.add(new Messages("ram_kumar", "Wassup guys??", "1 hr ago", R.drawable.scene));
        messagesList.add(new Messages("ethan111", "Nice to meet you man..", "1 hr ago", R.drawable.scene));
        messagesList.add(new Messages("chachi_420", "Lets go out somewhere!", "2 hr ago", R.drawable.scene));
        messagesList.add(new Messages("wick_john", "Whats your weekend plan?", "2 hr ago", R.drawable.scene));
        messagesList.add(new Messages("bond_007", "Hello Everyone!!", "3hr ago", R.drawable.scene));
        messagesList.add(new Messages("ram_kumar", "Wassup guys??", "5 hr ago", R.drawable.scene));
        messagesList.add(new Messages("ethan111", "Nice to meet you man..", "5 hr ago", R.drawable.scene));
        messagesList.add(new Messages("chachi_420", "Lets go out somewhere!", "10 hr ago", R.drawable.scene));
        messagesList.add(new Messages("wick_john", "Whats your weekend plan?", "12 hr ago", R.drawable.scene));


        adapter = new MessagesAdapter(this, messagesList);
        recyclerView.setAdapter(adapter);

    }

    //move to home activity
    public void home(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
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

    //start a new group
    public void add(View view) {
        Intent intent = new Intent(getApplicationContext(), StartaNewGroupActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //move to home activity on back pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
