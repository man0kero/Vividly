package com.manokero.vividly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manokero.vividly.model.ImageModel;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<ImageModel> list;

    public Adapter(Context context, ArrayList<ImageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String username = list.get(position).getUser().getName();
        String alt_description = list.get(position).getFormattedAltDescription();
        Glide.with(context).load(list.get(position).getUrls().getRegular())
                .into(holder.imageView);

        holder.userNameTextView.setText(username);

        holder.descriptionTextView.setText(alt_description);

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenActivity.class);
            intent.putExtra("image", list.get(position).getUrls().getRegular());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userNameTextView;
        TextView descriptionTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_imageView);
            userNameTextView = itemView.findViewById(R.id.item_userNameTextView);
            descriptionTextView = itemView.findViewById(R.id.item_descriptionTextView);
        }
    }
}