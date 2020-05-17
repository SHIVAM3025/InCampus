package com.demo.incampus.DiffUtils.Categories.DataSource;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.Model.Societies;

public class DataSourceFactory_Categories extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Societies>> userLiveDataSource =
            new MutableLiveData<>();

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_Categories userDataSource = new DataSource_Categories();

        //posting the dataSource to get the values
        userLiveDataSource.postValue(userDataSource);

        //returning the dataSource
        return userDataSource;
    }

    //getter for userLiveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Societies>> getUserLiveDataSource() {
        return userLiveDataSource;
    }

}
