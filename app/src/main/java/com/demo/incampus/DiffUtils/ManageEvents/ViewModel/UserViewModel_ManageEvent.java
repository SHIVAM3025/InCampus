package com.demo.incampus.DiffUtils.ManageEvents.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.ManageEvents.DataSource.DataSourceFactory_ManageEvent;
import com.demo.incampus.DiffUtils.ManageEvents.DataSource.DataSource_ManageEvents;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvent_Event;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvents_Data;

public class UserViewModel_ManageEvent extends ViewModel {

    private LiveData<PagedList<ManageEvent_Event>> homePagedList;
    private LiveData<PageKeyedDataSource<Integer, ManageEvent_Event>> liveDataSource;

    public LiveData<PagedList<ManageEvent_Event>> getHomePagedList() {
        return homePagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, ManageEvent_Event>> getLiveDataSource() {
        return liveDataSource;
    }

    public UserViewModel_ManageEvent() {
        //getting our data source factory
        DataSourceFactory_ManageEvent homeDataSourceFactory = new DataSourceFactory_ManageEvent();

        //getting the live data source from data source factory
        liveDataSource = homeDataSourceFactory.getHomeLiveDataSource();
//        homeDataSourceFactory.

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_ManageEvents.PAGE_SIZE).build();

        //Building the paged list
        homePagedList = (new LivePagedListBuilder(homeDataSourceFactory, pagedListConfig))
                .build();
    }

}
