package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Model.ManageCommunityAdmin;
import com.demo.incampus.R;

import java.util.ArrayList;

public class CreateCommunityAdminAdapter extends RecyclerView.Adapter<CreateCommunityAdminAdapter.CreateCommunityAdminHolder> {

    private Context context;
    private ArrayList<ManageCommunityAdmin> manageCommunityAdmins;


    public CreateCommunityAdminAdapter(Context context, ArrayList<ManageCommunityAdmin> createCommunityAdmins) {
        this.context = context;
        this.manageCommunityAdmins = createCommunityAdmins;

    }

    @NonNull
    @Override
    public CreateCommunityAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_admin_managecommunity, null);
        return new CreateCommunityAdminHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCommunityAdminHolder holder, int position) {

        ManageCommunityAdmin posi = manageCommunityAdmins.get(position);

        holder.society_name.setText(posi.getSociety_name());
        holder.followers.setText(posi.getFollowers());
        holder.society_photo.setImageDrawable(context.getResources().getDrawable(posi.getSociety_photo()));


    }

    @Override
    public int getItemCount() {
        return manageCommunityAdmins.size();
    }

    static class CreateCommunityAdminHolder extends RecyclerView.ViewHolder {

        TextView society_name, followers;
        ImageView society_photo;

        CreateCommunityAdminHolder(@NonNull View itemView) {
            super(itemView);

            society_name = itemView.findViewById(R.id.society_name);
            followers = itemView.findViewById(R.id.followers);
            society_photo = itemView.findViewById(R.id.society_photo);


        }

    }

}
