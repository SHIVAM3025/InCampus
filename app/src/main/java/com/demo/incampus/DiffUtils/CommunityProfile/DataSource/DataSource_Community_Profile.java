package com.demo.incampus.DiffUtils.CommunityProfile.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response;
import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response.Posts;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_Community_Profile extends PageKeyedDataSource<Integer, Posts> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Posts> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query"  , "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, where: {community_id: {_eq: \"200\"}}) {\n" +
                "    content\n" +
                "    name\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      pic_url\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      name\n" +
                "    }\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");

        api.getCommunityProfile(body).enqueue(new Callback<Community_Profile_Response>() {
            @Override
            public void onResponse(Call<Community_Profile_Response> call, Response<Community_Profile_Response> response) {

                if (response.body()!=null) {
                    callback.onResult(response.body().getData().getPosts(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
                }
            }

            @Override
            public void onFailure(Call<Community_Profile_Response> call, Throwable t) {

            }
        });



    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Posts> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;


        body.addProperty("query"  , "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, where: {community_id: {_eq: \"200\"}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    content\n" +
                "    name\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      pic_url\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      name\n" +
                "    }\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");

        api.getCommunityProfile(body).enqueue(new Callback<Community_Profile_Response>() {
            @Override
            public void onResponse(Call<Community_Profile_Response> call, Response<Community_Profile_Response> response) {

                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;
                    callback.onResult(response.body().getData().getPosts(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<Community_Profile_Response> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Community_Profile_Response.Posts> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query"  , "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, where: {community_id: {_eq: \"200\"}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    content\n" +
                "    name\n" +
                "    no_of_comments\n" +
                "    post_community {\n" +
                "      pic_url\n" +
                "    }\n" +
                "    post_to_user {\n" +
                "      name\n" +
                "    }\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");

        api.getCommunityProfile(body).enqueue(new Callback<Community_Profile_Response>() {
            @Override
            public void onResponse(Call<Community_Profile_Response> call, Response<Community_Profile_Response> response) {

                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key + PAGE_SIZE;
                    callback.onResult(response.body().getData().getPosts(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<Community_Profile_Response> call, Throwable t) {

            }
        });

    }
}
