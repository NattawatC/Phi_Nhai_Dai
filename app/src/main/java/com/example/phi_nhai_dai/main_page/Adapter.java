package com.example.phi_nhai_dai.main_page;

import android.annotation.SuppressLint;
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
import com.example.phi_nhai_dai.Description.Description;
import com.example.phi_nhai_dai.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Place> place;

    public Adapter(Context context, ArrayList<Place> place) {
        this.context = context;
        this.place = place;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.travel_card,parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place p = place.get(position);
        holder.rating.setText(String.valueOf(p.getRating()));
        holder.name.setText(p.getName() + ", " + String.valueOf(p.getLocation()));
        Glide.with(context).load(String.valueOf(p.getImg_link())).into(holder.img);

        holder.img.setOnClickListener(v -> {

            Intent intent = new Intent(context, Description.class);
            intent.putExtra("ID", String.valueOf(p.getId()));
            intent.putExtra("IMAGE", p.getImg_link());
            intent.putExtra("NAME", holder.name.getText().toString());
            context.startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return place.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rating, name;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.cardRating);
            name = itemView.findViewById(R.id.cardTitle);
            img = itemView.findViewById(R.id.cardImage);
        }
    }
}