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

import com.demo.incampus.Adapter.CreateCommunityMembersAdapter;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.Community_Members_Response;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.ViewModel.UserViewModel_Manage_Community_Members;
import com.demo.incampus.R;

import java.util.ArrayList;

public class ManageCommunityMembersFragment extends Fragment {

    //Initiate Variables
    View view;
    RecyclerView recyclerView;
    CreateCommunityMembersAdapter adapter;
    private LiveData<PagedList<Community_Members_Response.Community_members>> homePagedList;
   // CreateCommunityMembersAdapter adapter;

    public ManageCommunityMembersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_members_managecommunity, container, false);

        //Recycler View Code
        UserViewModel_Manage_Community_Members homeViewModel = ViewModelProviders.of(this).get(UserViewModel_Manage_Community_Members.class);
        recyclerView = view.findViewById(R.id.membersRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CreateCommunityMembersAdapter(getActivity());

        homePagedList = homeViewModel.getHomePagedList();
        homePagedList.observe(getActivity(), homes -> adapter.submitList(homes));

       recyclerView.setAdapter(adapter);
        return view;

    }
}
