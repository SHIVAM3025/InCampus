package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.DiffUtils.Fragment.CommunityAdmin.Community_Admin_Response;
import com.demo.incampus.Model.ManageCommunityMembers;
import com.demo.incampus.R;

import java.util.ArrayList;
import java.util.List;

public class CreateCommunityAdminAdapter extends PagedListAdapter<Community_Admin_Response.Community_members, CreateCommunityAdminAdapter.CreateCommunityAdminHolder> {

    private Context context;

    public CreateCommunityAdminAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Community_Admin_Response.Community_members> diffCallback =
            new DiffUtil.ItemCallback<Community_Admin_Response.Community_members>() {
                @Override
                public boolean areItemsTheSame(@NonNull Community_Admin_Response.Community_members oldItem, @NonNull Community_Admin_Response.Community_members newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Community_Admin_Response.Community_members oldItem, @NonNull Community_Admin_Response.Community_members newItem) {
                    return oldItem.getCommunity_to_members_relationship().getPic_url().equals(newItem.getCommunity_to_members_relationship().getPic_url());
                }
            };


    @NonNull
    @Override
    public CreateCommunityAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_admin_managecommunity, null);
        return new CreateCommunityAdminHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCommunityAdminHolder holder, int position) {

        Community_Admin_Response.Community_members posi = getItem(position);

        holder.name.setText(posi.getId());
       /* holder.followers.setText(posi.getCommunity_to_members_relationship().getMember_count() + " followers");

        Glide.with(context)
                .load(posi.getCommunity_to_members_relationship().getPic_url())
                .into(holder.profile_photo);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "EDIT FUNCTION", Toast.LENGTH_SHORT).show();
                holder.button.setVisibility(View.GONE);

            }
        });*/

    }


    static class CreateCommunityAdminHolder extends RecyclerView.ViewHolder {

        TextView name, followers;
        ImageView profile_photo;

        Button button;

        CreateCommunityAdminHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            followers = itemView.findViewById(R.id.followers);
            profile_photo = itemView.findViewById(R.id.society_photo);
            button = itemView.findViewById(R.id.button);


        }

    }

}
