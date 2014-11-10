package edu.virginia.cs2110.rlc4sv.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Button Sound
		final MediaPlayer buttonSound = MediaPlayer.create(menu.this, R.raw.button_click);
		
		
		//Setting up the button references
		Button tut1 = (Button)findViewById(R.id.tutorial1);
		Button tut2 = (Button)findViewById(R.id.tutorial2);
		Button SFX = (Button)findViewById(R.id.tutorial3);
		
		tut1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			buttonSound.start();	
			startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.TUTORIALONE"));	
			}
		});
		
		
		tut2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			buttonSound.start();		
			startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.TUTORIALFOUR"));	
			}
		});
		
		SFX.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			buttonSound.start();		
			startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.SURFACEVIEWEXAMPLE"));	
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.main_menu, menu);
		return true;
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menuSweet:
			startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.SWEET"));
			return true;
		case R.id.menuToast:
				Toast andEggs =Toast.makeText(menu.this, "This is a toast", Toast.LENGTH_LONG);
				andEggs.show();
			return true;
		}
		
		
		return false;
		
	}
}
