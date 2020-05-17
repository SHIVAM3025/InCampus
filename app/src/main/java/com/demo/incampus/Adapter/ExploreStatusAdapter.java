package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Model.ExploreStatus;
import com.demo.incampus.R;

import java.util.ArrayList;

public class ExploreStatusAdapter extends RecyclerView.Adapter<ExploreStatusAdapter.ExploreViewHolder> {

    private Context context;
    private ArrayList<ExploreStatus> exploreStatusList;

    public ExploreStatusAdapter(Context context, ArrayList<ExploreStatus> exploreStatusList) {
        this.context = context;
        this.exploreStatusList = exploreStatusList;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardlayout_status_explore, null);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {

        ExploreStatus posi = exploreStatusList.get(position);

        holder.name.setText(posi.getName());
        holder.profile_photo.setImageDrawable(context.getResources().getDrawable(posi.getProfile_photo()));
    }

    @Override
    public int getItemCount() {
        return exploreStatusList.size();
    }

    static class ExploreViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_photo;
        TextView name;

        ExploreViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_photo = itemView.findViewById(R.id.profile_photo);
            name = itemView.findViewById(R.id.name);
        }
    }
}
