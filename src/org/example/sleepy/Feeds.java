package org.example.sleepy;


import android.app.ListActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import static android.provider.BaseColumns._ID;
import static org.example.sleepy.Constants.CONTENT_URI;
import static org.example.sleepy.Constants.TIME;
import static org.example.sleepy.Constants.TITLE;
import android.content.Intent;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class Feeds extends ListActivity{
	private static String[] FROM = { _ID, TIME, TITLE, };
	private static String ORDER_BY = TIME + " DESC";
	private static int[] TO = { R.id.rowid, R.id.time, R.id.title, };
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.feeds);
			Cursor cursor = getFeeds();
			showFeeds(cursor);
	    }
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	       super.onCreateOptionsMenu(menu);
	       MenuInflater inflater = getMenuInflater();
	       inflater.inflate(R.menu.feeds_menu, menu);
	       return true;
	    }
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	       switch (item.getItemId()) {
		   case R.id.add:
		       startActivity(new Intent(this, AddFeed.class));
		       return true;
		    }
	       return false;
	    }
	          
	     private Cursor getFeeds() {
	        // Perform a managed query. The Activity will handle closing
	        // and re-querying the cursor when needed.
	    	 return managedQuery(CONTENT_URI, FROM, null, null, ORDER_BY);
	     }
	     

	     
	     private void showFeeds(Cursor cursor) {
	         // Set up data binding
	         SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
	               R.layout.feed, cursor, FROM, TO);
	         setListAdapter(adapter);

	     }

}