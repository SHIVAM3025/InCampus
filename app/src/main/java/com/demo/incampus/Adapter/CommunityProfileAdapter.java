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
import com.demo.incampus.DiffUtils.CommunityProfile.Community_Profile_Response.Community_profile;
import com.demo.incampus.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityProfileAdapter extends PagedListAdapter<Community_profile, CommunityProfileAdapter.CommunityProfileViewHolder> {

    private Context context;
    private OnPostClickListener onPostClickListener;

    public interface OnPostClickListener {
        void onPostClick(int position);
    }


    public CommunityProfileAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Community_profile> diffCallback =
            new DiffUtil.ItemCallback<Community_profile>() {
                @Override
                public boolean areItemsTheSame(@NonNull Community_profile oldItem, @NonNull Community_profile newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                    return oldItem.getPost_community().getPic_url().equals(newItem.getPost_community().getPic_url());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Community_profile oldItem, @NonNull Community_profile newItem) {
                    return oldItem.getPost_community().getPic_url().equals(newItem.getPost_community().getPic_url());
                }
            };


    @NonNull
    @Override
    public CommunityProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_profile_adapter, null);
        return new CommunityProfileViewHolder(view, onPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityProfileViewHolder holder, int position) {

        Community_profile post = getItem(position);

        holder.content.setText(post.getContent());
        holder.name.setText(post.getName());
        holder.time.setText(post.getCreated_at());

        if (post.getPost_community().getPic_url() != null) {
            Glide.with(context)
                    .load(post.getPost_community().getPic_url())
                    .into(holder.profilephoto);
        }


    }

    public static class CommunityProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView delete_forever;
        TextView content, name, time;
        CircleImageView profilephoto;

        CommunityProfileViewHolder(@NonNull View itemView, final OnPostClickListener onPostClickListener) {
            super(itemView);

            delete_forever = itemView.findViewById(R.id.delete_forever);
            content = itemView.findViewById(R.id.content);
            name = itemView.findViewById(R.id.user_profile_name);
            time = itemView.findViewById(R.id.time);
            profilephoto = itemView.findViewById(R.id.profilephoto);

            delete_forever.setOnClickListener(v -> {
                if (onPostClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onPostClickListener.onPostClick(position);
                    }
                }
            });

        }
    }
}
