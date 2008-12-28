package org.example.sleepy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Calendar;

public class Sleepy extends Activity{
	private TextView clockText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //int milli = cal.get(Calendar.MILLISECOND);
		clockText = (TextView) findViewById(R.id.clock_text);
		clockText.setText(hour+":"+minute+" "+second);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case R.id.settings:
          startActivity(new Intent(this, Settings.class));
          return true;
       case R.id.about:
           startActivity(new Intent(this, About.class));
           return true;
	   case R.id.feeds:
	       startActivity(new Intent(this, Feeds.class));
	       return true;
	    }
       return false;
    }

}