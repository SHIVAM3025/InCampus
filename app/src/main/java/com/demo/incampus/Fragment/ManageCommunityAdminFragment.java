package com.demo.incampus.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.CreateCommunityAdminAdapter;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.ViewModel.UserViewModel_Manage_Community_Admin;
import com.demo.incampus.R;

import java.util.ArrayList;

public class ManageCommunityAdminFragment extends Fragment {

    //Initiate Variables
    View view;
    RecyclerView recyclerView;
    CreateCommunityAdminAdapter adapter;
    private LiveData<PagedList<Community_Admin_Response.Community_members>> homePagedList;


    public ManageCommunityAdminFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_managecommunity, container, false);

        //Recycler View Code
        recyclerView = view.findViewById(R.id.adminRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserViewModel_Manage_Community_Admin homeViewModel = ViewModelProviders.of(this).get(UserViewModel_Manage_Community_Admin.class);

        adapter = new CreateCommunityAdminAdapter(getActivity());

        homePagedList = homeViewModel.getHomePagedList();
        homePagedList.observe(getActivity(), homes -> adapter.submitList(homes));

        recyclerView.setAdapter(adapter);

        return view;

    }
}
