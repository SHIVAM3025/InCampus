package com.demo.incampus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Activity.ProfileActivity;
import com.demo.incampus.Model.Messages;
import com.demo.incampus.R;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private Context mCtx;
    private List<Messages> messageList;

    public MessagesAdapter(Context mCtx, List<Messages> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardlayout_messages, null);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Messages posi = messageList.get(position);

        holder.profile_name__.setText(posi.getProfile_name__());
        holder.message_.setText(posi.getMessage_());
        holder.message_time_.setText(posi.getMessage_time_());
        holder.profile_photo_.setImageDrawable(mCtx.getResources().getDrawable(posi.getProfile_photo_()));

        //Click Listener for Profile Activity
        holder.profile_photo_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ProfileActivity.class);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_photo_;
        TextView profile_name__, message_, message_time_;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_photo_ = itemView.findViewById(R.id.profile_photo_);
            profile_name__ = itemView.findViewById(R.id.profile_name__);
            message_ = itemView.findViewById(R.id.message_);
            message_time_ = itemView.findViewById(R.id.message_time_);

        }
    }
}

