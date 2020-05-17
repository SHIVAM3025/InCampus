package com.demo.incampus.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Adapter.CommentsAdapter;
import com.demo.incampus.Model.Comments;
import com.demo.incampus.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostDetailedActivity extends AppCompatActivity {

    int flag;
    boolean like;

    RecyclerView recyclerView;
    CommentsAdapter adapter;
    ArrayList<Comments> commentsArrayList;
    ImageView profileImage, downArrow;
    TextView topic, name, time, content, messages, hearts;

    ImageView postImage;
    TextView heading;
    TextView commentsTV;
    EditText addCommentETV;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //check condition
        intent = getIntent();
        flag = intent.getIntExtra("flag", 0);

        //screen focus on comments when flag = 1 and open keyboard
        if (flag == 1) {
            openComment();
        }

        initUI();

        //open comment box
        ImageView comment = findViewById(R.id.comment);
        comment.setOnClickListener(v -> openComment());

        context = this;

        //heart
        final ImageView heart = findViewById(R.id.heart);
        like = true;
        heart.setImageResource(R.drawable.heart);
        heart.setOnClickListener(v -> {
            if (like) {
                heart.setImageResource(R.drawable.heart2);
                like = false;
            } else {
                heart.setImageResource(R.drawable.heart);
                like = true;
            }
        });

        //Imageview and Relative layout covering the amount of screen
        RelativeLayout relativeLayout = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ViewGroup.LayoutParams params[] = {postImage.getLayoutParams(), relativeLayout.getLayoutParams()};
        params[0].height = (int) (width * 0.30);
        params[1].height = (int) (height * 0.15);

        setValues();

//        //Recyclerview Code
//        recyclerView = findViewById(R.id.commentsRecyclerView);
//        commentsArrayList = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        for (int i = 0; i < 10; i++) {
//            commentsArrayList.add(new Comments("vaibhav_123", "1 hr ago",
//                    "Can you share some more details...It we be of great help!!", "40", "View 5 Replies"));
//        }
//
//        adapter = new CommentsAdapter(this, commentsArrayList);
//        recyclerView.setAdapter(adapter);

        //back imageview
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //open comment box function
    public void openComment() {
        EditText yourComment = findViewById(R.id.yourComment);
        yourComment.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(yourComment, InputMethodManager.SHOW_IMPLICIT);
    }

    private void initUI() {
        profileImage = findViewById(R.id.profilephoto);
        downArrow = findViewById(R.id.downarrow);
        topic = findViewById(R.id.topic);
        name = findViewById(R.id.user_profile_name);
        time = findViewById(R.id.time);
        heading = findViewById(R.id.heading);
        content = findViewById(R.id.content);
        messages = findViewById(R.id.nmessages);
        hearts = findViewById(R.id.nhearts);

        postImage = findViewById(R.id.postImageView);
        postImage.setVisibility(View.GONE);

        commentsTV = findViewById(R.id.commentTextView);
        addCommentETV = findViewById(R.id.yourComment);
        // recyclerView = findViewById(R.id.commentsRecyclerView);

        commentsTV.setVisibility(View.GONE);
        addCommentETV.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.GONE);
    }

    private void setValues() {

        topic.setText(intent.getStringExtra("communityName"));
        name.setText(intent.getStringExtra("userName"));
        time.setText(intent.getStringExtra("time"));
        content.setText(intent.getStringExtra("content"));
        heading.setText(intent.getStringExtra("title"));
        hearts.setText(intent.getStringExtra("hearts"));

        String d = intent.getStringExtra("time");
        if(d!=null) {
            String[] dd = d.split("//.p");
            long long_time = 0;
            PrettyTime prettyTime;
            prettyTime = new PrettyTime(Locale.getDefault());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            try {
                long_time = sdf.parse(dd[0]).getTime();
                time.setText(prettyTime.format(new Date(long_time)));
            } catch (ParseException e) {
                //e.printStackTrace();
                time.setText("");
            }
        }else
        {
            time.setText("");
        }

        String profileImageUrl = intent.getStringExtra("profileImageUrl");
        if (profileImageUrl != null) {
            Glide.with(context)
                    .load(profileImageUrl)
                    .fitCenter()
                    .into(profileImage);
        }
    }


}
