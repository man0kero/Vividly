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
import com.manokero.vividly.databinding.ItemRecyclerViewBinding;
import com.manokero.vividly.model.ImageModel;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<ImageModel> list;

    public Adapter(Context context, ArrayList<ImageModel> list) {
        this.context = context;
        this.list = list;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemRecyclerViewBinding binding;

        public ItemViewHolder(@NonNull ItemRecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerViewBinding binding = ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String username = list.get(position).getUser().getName();
        String alt_description = list.get(position).getFormattedAltDescription();
        Glide.with(context).load(list.get(position).getUrls().getRegular())
                .into(holder.binding.itemImageView);

        holder.binding.itemUserNameTextView.setText(username);

        holder.binding.itemDescriptionTextView.setText(alt_description);

        holder.binding.itemImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenActivity.class);
            intent.putExtra("image", list.get(position).getUrls().getRegular());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}