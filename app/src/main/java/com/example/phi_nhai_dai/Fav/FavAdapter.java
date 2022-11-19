package com.example.phi_nhai_dai.Fav;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.phi_nhai_dai.main_page.Place;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Place> place;
    private FavDB favdb;

    public FavAdapter(Context context, ArrayList<Place> place) {
        this.context = context;
        this.place = place;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favdb = new FavDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
           createTableOnFirstStart();
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_card,parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        readCursorData();

        Place p = place.get(position);
        holder.rating.setText(String.valueOf(p.getRating()));
        holder.name.setText(p.getName() + ", " + String.valueOf(p.getLocation()));
        Glide.with(context).load(String.valueOf(p.getImg_link())).into(holder.img);

        holder.img.setOnClickListener(v -> {
            Intent intent = new Intent(context, Description.class);
            intent.putExtra("ID", String.valueOf(p.getId()));
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

    private void createTableOnFirstStart() {
        favdb.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }


}
