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
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.CommunityMembersResponse;
import com.demo.incampus.DiffUtils.Fragment.CommunityMembers.CommunityMembersResponse.community_to_members_relationship;
import com.demo.incampus.Model.ManageCommunityMembers;
import com.demo.incampus.R;

import java.util.ArrayList;
import java.util.List;

public class CreateCommunityMembersAdapter extends PagedListAdapter<CommunityMembersResponse.Community_members, CreateCommunityMembersAdapter.CreateCommunityMembersHolder> {

    private Context context;

    public CreateCommunityMembersAdapter( Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<CommunityMembersResponse.Community_members> diffCallback =
            new DiffUtil.ItemCallback<CommunityMembersResponse.Community_members>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommunityMembersResponse.Community_members oldItem, @NonNull CommunityMembersResponse.Community_members newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                   return oldItem.toString().equals(newItem.toString());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommunityMembersResponse.Community_members oldItem, @NonNull CommunityMembersResponse.Community_members newItem) {
                    return oldItem.getCommunity_to_members_relationshipList().get(0).getList().getPic_url().equals(newItem.getCommunity_to_members_relationshipList().get(0).getList().getPic_url());
                }
            };


    @NonNull
    @Override
    public CreateCommunityMembersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_members_managecommunity, null);
        return new CreateCommunityMembersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCommunityMembersHolder holder, int position) {

        CommunityMembersResponse.Community_members posi = getItem(position);

        holder.name.setText(posi.getCommunity_to_members_relationshipList().get(position).getList().getName());
  /*      holder.followers.setText(posi.getList().get(0).getMember_count());

        Glide.with(context)
                .load(posi.getList().get(0).getPic_url())
                .into(holder.profile_photo);
*/
    }


    static class CreateCommunityMembersHolder extends RecyclerView.ViewHolder {

        TextView name, followers;
        ImageView profile_photo;

        CreateCommunityMembersHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            followers = itemView.findViewById(R.id.followers);
            profile_photo = itemView.findViewById(R.id.profile_photo);


        }

    }

}
