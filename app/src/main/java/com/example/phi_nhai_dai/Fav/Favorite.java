package com.example.phi_nhai_dai.Fav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.phi_nhai_dai.MainActivity;
import com.example.phi_nhai_dai.R;
import com.example.phi_nhai_dai.main_page.Adapter;
import com.example.phi_nhai_dai.main_page.Database;
import com.example.phi_nhai_dai.main_page.MainPage;
import com.example.phi_nhai_dai.main_page.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerview;
    Context context;
    SQLiteDatabase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().hide();
        context = this;
        recyclerview = findViewById(R.id.recyclerView_fav);
        OpenOrCreateDataBase();

        // Navigation Settings
        bottomNavigationView = findViewById(R.id.dock_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_favourite);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int currentItem = item.getItemId();
            if (currentItem == R.id.nav_favourite) {
                return true;
            } else if (currentItem == R.id.nav_aboutus) {
                Intent myIntent = new Intent(Favorite.this, MainActivity.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (currentItem == R.id.nav_discover) {
                Intent myIntent = new Intent(Favorite.this, MainPage.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else {
                System.out.println("Not implemented");
                return false;
            }
        });

        ArrayList<Place> p = new ArrayList<>();
        Cursor c = db1.rawQuery("SELECT * FROM Places WHERE fStatus=1" , null);

        if (c.moveToFirst()) {
            do {
                p.add(new Place(c.getInt(0), c.getString(1)
                        , c.getString(2), c.getString(3), c.getFloat(4), c.getString(5)));
            } while (c.moveToNext());
            Adapter adapter = new Adapter(context, p);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(context));
        }
        c.close();
        db1.close();
    }

    public void OpenOrCreateDataBase() {
        Database db = Database.getInstance(context);
        db1 = db.getReadableDatabase();
    }

}