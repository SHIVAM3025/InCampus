package com.demo.incampus.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Adapter.ProfilePostsAdapter;
import com.demo.incampus.R;

public class ProfilePostsFragment extends Fragment {

    //Initiate Variables
    View view;
    RecyclerView recyclerView;
    ProfilePostsAdapter adapter;

    public ProfilePostsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_posts_profile, container, false);

        //Recycler View Code

        recyclerView = view.findViewById(R.id.profileRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




      //  adapter = new ProfilePostsAdapter(profileList, getContext());

        //recyclerView.setAdapter(adapter);
        return view;
    }
}
