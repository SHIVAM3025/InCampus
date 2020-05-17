package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.paging.PagedListAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.DiffUtils.Comments.CommentsResponse.Comment;
import com.demo.incampus.Model.Comments;
import com.demo.incampus.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentsAdapter extends PagedListAdapter<Comment , CommentsAdapter.CommentsViewHolder >
{
    private Context context;
    private long time;
    private PrettyTime prettyTime;

    public CommentsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
        time = 0;
        prettyTime = new PrettyTime(Locale.getDefault());
    }

    private static DiffUtil.ItemCallback<Comment> diffCallback =
            new DiffUtil.ItemCallback<Comment>() {
                @Override
                public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
                    return oldItem.equals(newItem);
                }
            };



    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_comments,parent,false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        Comment comment = getItem(position);
        String[] dd = comment.getTimestamp().split("//.p");
        time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        holder.username.setText(comment.getUserName());
        holder.commentText.setText(comment.getContent());
        holder.hearts.setText(comment.getUpvotes());

        try {
            time = sdf.parse(dd[0]).getTime();
            holder.time.setText(prettyTime.format(new Date(time)));
        } catch (ParseException e) {
            holder.time.setText(prettyTime.format(new Date(time)));
        }

    }

   static class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView time;
        TextView commentText;
        TextView hearts;

        TextView view_replies_text;
        TextView reply_text;
        ImageView reply_icon;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            time = itemView.findViewById(R.id.time);
            commentText = itemView.findViewById(R.id.commentText);
            hearts = itemView.findViewById(R.id.hearts);

            view_replies_text = itemView.findViewById(R.id.replies);
            reply_text = itemView.findViewById(R.id.reply_text);
            reply_icon = itemView.findViewById(R.id.reply_icon);

            view_replies_text.setVisibility(View.GONE);
            reply_text.setVisibility(View.GONE);
            reply_icon.setVisibility(View.GONE);
        }
    }

}