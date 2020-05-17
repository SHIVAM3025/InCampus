package com.demo.incampus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.incampus.Model.Categories;
import com.demo.incampus.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private Context context;
    private ArrayList<Categories> categoriesArrayList;
    private onItemListener onItemListener;

    public CategoriesAdapter(Context context, ArrayList<Categories> categoriesArrayList, CategoriesAdapter.onItemListener onItemListener) {
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout_categories, null);
        return new CategoriesViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        Categories posi = categoriesArrayList.get(position);

        holder.heading.setText(posi.getHeading());
        holder.topicsArticles.setText(posi.getTopicsArticles());
        holder.bg_image.setImageDrawable(context.getResources().getDrawable(posi.getBg_image()));

    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public interface onItemListener {
        void onItemClick(int position, String heading);
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView heading, topicsArticles;
        ImageView bg_image;
        onItemListener onItemListener;

        CategoriesViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);

            heading = itemView.findViewById(R.id.heading);
            topicsArticles = itemView.findViewById(R.id.topicsArticles);
            bg_image = itemView.findViewById(R.id.bg_image);

            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition(), categoriesArrayList.get(getAdapterPosition()).getHeading());
        }
    }
}
