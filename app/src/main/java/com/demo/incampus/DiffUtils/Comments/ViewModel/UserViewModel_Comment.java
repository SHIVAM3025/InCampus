package com.demo.incampus.DiffUtils.Comments.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.demo.incampus.DiffUtils.Comments.CommentsResponse.Comment;
import com.demo.incampus.DiffUtils.Comments.DataSource.DataSourceFactory_Comment;
import com.demo.incampus.DiffUtils.Comments.DataSource.DataSource_Comment;

public class UserViewModel_Comment extends ViewModel {

    private Application mApplication;
    private int postId;

    //creating livedata for PagedList and PagedKeyedDataSource
    private LiveData<PagedList<Comment>> commentsPagedList;
    private LiveData<PageKeyedDataSource<Integer, Comment>> liveDataSource;

    public LiveData<PagedList<Comment>> getCommentsPagedList() {
        return commentsPagedList;
    }

    public LiveData<PageKeyedDataSource<Integer, Comment>> getLiveDataSource() {
        return liveDataSource;
    }

    public UserViewModel_Comment(Application mApplication, int postId) {
        this.mApplication = mApplication;
        this.postId = postId;

        //getting our data source factory
        DataSourceFactory_Comment dataSourceFactory = new DataSourceFactory_Comment(postId);

        //getting the live data source from data source factory
        liveDataSource=dataSourceFactory.getCommentsLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(DataSource_Comment.PAGE_SIZE).build();

        //Building the paged list
        commentsPagedList = (new LivePagedListBuilder(dataSourceFactory, pagedListConfig))
                .build();
    }

}
