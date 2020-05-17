package com.demo.incampus.DiffUtils.Fragment.CommunityMembers.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.Community_Members_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.DataSource.DataSourceFactory_ManageCommunity_Members;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.DataSource.DataSource_ManageCommunity_Members;

public class UserViewModel_Manage_Community_Members extends ViewModel {


    private LiveData<PagedList<Community_Members_Response.Community_members>> homePagedList;
    private LiveData<PageKeyedDataSource<Integer, Community_Members_Response.Community_members>> liveDataSource;

    public LiveData<PagedList<Community_Members_Response.Community_members>> getHomePagedList() {
        return homePagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, Community_Members_Response.Community_members>> getLiveDataSource() {
        return liveDataSource;
    }

    public UserViewModel_Manage_Community_Members() {
        //getting our data source factory
        DataSourceFactory_ManageCommunity_Members homeDataSourceFactory = new DataSourceFactory_ManageCommunity_Members();

        //getting the live data source from data source factory
        liveDataSource = homeDataSourceFactory.getHomeLiveDataSource();
//        homeDataSourceFactory.

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_ManageCommunity_Members.PAGE_SIZE).build();

        //Building the paged list
        homePagedList = (new LivePagedListBuilder(homeDataSourceFactory, pagedListConfig))
                .build();
    }



}
