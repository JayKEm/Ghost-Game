package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import edu.virginia.cs2110.rlc4sv.thebasics.R;
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

public class MainMenu extends Activity {

	private MediaPlayer logoMusic;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		logoMusic = MediaPlayer.create(this, R.raw.splash_sound);
		logoMusic.start();

		//Button Sound
		final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_click);

		//Setting up the button references
		Button tut1 = (Button)findViewById(R.id.tutorial1);
		Button tut2 = (Button)findViewById(R.id.tutorial2);
		Button tut3 = (Button)findViewById(R.id.tutorial3);
		Button tut4 = (Button)findViewById(R.id.tutorial4);

		tut1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});


		tut2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				buttonSound.start();		
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.HIGHSCORESCREEN"));	
			}
		});

		tut3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				buttonSound.start();		
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.SETTINGSMENU"));	
			}
		});

		tut4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				buttonSound.start();		
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.HELPSCREEN"));	
			}
		});
	}

	protected void onPause() {
		super.onPause();
		logoMusic.release();
	}
	
	protected void onResume() {
		super.onResume();
		try{
			logoMusic.start();
		} catch (IllegalStateException e){
			logoMusic = MediaPlayer.create(this, R.raw.splash_sound);
			logoMusic.start();
		}
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
			closeContextMenu();
			return true;
		case R.id.menuToast:
			Toast andEggs = Toast.makeText(MainMenu.this, "This is a toast", Toast.LENGTH_LONG);
			andEggs.show();
			return true;
		}

		return false;
	}
}
