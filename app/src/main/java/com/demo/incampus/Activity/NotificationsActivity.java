package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.NotificationsAdapter;
import com.demo.incampus.Model.Notifications;
import com.demo.incampus.R;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initiate Variable
    RecyclerView recyclerView;
    ArrayList<Notifications> notificationsArrayList;
    NotificationsAdapter adapter;
    Spinner snotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //Relative layout covering the amount of screen
        RelativeLayout rLayout = findViewById(R.id.relativeLayout2);
        RelativeLayout relativeLayout = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams params[] = {rLayout.getLayoutParams(), relativeLayout.getLayoutParams()};
        params[0].height = (int) (height * 0.23);
        params[1].height = (int) (height * 0.15);

        //Recycler view code
        recyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationsArrayList.add(new Notifications(R.drawable.scene, "shivam007 liked your comment", "1 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "bond007 liked your comment", "1 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "john_wick sent you a message", "1 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "ram_kumar sent you a message", "2 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "abc_xyz liked your comment", "2 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "shivam007 liked your comment", "3 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "bond007 liked your comment", "5 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "john_wick sent you a message", "5 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "ram_kumar sent you a message", "10 hr"));
        notificationsArrayList.add(new Notifications(R.drawable.scene, "abc_xyz liked your comment", "12 hr"));


        adapter = new NotificationsAdapter(this, notificationsArrayList);
        recyclerView.setAdapter(adapter);

        //Spinner code
        snotifications = findViewById(R.id.notifications_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.notifications, R.layout.spinner_notifications);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dialog_item);
        snotifications.setAdapter(spinnerAdapter);
        snotifications.setOnItemSelectedListener(this);


        //OnClickListener for Back Imageview
        ImageView backImageView = findViewById(R.id.back_arrow);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //OnClickListener for Search
        ImageView searchImageView = findViewById(R.id.search);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NotificationsActivity.this).toBundle());
            }
        });
    }

    //move to home activity on back pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //spinner on click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
