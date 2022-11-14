package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.phi_nhai_dai.R;

import java.io.IOException;


public class MainPage extends AppCompatActivity {

    String DB_PATH;
    final Context context=this;
    private SQLiteDatabase mDataBase;
    private static String DB_NAME ="place.db";
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Database db = new Database(this);

        try {

            db.copyDB();
        } catch (IOException ioe) {

            throw new Error("Database not created....");
        }

        try {
            db.openDB();

        }catch(SQLException sqle){

            throw sqle;
        }

        SQLiteDatabase db1;
        db1=openOrCreateDatabase("place",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        Cursor c= db1.rawQuery("SELECT * FROM Places",null);

        String temp="";
        c.moveToNext();
        while(! c.isAfterLast()) {
            String s2 = c.getString(0);
            String s3 = c.getString(1);
            String s4 = c.getString(2);
            temp += s2 + "\n" + s3 + "\n" + s4;
            c.moveToNext();
        }
        tv1= (TextView) findViewById(R.id.textid);
        tv1.setText(temp);

    }
}