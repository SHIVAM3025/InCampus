package com.demo.incampus.DiffUtils.HomeActivity.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.HomeActivity.DataSource.DataSourceFactory_Home_Post;
import com.demo.incampus.DiffUtils.HomeActivity.DataSource.DataSource_Home_Post;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response.Post;

public class UserViewModel_Home_Post extends ViewModel {

    private LiveData<PagedList<Post>> homePagedList;
    private LiveData<PageKeyedDataSource<Integer, Post>> liveDataSource;

    public LiveData<PagedList<Post>> getHomePagedList() {
        return homePagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, Post>> getLiveDataSource() {
        return liveDataSource;
    }

    public UserViewModel_Home_Post() {
        //getting our data source factory
        DataSourceFactory_Home_Post homeDataSourceFactory = new DataSourceFactory_Home_Post();

        //getting the live data source from data source factory
        liveDataSource = homeDataSourceFactory.getHomeLiveDataSource();
//        homeDataSourceFactory.

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_Home_Post.PAGE_SIZE).build();

        //Building the paged list
        homePagedList = (new LivePagedListBuilder(homeDataSourceFactory, pagedListConfig))
                .build();
    }


}
