package com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_ManageCommunity_Admin extends PageKeyedDataSource<Integer , Community_Admin_Response.Community_members> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Community_Admin_Response.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"101\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_member_relationship {\n" +
                "      name\n" +
                "      pic_url\n" +
                "      member_count\n" +
                "    }\n" +
                "    id\n" +
                "  }\n" +
                "}\n");

        api.getManageCommunity_Admin(body).enqueue(new Callback<Community_Admin_Response>() {
            @Override
            public void onResponse(Call<Community_Admin_Response> call, Response<Community_Admin_Response> response) {

                if (response.body()!=null) {
                    callback.onResult(response.body().getData().getCommunity_members(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
                }


            }

            @Override
            public void onFailure(Call<Community_Admin_Response> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Community_Admin_Response.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"101\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_member_relationship {\n" +
                "      name\n" +
                "      pic_url\n" +
                "      member_count\n" +
                "    }\n" +
                "    id\n" +
                "  }\n" +
                "}\n");
        api.getManageCommunity_Admin(body).enqueue(new Callback<Community_Admin_Response>() {
            @Override
            public void onResponse(Call<Community_Admin_Response> call, Response<Community_Admin_Response> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;

                    callback.onResult(response.body().getData().getCommunity_members(), adjacentPageKey);
                }


            }

            @Override
            public void onFailure(Call<Community_Admin_Response> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Community_Admin_Response.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;
        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"101\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_member_relationship {\n" +
                "      name\n" +
                "      pic_url\n" +
                "      member_count\n" +
                "    }\n" +
                "    id\n" +
                "  }\n" +
                "}\n");

        api.getManageCommunity_Admin(body).enqueue(new Callback<Community_Admin_Response>() {
            @Override
            public void onResponse(Call<Community_Admin_Response> call, Response<Community_Admin_Response> response) {

                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key +PAGE_SIZE;

                    callback.onResult(response.body().getData().getCommunity_members(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<Community_Admin_Response> call, Throwable t) {

            }
        });


    }



}
