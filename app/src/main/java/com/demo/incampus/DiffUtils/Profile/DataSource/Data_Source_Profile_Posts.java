package com.demo.incampus.DiffUtils.Profile.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Profile.POSTS.MainProfilePostData;
import com.demo.incampus.Model.ProfilePostModel;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demo.incampus.Activity.ProfileActivity.user_id;

public class Data_Source_Profile_Posts extends PageKeyedDataSource<Integer, ProfilePostModel> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 5;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ProfilePostModel> callback) {


        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, limit: 5, offset: " + offset + ", where: {created_by: {_eq: \"" + user_id + "\"}}) {\n" +
                "    pic_url\n" +
                "    created_by\n" +
                "    content\n" +
                "    upvotes\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");

        GraphqlClient.getApi().main_profile_post(body).enqueue(new Callback<MainProfilePostData>() {
            @Override
            public void onResponse(Call<MainProfilePostData> call, Response<MainProfilePostData> response) {

                if (response.body() != null) {
                    callback.onResult(response.body().getData().getUser(), null, FIRST_PAGE_OFFSET + 5);
                }

            }

            @Override
            public void onFailure(Call<MainProfilePostData> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ProfilePostModel> callback) {
        JsonObject body = new JsonObject();
        int offset = params.key;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, limit: 5, offset: " + offset + ", where: {created_by: {_eq: \"" + 101 + "\"}}) {\n" +
                "    pic_url\n" +
                "    created_by\n" +
                "    content\n" +
                "    upvotes\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");

        GraphqlClient.getApi().main_profile_post(body).enqueue(new Callback<MainProfilePostData>() {
            @Override
            public void onResponse(Call<MainProfilePostData> call, Response<MainProfilePostData> response) {

                if (response.body() != null) {
                    // if offset is greater than 0 (5, 10, ...etc)
                    // then we have a previous [set of users] to be loaded
                    Integer adjacentPageKey = (params.key > 0) ? params.key - 5 : null;

                    callback.onResult(response.body().getData().getUser(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<MainProfilePostData> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ProfilePostModel> callback) {
        JsonObject body = new JsonObject();
        int offset = params.key;
        body.addProperty("query", "query MyQuery {\n" +
                "  Posts(order_by: {created_at: desc}, limit: 5, offset: " + offset + ", where: {created_by: {_eq: \"" + 101 + "\"}}) {\n" +
                "    pic_url\n" +
                "    created_by\n" +
                "    content\n" +
                "    upvotes\n" +
                "    created_at\n" +
                "  }\n" +
                "}\n");


        GraphqlClient.getApi().main_profile_post(body).enqueue(new Callback<MainProfilePostData>() {
            @Override
            public void onResponse(Call<MainProfilePostData> call, Response<MainProfilePostData> response) {


                if (response.body() != null) {
                    // if response is not null "may be" we have some more data to load
                    Integer adjacentPageKey = params.key + 5;

                    callback.onResult(response.body().getData().getUser(), adjacentPageKey);
                } else {
                    // if response is null we "definitely" don't have more data to load
                    Integer adjacentPageKey = null;

                    // fake entry to denote end of list because the "callback.onResult()" method
                    // requires a non-null list
                    List<ProfilePostModel> list = new ArrayList<>();
                    list.add(new ProfilePostModel("END", "END", "END", "END" , "END"));

                    callback.onResult(list, null);
                }
            }

            @Override
            public void onFailure(Call<MainProfilePostData> call, Throwable t) {

            }
        });


    }
}
