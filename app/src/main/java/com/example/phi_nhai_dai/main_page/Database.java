package com.example.phi_nhai_dai.main_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {

    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/com.example.phi_nhai_dai/databases/";
    private static final String DB_NAME = "place";
    private final Context context;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public void copyDB() throws IOException{
        try {
            assert context != null;
            InputStream ip =  context.getAssets().open(DB_NAME+".db");
            String op=  DB_PATH  +  DB_NAME ;
            OutputStream output = new FileOutputStream(op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
