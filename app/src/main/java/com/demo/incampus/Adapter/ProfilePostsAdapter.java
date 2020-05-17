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
import com.demo.incampus.Model.ProfilePostModel;
import com.demo.incampus.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfilePostsAdapter extends PagedListAdapter<ProfilePostModel, ProfilePostsAdapter.ProfileViewHolder> {

    private Context context;
    private PrettyTime prettyTime;
    private long time;

    public ProfilePostsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
        time=0;
        prettyTime = new PrettyTime(Locale.getDefault());
    }


    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardlayout_posts_profile, null);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        ProfilePostModel user = getItem(position);

        String d = user.getCreated_at();
        String[] dd=  d.split("//.p");
        time=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" , Locale.getDefault());
        holder.name.setText(user.getCreated_by());
        // holder.time.setText(profileArrayList.get(position).getTime());
        holder.content.setText(user.getContent());
        //holder.messages.setText(profileArrayList.get(position).getMessages());
        holder.hearts.setText(user.getUpvotes());
        try {
            time = sdf.parse(dd[0]).getTime();
            holder.time.setText(prettyTime.format(new Date(time)));
        } catch (ParseException e) {
            //e.printStackTrace();
            holder.time.setText(prettyTime.format(new Date(time)));
        }



    }

    private static DiffUtil.ItemCallback<ProfilePostModel> diffCallback =
            new DiffUtil.ItemCallback<ProfilePostModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull ProfilePostModel oldItem, @NonNull ProfilePostModel newItem) {
                    return oldItem.getCreated_by().equals(newItem.getCreated_by());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ProfilePostModel oldItem, @NonNull ProfilePostModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class ProfileViewHolder extends RecyclerView.ViewHolder {

       // ImageView profileImage;
        TextView name, time, content, messages, hearts ;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

           // profileImage = itemView.findViewById(R.id.profilephoto);
            name = itemView.findViewById(R.id.user_profile_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
            messages = itemView.findViewById(R.id.nmessages);
            hearts = itemView.findViewById(R.id.nhearts);
        }
    }
}
