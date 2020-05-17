package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Model.Notifications;
import com.demo.incampus.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Context context;
    private ArrayList<Notifications> notificationsArrayList;

    public NotificationsAdapter(Context context, ArrayList<Notifications> notificationsArrayList) {
        this.context = context;
        this.notificationsArrayList = notificationsArrayList;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_notifications, null);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        Notifications posi = notificationsArrayList.get(position);

        holder.notification_title.setText(posi.getNotification_title());
        holder.notification_time.setText(posi.getNotification_time());
        holder.profile_photo_.setImageDrawable(context.getResources().getDrawable(posi.getProfile_photo_()));

    }

    @Override
    public int getItemCount() {
        return notificationsArrayList.size();
    }

    static class NotificationsViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_photo_;
        TextView notification_title, notification_time;

        NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_photo_ = itemView.findViewById(R.id.profile_photo_);
            notification_title = itemView.findViewById(R.id.notification_title);
            notification_time = itemView.findViewById(R.id.notification_time);
        }
    }
}
