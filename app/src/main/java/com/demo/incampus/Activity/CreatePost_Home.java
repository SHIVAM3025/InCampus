package com.demo.incampus.Activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Network.GraphqlClient;
import com.demo.incampus.Query.Profile_Post_Eligibility.CreatePostData;
import com.demo.incampus.R;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePost_Home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private JsonObject adapter_json;
    String user_id;
    String content , title ,community_id;
    EditText titleContent , postContent;

    private int POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post__home);

        //Relative layout covering the amount of screen
        RelativeLayout rLayout = findViewById(R.id.rLayout);
        RelativeLayout relativeLayout = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams params[] = {rLayout.getLayoutParams(), relativeLayout.getLayoutParams()};
        params[0].height = (int) (height * 0.60);
        params[1].height = (int) (height * 0.15);

        titleContent = findViewById(R.id.titleContent);
        postContent = findViewById(R.id.postContent);

        user_id = "1";

        //initializing
        adapter_json = new JsonObject();
        adapter_json.addProperty("query", "query MyQuery {\n" +
                "  Community_members(where: {_or: [{isAdmin: {_eq: true}}, {isMod: {_eq: true}}], _and: {user_id: {_eq: \"" + user_id + "\"}}}, offset: 0) {\n" +
                "    members_to_community {\n" +
                "      name\n" +
                "      community_id\n" +
                "    }\n" +
                "  }\n" +
                "}\n");


        //cancel post
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(v -> onBackPressed());

        POSITION = 0;
        List<String> communities_name, communities_id;
        communities_name = new ArrayList<>();
        communities_name.add("SELF");
        communities_id = new ArrayList<>();

        Spinner chooseCommunitySpinner = findViewById(R.id.chooseCommunitySpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_community, communities_name);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dialog_item);
        chooseCommunitySpinner.setOnItemSelectedListener(this);

        GraphqlClient.getApi().query_to_check_home_post_eigibility(adapter_json).enqueue(new Callback<CreatePostData>() {
            @Override
            public void onResponse(Call<CreatePostData> call, Response<CreatePostData> response) {
                for (int i = 0; response.body().getCommunity_members().getList().size() > i; ++i) {
                    communities_name.add(response.body().getCommunity_members().getList().get(i).getUser().getName());
                    communities_id.add(response.body().getCommunity_members().getList().get(i).getUser().getCommunity_id());
                }
                chooseCommunitySpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<CreatePostData> call, Throwable t) {

            }
        });

        //Spinner Code


        //post created
        Button post = findViewById(R.id.post);
        post.setOnClickListener(v -> {
            if (POSITION - 1 != -1) {
               // Toast.makeText(this, communities_id.get(POSITION - 1), Toast.LENGTH_SHORT).show();
                uploadPostById(communities_id.get(POSITION - 1));
            }
            else {
                //Toast.makeText(this, "SELF", Toast.LENGTH_SHORT).show();
                uploadPostBySelf();
            }
            // Intent intent = new Intent(getApplicationContext(), PostCreatedActivity.class);
            //startActivity(intent);
        });

    }

    private void uploadPostById(String id) {

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("query","mutation MyMutation {\n" +
                "  insert_Posts(objects: {content: \""+postContent.getText().toString()+"\", name: \""+titleContent.getText().toString()+"\", community_id: \""+id+"\", created_by: \""+user_id+"\"}) {\n" +
                "    affected_rows\n" +
                "    returning {\n" +
                "      post_id\n" +
                "      created_at\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        Call<JsonObject> create_post =GraphqlClient.getApi().graphql(jsonObject1);

        create_post.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Log.i("Success",response.body().toString());
                    Toast.makeText(CreatePost_Home.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(CreatePost_Home.this , PostCreatedActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("Failure",t.getMessage());
                Toast.makeText(CreatePost_Home.this, "Sorry, This community does not exists", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadPostBySelf() {

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("query","mutation MyMutation {\n" +
                "  insert_Posts(objects: {content: \""+postContent.getText().toString()+"\", name: \""+titleContent.getText().toString()+"\", created_by: \""+user_id+"\"}) {\n" +
                "    affected_rows\n" +
                "    returning {\n" +
                "      post_id\n" +
                "      created_at\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        Call<JsonObject> create_post =GraphqlClient.getApi().graphql(jsonObject1);

        create_post.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Log.i("Success",response.body().toString());
                    Intent intent  = new Intent(CreatePost_Home.this , PostCreatedActivity.class);
                    startActivity(intent);
                    Toast.makeText(CreatePost_Home.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("Failure",t.getMessage());
                Toast.makeText(CreatePost_Home.this, "Sorry, This community does not exists", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //spinnere click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        POSITION = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
