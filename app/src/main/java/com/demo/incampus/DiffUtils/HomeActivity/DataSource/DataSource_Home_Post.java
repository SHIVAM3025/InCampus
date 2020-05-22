package com.demo.incampus.DiffUtils.HomeActivity.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response.Post;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_Home_Post extends PageKeyedDataSource<Integer, Post> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Post> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts( limit: " + PAGE_SIZE + ", offset: " + offset + ", where : {_or: [{post_to_community_members: {user_id: {_eq: \"181\"}}}, {post_to_followers: {userid_from: {_eq: \"181\"}}}, {created_by: {_eq: \"181\"}}]}, order_by: {created_at: desc}) {\n" +
                "    community_id\n" +
                "    content\n" +
                "    created_at\n" +
                "    created_by\n" +
                "    name\n" +
                "    pic_url\n" +
                "    post_id\n" +
                "    upvotes\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      name\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      pic_url\n" +
                "      user_id\n" +
                "      name\n" +
                "    }\n" +
                "    postUpvotesByPostId_aggregate {\n" +
                "      aggregate {\n" +
                "        count\n" +
                "      }\n" +
                "    }\n" +
                "  postUpvotesByPostId(where: {user_id: {_eq: \"181\"}}) {\n" +
                "      user_id\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getHomeFeed(body).enqueue(new Callback<Home_Post_Response>() {
            @Override
            public void onResponse(Call<Home_Post_Response> call, Response<Home_Post_Response> response) {

                if (response.body() != null) {
                    callback.onResult(response.body().getData().getPosts(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
                }

            }

            @Override
            public void onFailure(Call<Home_Post_Response> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts( limit: " + PAGE_SIZE + ", offset: " + offset + ", where : {_or: [{post_to_community_members: {user_id: {_eq: \"181\"}}}, {post_to_followers: {userid_from: {_eq: \"181\"}}}, {created_by: {_eq: \"181\"}}]}, order_by: {created_at: desc}) {\n" +
                "    community_id\n" +
                "    content\n" +
                "    created_at\n" +
                "    created_by\n" +
                "    name\n" +
                "    pic_url\n" +
                "    post_id\n" +
                "    upvotes\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      name\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      pic_url\n" +
                "      user_id\n" +
                "      name\n" +
                "    }\n" +
                "    postUpvotesByPostId_aggregate {\n" +
                "      aggregate {\n" +
                "        count\n" +
                "      }\n" +
                "    }\n" +
                "  postUpvotesByPostId(where: {user_id: {_eq: \"181\"}}) {\n" +
                "      user_id\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getHomeFeed(body).enqueue(new Callback<Home_Post_Response>() {
            @Override
            public void onResponse(Call<Home_Post_Response> call, Response<Home_Post_Response> response) {

                if (response.body() != null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key - PAGE_SIZE : null;

                    callback.onResult(response.body().getData().getPosts(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<Home_Post_Response> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts( limit: " + PAGE_SIZE + ", offset: " + offset + ", where : {_or: [{post_to_community_members: {user_id: {_eq: \"181\"}}}, {post_to_followers: {userid_from: {_eq: \"181\"}}}, {created_by: {_eq: \"181\"}}]}, order_by: {created_at: desc}) {\n" +
                "    community_id\n" +
                "    content\n" +
                "    created_at\n" +
                "    created_by\n" +
                "    name\n" +
                "    pic_url\n" +
                "    post_id\n" +
                "    upvotes\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      name\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      pic_url\n" +
                "      user_id\n" +
                "      name\n" +
                "    }\n" +
                "    postUpvotesByPostId_aggregate {\n" +
                "      aggregate {\n" +
                "        count\n" +
                "      }\n" +
                "    }\n" +
                "  postUpvotesByPostId(where: {user_id: {_eq: \"181\"}}) {\n" +
                "      user_id\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getHomeFeed(body).enqueue(new Callback<Home_Post_Response>() {
            @Override
            public void onResponse(Call<Home_Post_Response> call, Response<Home_Post_Response> response) {

                if (response.body() != null) {
                    Integer adjacentPageKey = params.key + PAGE_SIZE;

                    callback.onResult(response.body().getData().getPosts(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<Home_Post_Response> call, Throwable t) {

            }
        });


    }
}
