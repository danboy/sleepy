package org.example.sleepy;

import static android.provider.BaseColumns._ID;
import static org.example.sleepy.Constants.AUTHORITY;
import static org.example.sleepy.Constants.CONTENT_URI;
import static org.example.sleepy.Constants.TABLE_NAME;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class SleepyProvider extends ContentProvider {
   private static final int FEEDS = 1;
   private static final int FEEDS_ID = 2;

   /** The MIME type of a directory of events */
   private static final String CONTENT_TYPE
      = "vnd.android.cursor.dir/vnd.example.feed";

   /** The MIME type of a single event */
   private static final String CONTENT_ITEM_TYPE
      = "vnd.android.cursor.item/vnd.example.feed";

   private SleepyDB feeds;
   private UriMatcher uriMatcher;
   // ...
   

   
   @Override
   public boolean onCreate() {
      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      uriMatcher.addURI(AUTHORITY, "feeds", FEEDS);
      uriMatcher.addURI(AUTHORITY, "feeds/#", FEEDS_ID);
      feeds = new SleepyDB(getContext());
      return true;
   }
   

   
   @Override
   public Cursor query(Uri uri, String[] projection,
         String selection, String[] selectionArgs, String orderBy) {
      if (uriMatcher.match(uri) == FEEDS_ID) {
         long id = Long.parseLong(uri.getPathSegments().get(1));
         selection = appendRowId(selection, id);
      }

      // Get the database and run the query
      SQLiteDatabase db = feeds.getReadableDatabase();
      Cursor cursor = db.query(TABLE_NAME, projection, selection,
            selectionArgs, null, null, orderBy);

      // Tell the cursor what uri to watch, so it knows when its
      // source data changes
      cursor.setNotificationUri(getContext().getContentResolver(),
            uri);
      return cursor;
   }
   

   
   @Override
   public String getType(Uri uri) {
      switch (uriMatcher.match(uri)) {
      case FEEDS:
         return CONTENT_TYPE;
      case FEEDS_ID:
         return CONTENT_ITEM_TYPE;
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
      }
   }
   

   
   @Override
   public Uri insert(Uri uri, ContentValues values) {
      SQLiteDatabase db = feeds.getWritableDatabase();

      // Validate the requested uri
      if (uriMatcher.match(uri) != FEEDS) {
         throw new IllegalArgumentException("Unknown URI " + uri);
      }

      // Insert into database
      long id = db.insertOrThrow(TABLE_NAME, null, values);

      // Notify any watchers of the change
      Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
      getContext().getContentResolver().notifyChange(newUri, null);
      return newUri;
   }
   

   
   @Override
   public int delete(Uri uri, String selection,
         String[] selectionArgs) {
      SQLiteDatabase db = feeds.getWritableDatabase();
      int count;
      switch (uriMatcher.match(uri)) {
      case FEEDS:
         count = db.delete(TABLE_NAME, selection, selectionArgs);
         break;
      case FEEDS_ID:
         long id = Long.parseLong(uri.getPathSegments().get(1));
         count = db.delete(TABLE_NAME, appendRowId(selection, id),
               selectionArgs);
         break;
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
      }

      // Notify any watchers of the change
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }
   

   
   @Override
   public int update(Uri uri, ContentValues values,
         String selection, String[] selectionArgs) {
      SQLiteDatabase db = feeds.getWritableDatabase();
      int count;
      switch (uriMatcher.match(uri)) {
      case FEEDS:
         count = db.update(TABLE_NAME, values, selection,
               selectionArgs);
         break;
      case FEEDS_ID:
         long id = Long.parseLong(uri.getPathSegments().get(1));
         count = db.update(TABLE_NAME, values, appendRowId(
               selection, id), selectionArgs);
         break;
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
      }

      // Notify any watchers of the change
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }
   

   
   /** Append an id test to a SQL selection expression */
   private String appendRowId(String selection, long id) {
      return _ID + "=" + id
            + (!TextUtils.isEmpty(selection)
                  ? " AND (" + selection + ')'
                  : "");
   }
   
   
}