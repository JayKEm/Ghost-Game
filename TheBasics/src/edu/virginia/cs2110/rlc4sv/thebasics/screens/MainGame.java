package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */

@SuppressLint("ClickableViewAccessibility")
public class MainGame extends Activity implements OnTouchListener {

	private OurView ov;
	private int x, y;
	private MediaPlayer logoMusic;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ov = new OurView(this);
		ov.setOnTouchListener(this);
		logoMusic = MediaPlayer.create(this, R.raw.dungeon_tremors);
		logoMusic.start();
		logoMusic.setLooping(true);

		x = y = 0;
		setContentView(ov);
	}

	protected void onResume() {
		super.onResume();
		ov.resume();
		try{
			logoMusic.start();
		} catch (IllegalStateException e){
			logoMusic = MediaPlayer.create(this, R.raw.dungeon_tremors);
			logoMusic.start();
		}
	}

	protected void onPause() {
		super.onPause();
		ov.pause();
		logoMusic.release();
	}

	public boolean onTouch(View s, MotionEvent me) {
		OurView v = (OurView) s;

		Rect left = new Rect(0, v.getHeight()- v.dh*2, v.dw, v.getHeight()-v.dh);
		Rect top = new Rect(v.dw, v.getHeight()- v.dh*3, v.dw*2, v.getHeight()-v.dh*2);
		Rect right = new Rect(v.dw*2, v.getHeight()- v.dh*2, v.dw*3, v.getHeight()-v.dh);
		Rect bottom =new Rect(v.dw, v.getHeight()- v.dh, v.dw*2, v.getHeight());
		Rect shoot1 =new Rect(v.dw*4, v.getHeight()- v.dh*2, v.dw*5, v.getHeight()-v.dh);
		Rect shoot2 =new Rect(v.dw*6, v.getHeight()- v.dh*2, v.dw*7, v.getHeight()-v.dh);
		if (!this.ov.getMyLevel().getWorld().contains(ov.getPlayer())) {
			Intent menuIntent = new Intent("edu.virginia.cs2110.rlc4sv.thebasics.GAMEOVER");
			menuIntent.putExtra("EXTRA_GHOSTS_KILLED" , ov.getLevel().getGhostsKilled() + "");
			menuIntent.putExtra("EXTRA_COINS_COLLECTED", ov.getLevel().getCoinsCollected() + "");
			startActivity(menuIntent);
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		switch(me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) me.getX();
			y = (int) me.getY();

			if(ov.getPlayer()== null)
				return false;
			if(!ov.getPlayer().locked){
				if(left.contains(x, y)) {
					ov.getPlayer().setDirection("left");
					ov.getPlayer().setMove(true);
				} else if(top.contains(x, y)) {
					ov.getPlayer().setDirection("up");
					ov.getPlayer().setMove(true);
				} else if(right.contains(x, y)) {
					ov.getPlayer().setDirection("right");
					ov.getPlayer().setMove(true);
				} else if(bottom.contains(x, y)) {
					ov.getPlayer().setDirection("down");
					ov.getPlayer().setMove(true);
				} else if (ov.getPlayer().getBounds().contains(x, y)){
					ov.getPlayer().interact();
				} else if(shoot1.contains(x, y)) {
					Bitmap fireballSprites = BitmapFactory.decodeResource(getResources(), R.drawable.explode);
					ov.getLevel().spawnFireball(fireballSprites);
					MediaPlayer.create(ov.getContext(), R.raw.fire).start();
				} else if(shoot2.contains(x, y)) {
					Bitmap iceboltSprites = BitmapFactory.decodeResource(getResources(), R.drawable.icebolt);
					ov.getLevel().spawnIcebolt(iceboltSprites);
					MediaPlayer.create(ov.getContext(), R.raw.fire).start();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break; 
		case MotionEvent.ACTION_UP:
			ov.getPlayer().setMove(false);
			break;
		}

		return true;
	}
}
