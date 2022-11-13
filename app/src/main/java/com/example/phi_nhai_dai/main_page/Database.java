package com.example.phi_nhai_dai.main_page;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DB_PATH = "app/src/main/res/raw/data.db";

    public static final String DB_NAME = "Data";


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, ".db", null, 1);
    }

    //To create a new Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableStatement = "CREATE TABLE PLACES_TABLE()";

        sqLiteDatabase.execSQL(CreateTableStatement);
    }

    //Prevent previous users apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
