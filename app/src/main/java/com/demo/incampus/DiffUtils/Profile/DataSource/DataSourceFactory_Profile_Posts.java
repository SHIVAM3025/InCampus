package com.demo.incampus.DiffUtils.Profile.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.Model.ProfilePostModel;

public class DataSourceFactory_Profile_Posts extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, ProfilePostModel>> userLiveDataSource =
            new MutableLiveData<>();



    //getter for userLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, ProfilePostModel>> getUserLiveDataSource() {
        return userLiveDataSource;
    }


    @Override
    public DataSource create() {
        Data_Source_Profile_Posts userDataSource = new Data_Source_Profile_Posts();

        //posting the dataSource to get the values
        userLiveDataSource.postValue(userDataSource);

        //returning the dataSource
        return userDataSource;
    }
}
