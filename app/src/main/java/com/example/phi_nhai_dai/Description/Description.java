package com.example.phi_nhai_dai.Description;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.phi_nhai_dai.R;
import com.example.phi_nhai_dai.main_page.Database;
import com.example.phi_nhai_dai.main_page.MainPage;
import com.example.phi_nhai_dai.main_page.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class Description extends AppCompatActivity {

    TextView name;
    TextView location;
    ImageView image;
    TextView description;

    SQLiteDatabase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.card_description_relative);
        db1 = openOrCreateDatabase("place", Context.MODE_PRIVATE, null);
        loadActivity();

    }

    @SuppressLint("SetTextI18n")
    public void loadActivity() {

        String id = getIntent().getStringExtra("ID");

        Place p;
        Cursor c = db1.rawQuery("SELECT * FROM Places JOIN Description ON Places.ID = Description.Places_ID\n"
                + "WHERE Places.ID=" + id, null);
        c.moveToFirst();
        p = new Place(c.getInt(0), c.getString(1)
                , c.getString(2), c.getString(3), c.getFloat(4), c.getString(5));

        name = findViewById(R.id.title_example);
        name.setText(p.getName());

        location = findViewById(R.id.location_example);
        location.setText(p.getLocation() +", Thailand");

        image = findViewById(R.id.cream_box);
        Glide.with(this).load(p.getImg_link()).into(image);

        description = findViewById(R.id.description_example);
        description.setText(c.getString(9));

        db1.close();

    }


}