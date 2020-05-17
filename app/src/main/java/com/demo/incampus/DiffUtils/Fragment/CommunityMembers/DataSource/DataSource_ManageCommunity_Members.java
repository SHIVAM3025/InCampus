package com.demo.incampus.DiffUtils.Fragment.CommunityMembers.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.CommunityMembersResponse;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_ManageCommunity_Members extends PageKeyedDataSource<Integer , CommunityMembersResponse.Community_members> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CommunityMembersResponse.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"1\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_members_relationship {\n" +
                "      member_count\n" +
                "      name\n" +
                "      pic_url\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getManageCommunity_Members(body).enqueue(new Callback<CommunityMembersResponse>() {
            @Override
            public void onResponse(Call<CommunityMembersResponse> call, Response<CommunityMembersResponse> response) {

                if (response.body()!=null) {
                    callback.onResult(response.body().getData().getCommunity_members(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
                }


            }

            @Override
            public void onFailure(Call<CommunityMembersResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CommunityMembersResponse.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"1\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_members_relationship {\n" +
                "      member_count\n" +
                "      name\n" +
                "      pic_url\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getManageCommunity_Members(body).enqueue(new Callback<CommunityMembersResponse>() {
            @Override
            public void onResponse(Call<CommunityMembersResponse> call, Response<CommunityMembersResponse> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;

                    callback.onResult(response.body().getData().getCommunity_members(), adjacentPageKey);
                }


            }

            @Override
            public void onFailure(Call<CommunityMembersResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CommunityMembersResponse.Community_members> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community_members(where: {user_id: {_eq: \"1\"}, _and: {isAdmin: {_eq: true}}}, limit: "+PAGE_SIZE+", offset: "+offset+") {\n" +
                "    community_to_members_relationship {\n" +
                "      member_count\n" +
                "      name\n" +
                "      pic_url\n" +
                "    }\n" +
                "  }\n" +
                "}\n");

        api.getManageCommunity_Members(body).enqueue(new Callback<CommunityMembersResponse>() {
            @Override
            public void onResponse(Call<CommunityMembersResponse> call, Response<CommunityMembersResponse> response) {

                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key +PAGE_SIZE;

                    callback.onResult(response.body().getData().getCommunity_members(), adjacentPageKey);
                }

            }

            @Override
            public void onFailure(Call<CommunityMembersResponse> call, Throwable t) {

            }
        });


    }



}
