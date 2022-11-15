package com.example.phi_nhai_dai.discover_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phi_nhai_dai.*;
import com.example.phi_nhai_dai.general.TravelList;


import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {

    Context context;
    ArrayList<TravelList> models;

    public CardAdapter(Context context, ArrayList<TravelList> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_card, null);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(models.get(position).getFood_item());
        holder.rating.setText(models.get(position).getRating());
        holder.imageView.setImageResource(models.get(position).getImage());
        holder.starView.setImageResource(models.get(position).getStar());
//        Picasso.get().load(models.get(position).getImage_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void changeDataSet(ArrayList<TravelList> newModels) {
        models = newModels;
    }
}
