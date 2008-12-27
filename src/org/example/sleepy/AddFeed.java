package org.example.sleepy;


import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import static org.example.sleepy.Constants.CONTENT_URI;
import static org.example.sleepy.Constants.TIME;
import static org.example.sleepy.Constants.TITLE;
import android.content.ContentValues;
import android.view.KeyEvent;


public class AddFeed extends Activity{
	private EditText feedText;
	private Button addButton;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_feed);
			feedText = (EditText) findViewById(R.id.feed_url);
			addButton = (Button) findViewById(R.id.add_button);
				 addButton.setOnClickListener(new OnClickListener() {
					 public void onClick(View view){
						 addFeed(feedText.getText().toString());
					 }
				 });
	    }
	 private void addFeed(String string) {
	        // Insert a new record into the Events data source.
	        // You would do something similar for delete and update.
	        ContentValues values = new ContentValues();
	        values.put(TIME, System.currentTimeMillis());
	        values.put(TITLE, string);
	        getContentResolver().insert(CONTENT_URI, values);

	     }
	     

	   

}