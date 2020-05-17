package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.Community_Members_Response;
import com.demo.incampus.R;

public class CreateCommunityMembersAdapter extends PagedListAdapter<Community_Members_Response.Community_members, CreateCommunityMembersAdapter.CreateCommunityMemberHolder> {

    private Context context;

    public CreateCommunityMembersAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Community_Members_Response.Community_members> diffCallback =
            new DiffUtil.ItemCallback<Community_Members_Response.Community_members>() {
                @Override
                public boolean areItemsTheSame(@NonNull Community_Members_Response.Community_members oldItem, @NonNull Community_Members_Response.Community_members newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Community_Members_Response.Community_members oldItem, @NonNull Community_Members_Response.Community_members newItem) {
                    return oldItem.getCommunity_to_members_relationship().getPic_url().equals(newItem.getCommunity_to_members_relationship().getPic_url());
                }
            };


    @NonNull
    @Override
    public CreateCommunityMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_members_managecommunity, null);
        return new CreateCommunityMemberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCommunityMemberHolder holder, int position) {

        Community_Members_Response.Community_members posi = getItem(position);

        holder.name.setText(posi.getCommunity_to_members_relationship().getName());
        holder.followers.setText(posi.getCommunity_to_members_relationship().getMember_count() + " followers");

        Glide.with(context)
                .load(posi.getCommunity_to_members_relationship().getPic_url())
                .into(holder.profile_photo);

    }


    static class CreateCommunityMemberHolder extends RecyclerView.ViewHolder {

        TextView name, followers;
        ImageView profile_photo;

        CreateCommunityMemberHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            followers = itemView.findViewById(R.id.followers);
            profile_photo = itemView.findViewById(R.id.profile_photo);


        }

    }


}
