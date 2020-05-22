package com.demo.incampus.Network;

import com.demo.incampus.Interface.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScalarsClient {

    private static final String LOGIN_BASE_URL="https://incampusbackend.herokuapp.com/api/v1/";
    private static ScalarsClient mInstance;
    private Retrofit retrofit;

    private ScalarsClient()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(LOGIN_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static synchronized ScalarsClient getInstance()
    {
        if(mInstance==null)
        {
            mInstance=new ScalarsClient();
        }
        return mInstance;
    }

    public Api getApi()
    {
        return retrofit.create(Api.class);
    }

}
