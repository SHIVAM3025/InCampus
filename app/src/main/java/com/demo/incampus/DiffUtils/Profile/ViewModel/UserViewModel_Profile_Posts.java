package com.demo.incampus.DiffUtils.Profile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Profile.DataSource.DataSourceFactory_Profile_Posts;
import com.demo.incampus.DiffUtils.Profile.DataSource.Data_Source_Profile_Posts;
import com.demo.incampus.Model.ProfilePostModel;

public class UserViewModel_Profile_Posts extends ViewModel {

    public LiveData<PagedList<ProfilePostModel>> userPagedList;
    LiveData<PageKeyedDataSource<Integer, ProfilePostModel>> liveDataSource;

    public UserViewModel_Profile_Posts() {
        //getting our data source factory
        DataSourceFactory_Profile_Posts userDataSourceFactory = new DataSourceFactory_Profile_Posts();

        //getting the live data source from data source factory
        liveDataSource = userDataSourceFactory.getUserLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(Data_Source_Profile_Posts.PAGE_SIZE).build();

        //Building the paged list
        userPagedList = (new LivePagedListBuilder(userDataSourceFactory, pagedListConfig))
                .build();
    }


}
