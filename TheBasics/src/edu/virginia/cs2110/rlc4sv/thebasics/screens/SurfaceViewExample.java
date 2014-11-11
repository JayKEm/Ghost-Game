package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import java.util.ArrayList;

import edu.virginia.cs2110.rc4sv.thebasics.objects.Ghost;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint({ "WrongCall", "DrawAllocation", "ClickableViewAccessibility" })
public class SurfaceViewExample extends Activity implements OnTouchListener{

	private OurView v;
	private Bitmap blob, slob, directions;
	private int x, y;
	private Sprite player;
	private Ghost[] ghosts = new Ghost[6];
	private ArrayList<Sprite> world;
	private Rect src, dst;
	private int dw,dh;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new OurView(this);
		v.setOnTouchListener(this);
		//ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		directions = BitmapFactory.decodeResource(getResources(), R.drawable.directions);
		dw = directions.getWidth();
		dh = directions.getHeight();
		blob = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		slob = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);

		x = y = 0;
		world = new ArrayList<Sprite>();
		setContentView(v);
	}

	protected void onResume() {
		super.onResume();
		v.resume();
	}

	protected void onPause() {
		super.onPause();
		v.pause();
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
				player.setDirection("left");
				player.setMove(true);
			}
			
			else if(top.contains(x, y)) {
				player.setDirection("up");
				player.setMove(true);
			}
			
			else if(right.contains(x, y)) {
				player.setDirection("right");
				player.setMove(true);
			}
			
			else if(bottom.contains(x, y)) {
				player.setDirection("down");
				player.setMove(true);
			}

			//sprite.setMove(true);
			break;
		case MotionEvent.ACTION_MOVE:
			break; 
		case MotionEvent.ACTION_UP:
			player.setMove(false);
			break;
		}

		return true;
	}
}
