package com.demo.incampus.DiffUtils.CommunityProfile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response.Posts;
import com.demo.incampus.DiffUtils.CommunityProfile.DataSource.DataSourceFactory_Community_Profile;
import com.demo.incampus.DiffUtils.CommunityProfile.DataSource.DataSource_Community_Profile;

public class UserViewModel_Community_Profile extends ViewModel {


    //creating livedata for PagedList and PagedKeyedDataSource
    private LiveData<PagedList<Posts>> eventsPagedList;
    private LiveData<PageKeyedDataSource<Integer, Posts>> liveDataSource;

    public LiveData<PagedList<Posts>> getEventsPagedList() {
        return eventsPagedList;
    }

    public UserViewModel_Community_Profile() {
        //getting our data source factory
        DataSourceFactory_Community_Profile dataSourceFactory = new DataSourceFactory_Community_Profile();

        //getting the live data source from data source factory
        liveDataSource=dataSourceFactory.getEventsLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_Community_Profile.PAGE_SIZE).build();

        //Building the paged list
        eventsPagedList = (new LivePagedListBuilder(dataSourceFactory, pagedListConfig))
                .build();
    }

}
