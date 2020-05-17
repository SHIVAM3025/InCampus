package com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.DataSource.DataSource_ManageCommunity_Admin;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.DataSource.DataSourceFactory_ManageCommunity_Admin;


public class UserViewModel_Manage_Community_Admin extends ViewModel {

    private LiveData<PagedList<Community_Admin_Response.Community_members>> homePagedList;
    private LiveData<PageKeyedDataSource<Integer, Community_Admin_Response.Community_members>> liveDataSource;

    public LiveData<PagedList<Community_Admin_Response.Community_members>> getHomePagedList() {
        return homePagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, Community_Admin_Response.Community_members>> getLiveDataSource() {
        return liveDataSource;
    }

    public UserViewModel_Manage_Community_Admin() {
        //getting our data source factory
        DataSourceFactory_ManageCommunity_Admin homeDataSourceFactory = new DataSourceFactory_ManageCommunity_Admin();

        //getting the live data source from data source factory
        liveDataSource = homeDataSourceFactory.getHomeLiveDataSource();
//        homeDataSourceFactory.

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_ManageCommunity_Admin.PAGE_SIZE).build();

        //Building the paged list
        homePagedList = (new LivePagedListBuilder(homeDataSourceFactory, pagedListConfig))
                .build();
    }


}
