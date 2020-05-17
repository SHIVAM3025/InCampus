package com.demo.incampus.Interface;

import com.demo.incampus.CommonGraphqlModels.CommentCountResponse;
import com.demo.incampus.CommonGraphqlModels.InsertCommentResponse;
import com.demo.incampus.DiffUtils.Categories.Data_Categories;
import com.demo.incampus.DiffUtils.Comments.CommentsResponse;
import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.Community_Members_Response;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvents_Data;
import com.demo.incampus.DiffUtils.Profile.CommunityNumber.Community_Number_Data;
import com.demo.incampus.DiffUtils.Profile.FollowNumber.Followers_Number_Data;
import com.demo.incampus.DiffUtils.Profile.MainProfile.MainProfileData;
import com.demo.incampus.DiffUtils.Profile.POSTS.MainProfilePostData;
import com.demo.incampus.Model.Phone;
import com.demo.incampus.Model.Register;
import com.demo.incampus.Query.Profile_Post_Eligibility.CreatePostData;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("register")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Register> register(
            @Field("email") String username,
            @Field("username") String name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<Register> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("token")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<ResponseBody> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code);

    @FormUrlEncoded
    @POST("otp")
        // @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoieWFzaCIsImlhdCI6MTU4NDk2OTQ3MH0.y5elEi_0HoBx8H9pYn7p5qSn-669lixXK4RYYSvsBps")
    Call<Phone> otpReceive(
            @Header("Authorization") String header,
            @Field("phone_number") String phoneNumber
    );

    @FormUrlEncoded
    @POST("verifyotp")
    Call<ResponseBody> verifyotp(
            @Field("otp") String OTP,
            @Field("session_id") String sessionID);

    @FormUrlEncoded
    @POST("social/android/auth/google")
    Call<ResponseBody> google_id_token(
            @Field("id_token") String id_token_google
    );

    @FormUrlEncoded
    @POST("username/search/")
    Call<String> username_search(
            @Field("username") String username
    );

    @POST("graphql/")
    Call<JsonObject> graphql(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<Data_Categories> categories_list(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<Community_Number_Data> community_numbers(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<Followers_Number_Data> graphql_follow_count(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<MainProfileData> profile_show(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<MainProfilePostData> main_profile_post(
            @Body JsonObject jsonObject
    );

    @POST("graphql/")
    Call<CreatePostData> query_to_check_home_post_eigibility(
            @Body JsonObject jsonObject
    );

    @POST("graphql")
    Call<Home_Post_Response> getHomeFeed(@Body JsonObject body);

    @POST("graphql")
    Call<Explore_Event_Response> getExploreEventsFeed(@Body JsonObject body);

    @POST("graphql/")
    Call<ManageEvents_Data> ManageEvents(@Body JsonObject body);

    @POST("graphql/")
    Call<JsonObject> createEvent(@Body JsonObject body);

    @POST("graphql/")
    Call<Community_Admin_Response> getManageCommunity_Admin(@Body JsonObject body);

    @POST("graphql/")
    Call<Community_Members_Response> getManageCommunity_Members(@Body JsonObject body);

    @POST("graphql/")
    Call<CommentsResponse> getCommentsOnPost(@Body JsonObject body);

    @POST("graphql")
    Call<InsertCommentResponse> insertComment(@Body JsonObject body);

    @POST("graphql")
    Call<CommentCountResponse> getCommentCount(@Body JsonObject body);

}

