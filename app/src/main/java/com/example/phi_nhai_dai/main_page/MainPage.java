package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.phi_nhai_dai.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MainPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);


        Database db = new Database(this);

        try {
            db.getReadableDatabase();
            db.copyDB();
        } catch (IOException ioe) {

            throw new Error("Database not created");
        }

        db.openDB();

        SQLiteDatabase db1;
        db1 = openOrCreateDatabase("place", Context.MODE_PRIVATE, null);
        Cursor c = db1.rawQuery("SELECT * FROM Places", null);
        c.moveToFirst();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ArrayList<Place> PlaceArrayList = new ArrayList<>();
        ArrayList<String> id, name, location, img_link;

        id = new ArrayList<>();
        name = new ArrayList<>();
        location = new ArrayList<>();
        img_link = new ArrayList<>();
        Adapter adapter = new Adapter(this, id, name, location);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        do {
            PlaceArrayList.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3)));
        } while (c.moveToNext());

        Collections.shuffle(PlaceArrayList);

        for (int i = 0; i < PlaceArrayList.size(); i++) {
            id.add(Integer.toString(PlaceArrayList.get(i).getId()));
            name.add(PlaceArrayList.get(i).getName());
            location.add(PlaceArrayList.get(i).getLocation())
            img_link.add(PlaceArrayList.get(i).getImg_link());
        }
    }
}