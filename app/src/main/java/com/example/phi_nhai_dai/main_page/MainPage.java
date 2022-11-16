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
        setContentView(R.layout.new_discover_page);

        ArrayList<Place> PlaceArrayList = new ArrayList<>();
        SQLiteDatabase db1 = OpenOrCreateDataBase();


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Adapter adapter = new Adapter(this, PlaceArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FilterData(PlaceArrayList, db1, "location" ,"Chiangrai");
    }

    public SQLiteDatabase OpenOrCreateDataBase() {
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

        return db1;
    }

    public void ReadData(ArrayList<Place> p, SQLiteDatabase db1) {
        Cursor c = db1.rawQuery("SELECT * FROM Places", null);
        c.moveToFirst();
        do {
            p.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3)));
        } while (c.moveToNext());
        Collections.shuffle(p);
    }

    public void SetDummy(ArrayList<Place> p) {
        p.add(new Place(1, "MaePharoung", "Krungthape", "sfsd"));
        p.add(new Place(1, "Doi inthanon", "Chiang Mai", "sfsd"));
    }

    public void FilterData(ArrayList<Place> p, SQLiteDatabase db1, String category ,String value) {
        Cursor c = db1.rawQuery("SELECT * FROM Places " +
                "WHERE " + category + "= \"" + value + "\"" , null);
        c.moveToFirst();
        do {
            p.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3)));
        } while (c.moveToNext());
        Collections.shuffle(p);
    }
}