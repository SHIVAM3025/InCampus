package com.demo.incampus.DiffUtils.Comments.DataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.demo.incampus.DiffUtils.Comments.CommentsResponse.Comment;

public class DataSourceFactory_Comment extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Comment>> commentsLiveDataSource =
            new MutableLiveData<>();

    private int postId;

    public DataSourceFactory_Comment(int postId) {
        this.postId = postId;
    }

    @Override
    public DataSource create() {
        //getting our data source object
        DataSource_Comment dataSource = new DataSource_Comment(postId);

        //posting the dataSource to get the values
        commentsLiveDataSource.postValue(dataSource);

        //returning the dataSource
        return dataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Comment>> getCommentsLiveDataSource() {
        return commentsLiveDataSource;
    }
}
