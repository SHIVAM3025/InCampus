package com.demo.incampus.DiffUtils.Comments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.incampus.DiffUtils.Comments.ViewModel.UserViewModel_Comment;

public class ViewModelFactory_Comments implements ViewModelProvider.Factory{

    private int postId;
    private Application mApplication;

    public ViewModelFactory_Comments(Application mApplication, int postId) {
        this.mApplication = mApplication;
        this.postId = postId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserViewModel_Comment(mApplication, postId);
    }
}
