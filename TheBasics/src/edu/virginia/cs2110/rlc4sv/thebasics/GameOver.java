package edu.virginia.cs2110.rlc4sv.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends Activity {
 
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		Button but1 = (Button)findViewById(R.id.but1);
		Button but2 = (Button)findViewById(R.id.but2);
		
		but2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		but1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MENU"));	
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
