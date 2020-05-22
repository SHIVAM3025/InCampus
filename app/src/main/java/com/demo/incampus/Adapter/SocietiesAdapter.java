package com.demo.incampus.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.Interface.Api;
import com.demo.incampus.Model.Societies;
import com.demo.incampus.Network.GraphqlClient;
import com.demo.incampus.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocietiesAdapter extends PagedListAdapter<Societies, SocietiesAdapter.SocietiesViewHolder> {

    private Context context;
    private String user_id;
    private OnPostClickListener onPostClickListener;

    public interface OnPostClickListener {
        void onPostClick(int position);
    }

    public void setOnPostClickListener(OnPostClickListener onPostClickListener) {
        this.onPostClickListener = onPostClickListener;
    }

    public SocietiesAdapter(Context context  ,String user_id) {
        super(diffCallback);
        this.context = context;
        this.user_id=  user_id;
    }

    @NonNull
    @Override
    public SocietiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_societies, null);
        return new SocietiesViewHolder(view , onPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SocietiesViewHolder holder, int position) {

        Societies posi = getItem(position);

        holder.society_name.setText(posi.getSociety_name());
        holder.followers.setText(posi.getFollowers()+ " followers");
        Glide.with(context)
                .load(posi.getSociety_photo())
                .into(holder.society_photo);

        final boolean[] follow = {true};

        Api api = GraphqlClient.getApi();
        //TODO change this listener
         holder.button.setOnClickListener(v -> {
            if (follow[0]) {
                holder.button.setText("REQUESTED");
                holder.button.setEnabled(false);

               // Toast.makeText(context, "requested", Toast.LENGTH_SHORT).show();
                holder.button.getBackground().setTint(Color.parseColor("#696969"));
                follow[0] = false;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("query" , "mutation MyMutation {\n" +
                        "  update_Community(where: {community_id: {_eq: \""+posi.getCommunity_id()+"\"}}, _inc: {member_count: 1}) {\n" +
                        "    affected_rows\n" +
                        "  }\n" +
                        "  insert_Community_members(objects: {user_id: \""+user_id+"\", isAdmin: false, isMod: false, community_id: \""+posi.getCommunity_id()+"\"}) {\n" +
                        "    affected_rows\n" +
                        "  }\n" +
                        "}");

                api.graphql(jsonObject).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                            holder.button.setEnabled(true);
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        holder.button.setEnabled(true);
                        holder.button.setText("FOLLOW");
                        holder.button.getBackground().setTint(Color.parseColor("#8A56AC"));
                        follow[0] = true;

                    }
                });



            } else {
                holder.button.setText("FOLLOW");
                holder.button.setEnabled(false);
               // Toast.makeText(context, "follow", Toast.LENGTH_SHORT).show();
                holder.button.getBackground().setTint(Color.parseColor("#8A56AC"));
                follow[0] = true;

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("query" , "mutation MyMutation {\n" +
                        "  update_Community(where: {community_id: {_eq: \""+posi.getCommunity_id()+"\"}}, _inc: {member_count: -1}) {\n" +
                        "    affected_rows\n" +
                        "  }\n" +
                        " delete_Community_members(where: {user_id: {_eq: \""+user_id+"\"}, _and: {community_id: {_eq: \""+posi.getCommunity_id()+"\"}}}) {\n" +
                        "    affected_rows\n" +
                        "  }\n" +
                        "}");

                api.graphql(jsonObject).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                            holder.button.setEnabled(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        holder.button.setEnabled(true);
                        holder.button.setText("REQUESTED");
                        holder.button.setEnabled(false);
                        holder.button.getBackground().setTint(Color.parseColor("#696969"));
                        follow[0] = false;
                    }
                });
            }
        });
    }

    private static DiffUtil.ItemCallback<Societies> diffCallback =
            new DiffUtil.ItemCallback<Societies>() {
                @Override
                public boolean areItemsTheSame(@NonNull Societies oldItem, @NonNull Societies newItem) {
                    return oldItem.getCommunity_id().equals(newItem.getCommunity_id());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Societies oldItem, @NonNull Societies newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class SocietiesViewHolder extends RecyclerView.ViewHolder {

        TextView society_name, followers;
        ImageView society_photo;
        Button button;

        SocietiesViewHolder(@NonNull View itemView ,  final OnPostClickListener onPostClickListener) {
            super(itemView);

            society_name = itemView.findViewById(R.id.society_name);
            followers = itemView.findViewById(R.id.followers);
            society_photo = itemView.findViewById(R.id.society_photo);
            button = itemView.findViewById(R.id.button);


//            button.setOnClickListener(v -> {
//                if (onPostClickListener != null) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        onPostClickListener.onPostClick(position);
//                    }
//                }
//            });

        }

    }

}
