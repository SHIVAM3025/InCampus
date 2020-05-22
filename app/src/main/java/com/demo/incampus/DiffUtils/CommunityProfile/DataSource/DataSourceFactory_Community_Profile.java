package com.demo.incampus.DiffUtils.CommunityProfile.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response.Posts;


public class DataSourceFactory_Community_Profile extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Posts>> eventsLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_Community_Profile dataSource = new DataSource_Community_Profile();

        //posting the dataSource to get the values
        eventsLiveDataSource.postValue(dataSource);

        //returning the dataSource
        return dataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> getEventsLiveDataSource() {
        return eventsLiveDataSource;
    }
}
