package com.demo.incampus.DiffUtils.Categories.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Categories.DataSource.DataSourceFactory_Categories;
import com.demo.incampus.DiffUtils.Categories.DataSource.DataSource_Categories;
import com.demo.incampus.Model.Societies;

public class UserViewModel_Categories extends ViewModel {

    //creating livedata for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Societies>> userPagedList;
    LiveData<PageKeyedDataSource<Integer, Societies>> liveDataSource;

    public UserViewModel_Categories() {
        //getting our data source factory
        DataSourceFactory_Categories userDataSourceFactory = new DataSourceFactory_Categories();

        //getting the live data source from data source factory
        liveDataSource = userDataSourceFactory.getUserLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_Categories.PAGE_SIZE).build();

        //Building the paged list
        userPagedList = (new LivePagedListBuilder(userDataSourceFactory, pagedListConfig))
                .build();
    }


}
