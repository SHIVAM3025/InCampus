package com.demo.incampus.DiffUtils.Fragment.CommunityMembers.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.CommunityMembersResponse;

public class DataSourceFactory_ManageCommunity_Members extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, CommunityMembersResponse.Community_members>> homeLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_ManageCommunity_Members homeDataSource = new DataSource_ManageCommunity_Members();

        //posting the dataSource to get the values
        homeLiveDataSource.postValue(homeDataSource);

        //returning the dataSource
        return homeDataSource;
    }

    //getter for homeLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, CommunityMembersResponse.Community_members>> getHomeLiveDataSource() {

        return homeLiveDataSource;
    }
}
