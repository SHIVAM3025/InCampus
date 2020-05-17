package com.demo.incampus.DiffUtils.Explore.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response;
import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response.Event;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_Explore_Event extends PageKeyedDataSource<Integer, Event> {
    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 4;

    public static final Api api = GraphqlClient.getApi();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Event> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Events(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {Event_to_community_members: {user_id: {_eq: \"181\"}}}, order_by: {created_at: desc}) {\n" +
                        "    name\n" +
                        "    event_id\n" +
                        "    time\n" +
                        "    registration_fees\n" +
                        "    venue\n" +
                        "    venue_type\n" +
                        "    description\n" +
                        "    date\n" +
                        "    created_by\n" +
                        "    created_at\n" +
                        "    cover_pic\n" +
                        "    community_id\n" +
                        "    category\n" +
                        "    age_limit\n" +
                        "    additional_notes\n" +
                        "    Event_to_community {\n" +
                        "      community_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"
        );

      api.getExploreEventsFeed(body).enqueue(new Callback<Explore_Event_Response>() {
          @Override
          public void onResponse(Call<Explore_Event_Response> call, Response<Explore_Event_Response> response) {

              if (response.body()!=null) {
                  callback.onResult(response.body().getData().getEvents(), null, FIRST_PAGE_OFFSET + PAGE_SIZE);
              }

          }

          @Override
          public void onFailure(Call<Explore_Event_Response> call, Throwable t) {

          }
      });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Event> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Events(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {Event_to_community_members: {user_id: {_eq: \"181\"}}}, order_by: {created_at: desc}) {\n" +
                        "    name\n" +
                        "    event_id\n" +
                        "    time\n" +
                        "    registration_fees\n" +
                        "    venue\n" +
                        "    venue_type\n" +
                        "    description\n" +
                        "    date\n" +
                        "    created_by\n" +
                        "    created_at\n" +
                        "    cover_pic\n" +
                        "    community_id\n" +
                        "    category\n" +
                        "    age_limit\n" +
                        "    additional_notes\n" +
                        "    Event_to_community {\n" +
                        "      community_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"
        );

        api.getExploreEventsFeed(body).enqueue(new Callback<Explore_Event_Response>() {
            @Override
            public void onResponse(Call<Explore_Event_Response> call, Response<Explore_Event_Response> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;
                    callback.onResult(response.body().getData().getEvents(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<Explore_Event_Response> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Event> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Events(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {Event_to_community_members: {user_id: {_eq: \"181\"}}}, order_by: {created_at: desc}) {\n" +
                        "    name\n" +
                        "    event_id\n" +
                        "    time\n" +
                        "    registration_fees\n" +
                        "    venue\n" +
                        "    venue_type\n" +
                        "    description\n" +
                        "    date\n" +
                        "    created_by\n" +
                        "    created_at\n" +
                        "    cover_pic\n" +
                        "    community_id\n" +
                        "    category\n" +
                        "    age_limit\n" +
                        "    additional_notes\n" +
                        "    Event_to_community {\n" +
                        "      community_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"
        );

        api.getExploreEventsFeed(body).enqueue(new Callback<Explore_Event_Response>() {
            @Override
            public void onResponse(Call<Explore_Event_Response> call, Response<Explore_Event_Response> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key + PAGE_SIZE;
                    callback.onResult(response.body().getData().getEvents(), adjacentPageKey);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Explore_Event_Response> call, Throwable t) {

            }
        });

    }
}
