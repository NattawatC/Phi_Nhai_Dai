package com.example.phi_nhai_dai.main_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phi_nhai_dai.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList id, name, location, img_link;

    public Adapter(Context context, ArrayList id, ArrayList name, ArrayList location, ArrayList img_link) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.location = location;
        this.img_link = img_link;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.travel_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(id.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.location.setText(String.valueOf(location.get(position)));
        Glide.with(context).load(String.valueOf(img_link.get(position))).into(holder.img_link);
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, location;
        ImageView img_link;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.cardRating);
            name = itemView.findViewById(R.id.cardTitle);
            location = itemView.findViewById(R.id.textloc);
            img_link = itemView.findViewById(R.id.cardImage);
        }
    }
}