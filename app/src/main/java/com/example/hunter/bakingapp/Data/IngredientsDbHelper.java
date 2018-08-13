package com.example.hunter.bakingapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IngredientsDbHelper extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "bakingDb.db";
    private static final  int VERSION = 1;

    public IngredientsDbHelper(Context context) {
        super(context, DATABSE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + IngredientsContract.IngredientsEntry.TABLE_NAME + " ("+
                IngredientsContract.IngredientsEntry._ID + " INTEGER PRIMARY KEY, " +
                IngredientsContract.IngredientsEntry.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                IngredientsContract.IngredientsEntry.COLUMN_MEASUSE + " TEXT NOT NULL, " +
                IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ IngredientsContract.IngredientsEntry.TABLE_NAME);
        onCreate(db);


    }
}
