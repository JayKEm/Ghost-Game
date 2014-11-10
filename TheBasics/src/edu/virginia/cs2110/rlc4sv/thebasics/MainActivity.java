package edu.virginia.cs2110.rlc4sv.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	
	MediaPlayer logoMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
       
        
        logoMusic = MediaPlayer.create(MainActivity.this, R.raw.splash_sound);
        logoMusic.start();
        
        Thread logoTimer = new Thread(){
        	public void run() {
        		try{
        			sleep(1000);
        			Intent menuIntent = new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MENU");
        			startActivity(menuIntent);
        		} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		
        		finally {
        			
        		}
        	}
        };
        logoTimer.start();
    }


    @Override
	protected void onPause() {
		super.onPause();
		logoMusic.release();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
