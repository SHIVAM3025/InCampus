package com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.DataSource.DataSource_ManageCommunity_Admin;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;


public class DataSourceFactory_ManageCommunity_Admin extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Community_Admin_Response.Community_members>> homeLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_ManageCommunity_Admin homeDataSource = new DataSource_ManageCommunity_Admin();

        //posting the dataSource to get the values
        homeLiveDataSource.postValue(homeDataSource);

        //returning the dataSource
        return homeDataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Community_Admin_Response.Community_members>> getHomeLiveDataSource() {

        return homeLiveDataSource;
    }
}
