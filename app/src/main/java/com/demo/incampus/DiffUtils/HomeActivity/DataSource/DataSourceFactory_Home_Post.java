package com.demo.incampus.DiffUtils.HomeActivity.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response.Post;

public class DataSourceFactory_Home_Post extends DataSource.Factory{
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Post>> homeLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_Home_Post homeDataSource = new DataSource_Home_Post();

        //posting the dataSource to get the values
        homeLiveDataSource.postValue(homeDataSource);

        //returning the dataSource
        return homeDataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Post>> getHomeLiveDataSource() {
        return homeLiveDataSource;
    }
}
