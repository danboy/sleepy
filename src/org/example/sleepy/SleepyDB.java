package org.example.sleepy;

import static android.provider.BaseColumns._ID;
import static org.example.sleepy.Constants.TABLE_NAME;
import static org.example.sleepy.Constants.TIME;
import static org.example.sleepy.Constants.TITLE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepyDB extends SQLiteOpenHelper {
   private static final String DATABASE_NAME = "sleepy.db";
   private static final int DATABASE_VERSION = 3;

   /** Create a helper object for the Events database */
   public SleepyDB(Context ctx) { 
      super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) { 
      db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME
            + " INTEGER," + TITLE + " TEXT NOT NULL);");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, 
         int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
   }
}