package com.demo.incampus.DiffUtils.Comments.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Comments.CommentsResponse;
import com.demo.incampus.DiffUtils.Comments.CommentsResponse.Comment;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource_Comment extends PageKeyedDataSource<Integer, Comment> {

    public static final int FIRST_PAGE_OFFSET = 0;

    public static final int PAGE_SIZE = 2;

    public static final Api api = GraphqlClient.getApi();

    private int post_id;

    public DataSource_Comment(int post_id) {
        this.post_id = post_id;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Comment> callback) {

        JsonObject body = new JsonObject();
        int offset = FIRST_PAGE_OFFSET;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Comments(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {post_id: {_eq: \"" + post_id + "\"}}, order_by: {timestamp: desc}) {\n" +
                        "    UserID {\n" +
                        "      user_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "    content\n" +
                        "    upvotes\n" +
                        "    user_id\n" +
                        "    id\n" +
                        "    timestamp\n" +
                        "  }\n" +
                        "}"
        );

        api.getCommentsOnPost(body).enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body()!=null) {
                    callback.onResult(response.body().getData().getComments(),null,
                            FIRST_PAGE_OFFSET + PAGE_SIZE);
                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Comment> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Comments(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {post_id: {_eq: \"" + post_id + "\"}}, order_by: {timestamp: desc}) {\n" +
                        "    UserID {\n" +
                        "      user_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "    content\n" +
                        "    upvotes\n" +
                        "    user_id\n" +
                        "    id\n" +
                        "    timestamp\n" +
                        "  }\n" +
                        "}"
        );

        api.getCommentsOnPost(body).enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = (params.key > FIRST_PAGE_OFFSET) ? params.key -PAGE_SIZE : null;
                    callback.onResult(response.body().getData().getComments(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Comment> callback) {

        JsonObject body = new JsonObject();
        int offset = params.key;

        body.addProperty("query",
                "query MyQuery {\n" +
                        "  Comments(limit: " + PAGE_SIZE + ", offset: " + offset + ", where: {post_id: {_eq: \"" + post_id + "\"}}, order_by: {timestamp: desc}) {\n" +
                        "    UserID {\n" +
                        "      user_id\n" +
                        "      name\n" +
                        "      pic_url\n" +
                        "    }\n" +
                        "    content\n" +
                        "    upvotes\n" +
                        "    user_id\n" +
                        "    id\n" +
                        "    timestamp\n" +
                        "  }\n" +
                        "}"
        );

        api.getCommentsOnPost(body).enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body()!=null) {
                    Integer adjacentPageKey = params.key + PAGE_SIZE;
                    callback.onResult(response.body().getData().getComments(), adjacentPageKey);
                } else {

                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {

            }
        });


    }
}
