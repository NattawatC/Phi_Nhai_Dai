package com.example.phi_nhai_dai.main_page;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phi_nhai_dai.Description.Description;
import com.example.phi_nhai_dai.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private final Context context;
    private final ArrayList<Place> place;
    private Database db;
    private int status;
    public Adapter(Context context, ArrayList<Place> place) {
        this.context = context;
        this.place = place;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Database.getInstance(context);
        View v = LayoutInflater.from(context).inflate(R.layout.travel_card,parent,false);

        return new ViewHolder(v);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place p = place.get(position);
        Checkstatus(p);
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

        holder.favbtn.setOnClickListener(v ->  {
            if (status == 0) {
//                if (p.getFavStatus().equals("0")) {
                    p.setFavStatus("1");
                    db.AddFav(String.valueOf(p.getId()));
                    Toast.makeText(context,
                                    "Add to favorite",
                                    Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    p.setFavStatus("0");
                    db.removeFav(String.valueOf(p.getId()));
                    Toast.makeText(context,
                                    "Remove from favorite",
                                    Toast.LENGTH_LONG)
                            .show();
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


    private void Checkstatus(Place p) {
        SQLiteDatabase db1 = db.getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT fStatus FROM Places WHERE ID=" + p.getId() ,null);
        c.moveToFirst();
        status = c.getInt(0);
        c.close();
        db1.close();
    }


}
