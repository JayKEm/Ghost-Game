package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
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
	private Rect src, dst;
	private MediaPlayer logoMusic;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ov = new OurView(this);
		ov.setOnTouchListener(this);

		logoMusic = MediaPlayer.create(this, R.raw.splash_sound);
		logoMusic.start();

		x = y = 0;
		setContentView(ov);
	}

	protected void onResume() {
		super.onResume();
		ov.resume();
		try{
			logoMusic.start();
		} catch (IllegalStateException e){
			logoMusic = MediaPlayer.create(this, R.raw.splash_sound);
			logoMusic.start();
		}
	}

	protected void onPause() {
		super.onPause();
		ov.pause();
		logoMusic.release();
	}

	public boolean onTouch(View v, MotionEvent me) {
		Rect left = new Rect(0, v.getHeight() - 150, 100, v.getHeight( ) -51 );
		Rect top = new Rect(101, v.getHeight() - 220, 200, v.getHeight() - 151 );
		Rect right = new Rect(201, v.getHeight() - 150, 300, v.getHeight() - 51 );
		Rect bottom = new Rect(101, v.getHeight() - 50, 200, v.getHeight() );

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		switch(me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) me.getX();
			y = (int) me.getY();

			if(left.contains(x, y)) {
				ov.getPlayer().setDirection("left");
				ov.getPlayer().setMove(true);
			}

			else if(top.contains(x, y)) {
				ov.getPlayer().setDirection("up");
				ov.getPlayer().setMove(true);
			}

			else if(right.contains(x, y)) {
				ov.getPlayer().setDirection("right");
				ov.getPlayer().setMove(true);
			}

			else if(bottom.contains(x, y)) {
				ov.getPlayer().setDirection("down");
				ov.getPlayer().setMove(true);
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
