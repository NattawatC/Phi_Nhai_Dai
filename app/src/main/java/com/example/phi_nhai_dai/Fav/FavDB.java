package com.example.phi_nhai_dai.Fav;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



public class FavDB extends SQLiteOpenHelper {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static  int DB_VERSION = 1;
    private static final String DB_PATH = "/data/data/com.example.phi_nhai_dai/databases/";
    private static final String DATABASE_NAME = "place";
    private static final String TABLE_NAME = "favourite";
    private static final String PLACE_ID = "Place_ID";
    public static String FAVOURITE_STATUS = "fStatus";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + PLACE_ID + " INT," +  FAVOURITE_STATUS + " INT)";



    public FavDB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        FavDB.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String myPath = DB_PATH + DATABASE_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        for(int x = 1; x <= 40; x++ ) {
            cv.put(PLACE_ID, x);
            cv.put(FAVOURITE_STATUS, 0);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    public Cursor ReadData() {
        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME + " JOIN Places ON favourite.Place_ID = Places.ID";
        String sql = "SELECT * FROM Places" ;
        return db.rawQuery(sql,null,null);
    }

    public void removeFav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET " + FAVOURITE_STATUS + "= 0" + " WHERE "
                + PLACE_ID + "=" + id;
        Toast.makeText(context,
                        "Remove from favourite",
                        Toast.LENGTH_LONG)
                .show();
        db.execSQL(sql);
    }


    public Cursor selectAllFav() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM Places" + " JOIN favourite ON " + "Places.ID = favourite.Places_ID"
                 + " WHERE " + FAVOURITE_STATUS + "=1";

        return db.rawQuery(sql, null, null);
    }

    public void insertIntoDatabase(String place_id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PLACE_ID, Integer.valueOf(place_id));
        cv.put(FAVOURITE_STATUS, status);
        db.insert(TABLE_NAME, null,cv);

    }
}
