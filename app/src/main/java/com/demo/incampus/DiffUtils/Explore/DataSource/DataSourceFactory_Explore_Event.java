package com.demo.incampus.DiffUtils.Explore.DataSource;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response.Event;

public class DataSourceFactory_Explore_Event extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Event>> eventsLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_Explore_Event dataSource = new DataSource_Explore_Event();

        //posting the dataSource to get the values
        eventsLiveDataSource.postValue(dataSource);

        //returning the dataSource
        return dataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Event>> getEventsLiveDataSource() {
        return eventsLiveDataSource;
    }
}
