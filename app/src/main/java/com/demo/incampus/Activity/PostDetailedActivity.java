package com.demo.incampus.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Adapter.CommentsAdapter;
import com.demo.incampus.CommonGraphqlModels.CommentCountResponse;
import com.demo.incampus.CommonGraphqlModels.InsertCommentResponse;
import com.demo.incampus.DiffUtils.Comments.CommentsResponse.Comment;
import com.demo.incampus.DiffUtils.Comments.ViewModel.UserViewModel_Comment;
import com.demo.incampus.DiffUtils.Comments.ViewModelFactory_Comments;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Model.Comments;
import com.demo.incampus.Network.GraphqlClient;
import com.demo.incampus.R;
import com.google.gson.JsonObject;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    int postId;
    int userId = 181;

    int count = 0;

    UserViewModel_Comment viewModel;
    LiveData<PagedList<Comment>> pagedList;
    private LiveData<PageKeyedDataSource<Integer, Comment>> liveDataSource;

    public static final Api api = GraphqlClient.getApi();

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


        /////////////////// ................................. ////////////////////////////////////
        ///////////////////   C O M M E N T S    F E E D    START   //////////////////////////////
        getCommentCount();

        showComments();

        setAddCommentListener();

        /////////////////// ................................. ////////////////////////////////////
        ///////////////////   C O M M E N T S    F E E D     END    //////////////////////////////

        //back imageview
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getCommentCount() {
        JsonObject body = new JsonObject();

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Comments_aggregate(where: {post_id: {_eq: \"" + postId + "\"}}) {\n" +
                        "    aggregate {\n" +
                        "      count\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"
        );

        api.getCommentCount(body).enqueue(new Callback<CommentCountResponse>() {
            @Override
            public void onResponse(Call<CommentCountResponse> call, Response<CommentCountResponse> response) {
                messages.setText(response.body().getCommentCount());
            }

            @Override
            public void onFailure(Call<CommentCountResponse> call, Throwable t) {

            }
        });
    }

    private void addComment() {
        JsonObject body = new JsonObject();

        body.addProperty("query",
                "mutation MyMutation {\n" +
                        "  insert_Comments(objects: {content: \"" + addCommentETV.getText().toString()
                        + "\", post_id: \"" + postId + "\", upvotes: \"0\", user_id: \"" + userId + "\"}) {\n" +
                        "    returning {\n" +
                        "      id\n" +
                        "      content\n" +
                        "    }\n" +
                        "    affected_rows\n" +
                        "  }\n" +
                        "  update_Posts(where: {post_id: {_eq: \"" + postId + "\"}}, _inc: {no_of_comments: \"1\"}) {\n" +
                        "    affected_rows\n" +
                        "    returning {\n" +
                        "      no_of_comments\n" +
                        "    }\n" +
                        "  }" +
                        "}"
        );

        if (!TextUtils.isEmpty(addCommentETV.getText().toString())) {
            api.insertComment(body).enqueue(new Callback<InsertCommentResponse>() {
                @Override
                public void onResponse(Call<InsertCommentResponse> call, Response<InsertCommentResponse> response) {
                    String str = "affected rows : " + response.body().getData().getInsert_Comments().getAffected_rows();
                    Toast.makeText(PostDetailedActivity.this, str,
                            Toast.LENGTH_SHORT).show();

                    liveDataSource.getValue().invalidate();

                    getCommentCount();
                }

                @Override
                public void onFailure(Call<InsertCommentResponse> call, Throwable t) {

                }
            });
        }

        // hotfix
        addCommentETV.setText("");
    }

    private void showComments() {
        //Recyclerview Code
        commentsArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this,
                new ViewModelFactory_Comments(this.getApplication(), postId))
                .get(UserViewModel_Comment.class);

        //creating the Adapter
        adapter = new CommentsAdapter(this);

        //observing the homePagedList from view model
        pagedList = viewModel.getCommentsPagedList();

        liveDataSource = viewModel.getLiveDataSource();

        pagedList.observe(this, new Observer<PagedList<Comment>>() {
            @Override
            public void onChanged(PagedList<Comment> comments) {
                adapter.submitList(comments);
            }
        });

        //setting the adapter
        recyclerView.setAdapter(adapter);
    }

    private void setAddCommentListener() {
        /*
        There is a problem with this OnKeyListener
        It fires the addComment method two times (i don't know why)
        So have i have handled this using a hotfix which is to set the editText text to "" (empty)
         */

        addCommentETV.setOnKeyListener((v, keyCode, event) -> {
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                closeKeyboard();
                if (!TextUtils.isEmpty(addCommentETV.getText().toString())) {
                    addComment();
                }
                return true;
            }
            return false;
        });
    }

    private void closeKeyboard() {
        View newView = this.getCurrentFocus();
        if (newView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(newView.getWindowToken(), 0);
        }
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
        recyclerView = findViewById(R.id.commentsRecyclerView);

        postImage = findViewById(R.id.postImageView);
        postImage.setVisibility(View.GONE);

        commentsTV = findViewById(R.id.commentTextView);
        addCommentETV = findViewById(R.id.yourComment);

        commentsTV.setVisibility(View.GONE);
        addCommentETV.setVisibility(View.GONE);
    }

    private void setValues() {

        topic.setText(intent.getStringExtra("communityName"));
        name.setText(intent.getStringExtra("userName"));
        time.setText(intent.getStringExtra("time"));
        content.setText(intent.getStringExtra("content"));
        heading.setText(intent.getStringExtra("title"));
        hearts.setText(intent.getStringExtra("hearts"));

        String d = intent.getStringExtra("time");
        if (d != null) {
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
        } else {
            time.setText("");
        }

        String profileImageUrl = intent.getStringExtra("profileImageUrl");
        if (profileImageUrl != null) {
            Glide.with(context)
                    .load(profileImageUrl)
                    .fitCenter()
                    .into(profileImage);
        }

        postId = Integer.parseInt(intent.getStringExtra("postId"));
    }


}
