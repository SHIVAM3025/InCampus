package com.demo.incampus.DiffUtils.ManageEvents.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.ManageEvents.ManageEvent_Event;

public class DataSourceFactory_ManageEvent extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, ManageEvent_Event>> homeLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_ManageEvents homeDataSource = new DataSource_ManageEvents();

        //posting the dataSource to get the values
        homeLiveDataSource.postValue(homeDataSource);

        //returning the dataSource
        return homeDataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, ManageEvent_Event>> getHomeLiveDataSource() {
        return homeLiveDataSource;
    }
}
