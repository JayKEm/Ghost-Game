package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class GameOver extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		Button but1 = (Button)findViewById(R.id.button1);
		Button but2 = (Button)findViewById(R.id.button2);
		
		but1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		but2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MENU"));	
			}
		});
		String ghostsKilled = "";
		String coinsCollected = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			ghostsKilled = extras.getString("EXTRA_GHOSTS_KILLED");
			coinsCollected = extras.getString("EXTRA_COINS_COLLECTED");
		}
		TextView score = (TextView)findViewById(R.id.textView1);
		if(ghostsKilled.equals("1")){
			score.setText("You killed " + ghostsKilled + " ghost and collected $" + coinsCollected + " worth of coins");
		}
		else{
		score.setText("You killed " + ghostsKilled + " ghosts and collected $" + coinsCollected + " worth of coins");
		}
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
