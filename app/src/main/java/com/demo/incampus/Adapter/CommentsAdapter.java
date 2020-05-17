package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Model.Comments;
import com.demo.incampus.R;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Context context;
    private ArrayList<Comments> comments;

    public CommentsAdapter(Context context, ArrayList<Comments> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_comments, null);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        Comments posi = comments.get(position);

        holder.username.setText(posi.getUsername());
        holder.time.setText(posi.getTime());
        holder.commentText.setText(posi.getCommentText());
        holder.hearts.setText(posi.getHearts());
        holder.replies.setText(posi.getReplies());
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView username, time, commentText, hearts, replies;

        CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            time = itemView.findViewById(R.id.time);
            commentText = itemView.findViewById(R.id.commentText);
            hearts = itemView.findViewById(R.id.hearts);
            replies = itemView.findViewById(R.id.replies);

        }
    }
}
