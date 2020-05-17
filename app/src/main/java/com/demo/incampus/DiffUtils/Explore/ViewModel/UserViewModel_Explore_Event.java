package com.demo.incampus.DiffUtils.Explore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Explore.DataSource.DataSourceFactory_Explore_Event;
import com.demo.incampus.DiffUtils.Explore.DataSource.DataSource_Explore_Event;
import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response.Event;

public class UserViewModel_Explore_Event extends ViewModel {

    //creating livedata for PagedList and PagedKeyedDataSource
    private LiveData<PagedList<Event>> eventsPagedList;
    private LiveData<PageKeyedDataSource<Integer, Event>> liveDataSource;

    public LiveData<PagedList<Event>> getEventsPagedList() {
        return eventsPagedList;
    }

    public UserViewModel_Explore_Event() {
        //getting our data source factory
        DataSourceFactory_Explore_Event dataSourceFactory = new DataSourceFactory_Explore_Event();

        //getting the live data source from data source factory
        liveDataSource=dataSourceFactory.getEventsLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_Explore_Event.PAGE_SIZE).build();

        //Building the paged list
        eventsPagedList = (new LivePagedListBuilder(dataSourceFactory, pagedListConfig))
                .build();
    }

}
