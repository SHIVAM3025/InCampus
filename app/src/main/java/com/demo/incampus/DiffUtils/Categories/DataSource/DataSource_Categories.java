package com.demo.incampus.DiffUtils.Categories.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Categories.Data_Categories;
import com.demo.incampus.Model.Societies;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demo.incampus.Activity.SocietiesActivity.title;

public class DataSource_Categories extends PageKeyedDataSource<Integer, Societies> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 10;

    public String joekr = "sporty people";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Societies> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community(where: {category: {_eq: \""+title+"\"}}, limit: 10, offset: "+offset+") {\n" +
                "    pic_url\n" +
                "    name\n" +
                "    member_count\n" +
                "    community_id\n" +
                "  }\n" +
                "}\n");



        GraphqlClient.getApi().categories_list(body).enqueue(new Callback<Data_Categories>() {
            @Override
            public void onResponse(Call<Data_Categories> call, Response<Data_Categories> response) {
                        if(response.body()!=null)
                        {
                            callback.onResult(response.body().getData().getUser() ,null, FIRST_PAGE_OFFSET + 5);
                        }
            }

            @Override
            public void onFailure(Call<Data_Categories> call, Throwable t) {

            }
        });



    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Societies> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;


        body.addProperty("query" , "query MyQuery {\n" +
                "  Community(where: {category: {_eq: \""+title+"\"}}, limit: 10, offset: "+offset+") {\n" +
                "    pic_url\n" +
                "    name\n" +
                "    member_count\n" +
                "    community_id\n" +
                "  }\n" +
                "}\n");


        GraphqlClient.getApi().categories_list(body).enqueue(new Callback<Data_Categories>() {
            @Override
            public void onResponse(Call<Data_Categories> call, Response<Data_Categories> response) {

                if(response.body()!=null)
                {
                    Integer adjacentPageKey = (params.key > 0) ? params.key -5 : null;

                    callback.onResult(response.body().getData().getUser(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<Data_Categories> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Societies> callback) {


        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query" , "query MyQuery {\n" +
                "  Community(where: {category: {_eq: \""+title+"\"}}, limit: 10, offset: "+offset+") {\n" +
                "    pic_url\n" +
                "    name\n" +
                "    member_count\n" +
                "    community_id\n" +
                "  }\n" +
                "}\n");

        GraphqlClient.getApi().categories_list(body).enqueue(new Callback<Data_Categories>() {
            @Override
            public void onResponse(Call<Data_Categories> call, Response<Data_Categories> response) {

                if(response.body()!=null)
                {
                    // if response is not null "may be" we have some more data to load
                    Integer adjacentPageKey = params.key + 5;

                    callback.onResult(response.body().getData().getUser(), adjacentPageKey);

                }else {

                    Integer adjacentPageKey = null;

                    // fake entry to denote end of list because the "callback.onResult()" method
                    // requires a non-null list
                    List<Societies> list = new ArrayList<>();
                    list.add(new Societies("END", "END", "END", "END"));

                    callback.onResult(list, null);
                }

            }

            @Override
            public void onFailure(Call<Data_Categories> call, Throwable t) {

            }
        });

    }


}
