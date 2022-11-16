package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.example.phi_nhai_dai.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MainPage extends AppCompatActivity {

    public static Boolean eventFilter = false;

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


        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch s1 = findViewById(R.id.filter);
        Boolean s1State = s1.isChecked();

        ArrayList filter = new ArrayList<>();
        if (s1State == true)
            filter.add(new Filter("location", "Chiangrai"));
        else
            filter.rem
        eventFilter = true;

        if (eventFilter == true) {
            String filterStatement =
                    ImplementFilterStatement(filter);
            ReadData(PlaceArrayList, db1, filterStatement);
        }
        else {
           filter = null;
        }

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

    public void ReadData(ArrayList<Place> p, SQLiteDatabase db1, String filterStatement) {
        Cursor c = db1.rawQuery("SELECT * FROM Places " + filterStatement, null);
        c.moveToFirst();
        do {
            p.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3)));
        } while (c.moveToNext());
        Collections.shuffle(p);
    }

    public void SetDummy(ArrayList<Place> p) {
        p.add(new Place(1, "MaePharoung", "Krungthape", "sfsd"));
        p.add(new Place(2, "Doi inthanon", "Chiang Mai", "sfsd"));
        p.add(new Place(3, "Doi", "Chiang Mai", "sfsd"));
        p.add(new Place(3, "Doi", "Chiang Mai", "s"));
    }

    public String ImplementFilterStatement(ArrayList<Filter> filterArrayList){
        String filterStatement = "WHERE ";

        for (int i = 0; i < filterArrayList.size(); i++) {
            if (i == filterArrayList.size()-1) {
                filterStatement += filterArrayList.get(i).getCategory() + "= \""
                        + filterArrayList.get(i).getValue() + "\"";
            }
            else {
                filterStatement += filterArrayList.get(i).getCategory() + "= \""
                        + filterArrayList.get(i).getValue() + "\" AND";
            }
        }
        eventFilter = true;
        return filterStatement;
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