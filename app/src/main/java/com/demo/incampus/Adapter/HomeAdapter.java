package com.demo.incampus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Activity.PostDetailedActivity;
import com.demo.incampus.Activity.ProfileActivity;
import com.demo.incampus.DiffUtils.HomeActivity.Home_Post_Response.Post;
import com.demo.incampus.Model.Home;
import com.demo.incampus.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeAdapter extends PagedListAdapter<Post, HomeAdapter.HomeViewHolder> {

    private Context mCtx;
    private long time;
    private PrettyTime prettyTime;
    private OnPostClickListener onPostClickListener;

    public interface OnPostClickListener {
        void onPostClick(int position);
    }

    public void setOnPostClickListener(OnPostClickListener onPostClickListener) {
        this.onPostClickListener = onPostClickListener;
    }

    public HomeAdapter(Context context) {
        super(diffCallback);
        this.mCtx = context;
        time = 0;
        prettyTime = new PrettyTime(Locale.getDefault());
    }

    private static DiffUtil.ItemCallback<Post> diffCallback =
            new DiffUtil.ItemCallback<Post>() {
                @Override
                public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                    return oldItem.getPost_id().equals(newItem.getPost_id());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cardlayout_home, parent, false);
        return new HomeViewHolder(view, onPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {

        Post posi = getItem(position);

        String d = posi.getCreated_at();
        String[] dd = d.split("//.p");
        time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        holder.topic.setText(posi.getCommunityName());
        holder.name.setText(posi.getUserName());
        holder.time.setText(posi.getCreated_at());
        holder.content.setText(posi.getContent());
        // holder.messages.setText(posi.getUpvotes());
        holder.hearts.setText(posi.getUpvotes());
        holder.messages.setText(posi.getNo_of_comments());

        try {
            time = sdf.parse(dd[0]).getTime();
            holder.time.setText(prettyTime.format(new Date(time)));
        } catch (ParseException e) {
            //e.printStackTrace();
            holder.time.setText(prettyTime.format(new Date(time)));
        }

        try {
            if (posi.getUserPicUrl() != null) {
                Glide.with(mCtx)
                        .load(posi.getUserPicUrl())
                        .fitCenter()
                        .into(holder.profileImage);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //Click Listener for Profile Activity
        holder.profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, ProfileActivity.class);
            intent.putExtra("user_id", posi.getCreated_by());
            mCtx.startActivity(intent);
        });
        holder.name.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, ProfileActivity.class);
            intent.putExtra("user_id", posi.getCreated_by());
            mCtx.startActivity(intent);
        });

  /*      holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, PostDetailedActivity.class);

                intent.putExtra("communityName", posi.getCommunityName());
                intent.putExtra("title", posi.getName());
                intent.putExtra("userName", posi.getUserName());
                intent.putExtra("content", posi.getContent());
                intent.putExtra("time", posi.getCreated_at());

                intent.putExtra("hearts", posi.getUpvotes());
                intent.putExtra("profileImageUrl", posi.getUserPicUrl());

                mCtx.startActivity(intent);

            }
        });
*/
        //click listener for post activity
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, PostDetailedActivity.class);
                //setting flag
                intent.putExtra("flag", 0);
                mCtx.startActivity(intent);
            }
        });
        holder.downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, PostDetailedActivity.class);
                //setting flag
                intent.putExtra("flag", 0);
                mCtx.startActivity(intent);
            }
        });
        holder.commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, PostDetailedActivity.class);
                //setting flag
                intent.putExtra("flag", 1);
                mCtx.startActivity(intent);
            }
        });

        //heart
        final boolean[] like = {true};
        holder.heartImage.setImageResource(R.drawable.heart);

        holder.heartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like[0]) {
                    holder.heartImage.setImageResource(R.drawable.heart2);
                    like[0] = false;
                } else {
                    holder.heartImage.setImageResource(R.drawable.heart);
                    like[0] = true;
                }
            }
        });

    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage, downarrow, commentImage, heartImage;
        TextView topic, name, time, content, messages, hearts;


        HomeViewHolder(@NonNull View itemView, final OnPostClickListener onPostClickListener) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profilephoto);
            topic = itemView.findViewById(R.id.topic);
            name = itemView.findViewById(R.id.user_profile_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
            messages = itemView.findViewById(R.id.nmessages);
            hearts = itemView.findViewById(R.id.nhearts);
            downarrow = itemView.findViewById(R.id.downarrow);
            commentImage = itemView.findViewById(R.id.messages);
            heartImage = itemView.findViewById(R.id.heart);

            itemView.setOnClickListener(v -> {
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
