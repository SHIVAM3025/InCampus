package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.incampus.DiffUtils.ManageEvents.ManageEvent_Event;
import com.demo.incampus.Network.GraphqlClient;
import com.demo.incampus.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManageEventsAdapter extends PagedListAdapter<ManageEvent_Event, ManageEventsAdapter.ManageEventsViewHolder> {

    private Context context;

    public ManageEventsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<ManageEvent_Event> diffCallback =
            new DiffUtil.ItemCallback<ManageEvent_Event>() {
                @Override
                public boolean areItemsTheSame(@NonNull ManageEvent_Event oldItem, @NonNull ManageEvent_Event newItem) {
                    // Post id will be used to differentiate b/w 2 posts
                    return oldItem.getEvent_id().equals(newItem.getEvent_id());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ManageEvent_Event oldItem, @NonNull ManageEvent_Event newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public ManageEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_manageevents, null);
        return new ManageEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageEventsViewHolder holder, int position) {

        ManageEvent_Event posi = getItem(position);
        holder.name.setText(posi.getEvent_to_community().getName());
        holder.about.setText(posi.getName());

        try {
            if (posi.getCover_pic() != null) {
//                holder.profileImage.setImageDrawable(context.getResources()
//                        .getDrawable(Integer.parseInt(post.getUserPicUrl())));

                Glide.with(context)
                        .load(posi.getCover_pic())
                        .fitCenter()
                        .into(holder.event_photo);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        holder.medit.setOnClickListener(v -> {

            holder.mcardview.setVisibility(View.GONE);

            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("query", "mutation MyMutation {\n" +
                    "  delete_Events(where: {Event_to_community: {}, event_id: {_eq: \"" + posi.getEvent_id() + "\"}}) {\n" +
                    "    affected_rows\n" +
                    "  }\n" +
                    "}\n");


            GraphqlClient.getApi().graphql(jsonObject1).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                        //Log.i("Success",response.body().toString());
                    } else {

                        holder.mcardview.setVisibility(View.VISIBLE);

                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    holder.mcardview.setVisibility(View.VISIBLE);

                }
            });

        });


    }


    static class ManageEventsViewHolder extends RecyclerView.ViewHolder {
        TextView name, about;
        ImageView event_photo;
        Button medit;
        CardView mcardview;

        ManageEventsViewHolder(@NonNull View itemView) {
            super(itemView);
            mcardview = itemView.findViewById(R.id.cardView);
            medit = itemView.findViewById(R.id.edit_button);
            name = itemView.findViewById(R.id.name);
            about = itemView.findViewById(R.id.about);
            event_photo = itemView.findViewById(R.id.event_photo);
        }
    }
}
