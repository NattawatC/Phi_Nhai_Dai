package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.phi_nhai_dai.R;

import java.io.IOException;
import java.util.ArrayList;


public class MainPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Database db = new Database(this);

        try {
            db.getReadableDatabase();
            db.copyDB();
        } catch (IOException ioe) {

            throw new Error("Database not created");
        }

        db.openDB();

        SQLiteDatabase db1;
        db1=openOrCreateDatabase("place", Context.MODE_PRIVATE,null);
        Cursor c= db1.rawQuery("SELECT * FROM Places",null);
        c.moveToFirst();

        ArrayList<Place> PlaceArrayList = new ArrayList<>();
        do {
           PlaceArrayList.add(new Place(c.getInt(0), c.getString(1)
           , c.getString(2)));
        } while (c.moveToNext());

        TextView id = findViewById(R.id.textid);
        id.setText(Integer.toString(PlaceArrayList.get(0).getId()));

        TextView name = findViewById(R.id.textname);
        name.setText(PlaceArrayList.get(0).getName());

        TextView location = findViewById(R.id.textloc);
        location.setText(PlaceArrayList.get(0).getLocation());

    }
}