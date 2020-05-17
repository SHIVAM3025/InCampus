package com.demo.incampus.DiffUtils.ManageEvents.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvent_Event;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvents_Data;

import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_ManageEvents extends PageKeyedDataSource<Integer , ManageEvent_Event> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ManageEvent_Event> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;


        body.addProperty("query" , "query MyQuery {\n" +
                "  Events(where: {created_by: {_eq: \"181\"}}, order_by: {created_at: desc}, limit: 5, offset: "+offset+") {\n" +
                "    cover_pic\n" +
                "    name\n" +
                "    event_id\n" +
                "    Event_to_community {\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}\n");


        api.ManageEvents(body).enqueue(new Callback<ManageEvents_Data>() {
            @Override
            public void onResponse(Call<ManageEvents_Data> call, Response<ManageEvents_Data> response) {
                if (response.body()!=null) {
                    callback.onResult(response.body().getEvents_events().getEvent(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
                }
            }

            @Override
            public void onFailure(Call<ManageEvents_Data> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ManageEvent_Event> callback) {
        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Events(where: {created_by: {_eq: \"181\"}}, order_by: {created_at: desc}, limit: 5, offset: "+offset+") {\n" +
                "    cover_pic\n" +
                "    name\n" +
                "    event_id\n" +
                "    Event_to_community {\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}\n");


        api.ManageEvents(body).enqueue(new Callback<ManageEvents_Data>() {
            @Override
            public void onResponse(Call<ManageEvents_Data> call, Response<ManageEvents_Data> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;

                    callback.onResult(response.body().getEvents_events().getEvent(), adjacentPageKey);
                }


            }

            @Override
            public void onFailure(Call<ManageEvents_Data> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ManageEvent_Event> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Events(where: {created_by: {_eq: \"181\"}}, order_by: {created_at: desc}, limit: 5, offset: "+offset+") {\n" +
                "    cover_pic\n" +
                "    name\n" +
                "    event_id\n" +
                "    Event_to_community {\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}\n");


        api.ManageEvents(body).enqueue(new Callback<ManageEvents_Data>() {
            @Override
            public void onResponse(Call<ManageEvents_Data> call, Response<ManageEvents_Data> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key +PAGE_SIZE;

                    callback.onResult(response.body().getEvents_events().getEvent(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<ManageEvents_Data> call, Throwable t) {

            }
        });



    }
}
