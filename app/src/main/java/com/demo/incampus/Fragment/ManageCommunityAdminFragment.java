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

import com.demo.incampus.Adapter.CreateCommunityAdminAdapter;
import com.demo.incampus.Model.ManageCommunityAdmin;
import com.demo.incampus.R;

import java.util.ArrayList;

public class ManageCommunityAdminFragment extends Fragment {

    //Initiate Variables
    View view;
    RecyclerView recyclerView;
    CreateCommunityAdminAdapter adapter;
    ArrayList<ManageCommunityAdmin> createCommunityAdminArrayList;


    public ManageCommunityAdminFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_managecommunity, container, false);

        //Recycler View Code
        createCommunityAdminArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.adminRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Kalakriti", "700 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Gallery One", "350 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Chitrakar", "980 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Sketch Paint", "100 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Royal Painters", "560 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Paint Box", "280 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Canvas", "456 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Just Paint", "870 followers", R.drawable.scene));
        createCommunityAdminArrayList.add(new ManageCommunityAdmin("Art is Life", "550 followers", R.drawable.scene));


        adapter = new CreateCommunityAdminAdapter(getContext(), createCommunityAdminArrayList);

        recyclerView.setAdapter(adapter);

        return view;

    }
}
