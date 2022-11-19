package com.example.phi_nhai_dai.main_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phi_nhai_dai.Description.Description;
import com.example.phi_nhai_dai.Fav.FavDB;
import com.example.phi_nhai_dai.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Place> place;
    private FavDB favDB;

    public Adapter(Context context, ArrayList<Place> place) {
        this.context = context;
        this.place = place;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        View v = LayoutInflater.from(context).inflate(R.layout.travel_card,parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Place p = place.get(position);
        readCursorData(p, holder);
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

        holder.favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.getFavStatus().equals("0")) {
                    p.setFavStatus("1");
                    favDB.insertIntoDatabase(String.valueOf(p.getId()),p.getFavStatus());
                }
                else {
                    p.setFavStatus("0");
                    favDB.removeFav(String.valueOf(p.getId()));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return place.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton favbtn;
        TextView rating, name;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.cardRating);
            name = itemView.findViewById(R.id.cardTitle);
            img = itemView.findViewById(R.id.cardImage);
            favbtn = itemView.findViewById(R.id.cardFav);
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Place p, ViewHolder viewHolder) {
        Cursor c = favDB.ReadData();

        try (SQLiteDatabase db = favDB.getReadableDatabase()) {
            c.moveToFirst();
            while (c.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = c.getString(c.getColumnIndex(FavDB.FAVOURITE_STATUS));
                p.setFavStatus(item_fav_status);

                //check fav status
//            if (item_fav_status != null && item_fav_status.equals("1")) {
//                viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
//            } else if (item_fav_status != null && item_fav_status.equals("0")) {
//                viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
//            }
            }
        } finally {
            if (c != null && c.isClosed()) c.close();
        }
    }
}
