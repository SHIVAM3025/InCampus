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
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Activity.PostDetailedActivity;
import com.demo.incampus.Activity.ProfileActivity;
import com.demo.incampus.DiffUtils.Explore.Explore_Event_Response.Event;
import com.demo.incampus.Model.ExplorePosts;
import com.demo.incampus.R;
import com.demo.incampus.Support.Event_Time_Conversion;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ExplorePostsAdapter extends PagedListAdapter<Event, ExplorePostsAdapter.ExploreEventsViewHolder> {

    private Context context;
    private long time;
    private PrettyTime prettyTime;

    public ExplorePostsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
        time = 0;
        prettyTime = new PrettyTime(Locale.getDefault());
    }


    private static DiffUtil.ItemCallback<Event> diffCallback =
            new DiffUtil.ItemCallback<Event>() {
                @Override
                public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.getEventId().equals(newItem.getEventId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public ExploreEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardlayout_posts_explore, null);
        return new ExploreEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreEventsViewHolder holder, int position) {

        Event posi = getItem(position);
        assert posi != null;
        String d = posi.getCreatedAt();
        String[] dd = d.split("//.p");
        time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Event_Time_Conversion event_time_conversion = new Event_Time_Conversion(posi.getTime());

        holder.topic.setText(posi.getCommunityName());
        holder.name.setText(posi.getName());
        holder.content.setText(posi.getDescription());
        holder.date.setText(posi.getDate());
        holder.event_time.setText(event_time_conversion.get_Time());

        try {
            time = sdf.parse(dd[0]).getTime();
            holder.time.setText(prettyTime.format(new Date(time)));
        } catch (ParseException e) {
            //e.printStackTrace();
            holder.time.setText(prettyTime.format(new Date(time)));
        }

        try {
            if (posi.getCommunityPicUrl() != null) {
//                holder.profileImage.setImageDrawable(context.getResources()
//                        .getDrawable(Integer.parseInt(event.getCommunityPicUrl())));

                Glide.with(context)
                        .load(posi.getCommunityPicUrl())
                        .fitCenter()
                        .into(holder.profileImage);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //Click Listener for Profile Activity
        holder.profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("user_id" ,posi.getCreatedBy());
            context.startActivity(intent);
        });
        holder.name.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("user_id" ,posi.getCreatedBy());
            context.startActivity(intent);
        });

        //click listener for post activity
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(context, PostDetailedActivity.class);
                //setting flag
               // intent.putExtra("flag", 0);
                //context.startActivity(intent);
            }
        });

        holder.downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(context, PostDetailedActivity.class);
                //setting flag
               // intent.putExtra("flag", 0);
                //context.startActivity(intent);
            }
        });

    }


    static class ExploreEventsViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage, downarrow;
        TextView topic, name, time, content, date, event_time;

        ExploreEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profilephoto);
            topic = itemView.findViewById(R.id.topic);
            name = itemView.findViewById(R.id.user_profile_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
            event_time = itemView.findViewById(R.id.event_time);
            downarrow = itemView.findViewById(R.id.downarrow);

        }
    }
}
