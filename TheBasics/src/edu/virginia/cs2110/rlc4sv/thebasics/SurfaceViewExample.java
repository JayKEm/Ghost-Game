package edu.virginia.cs2110.rlc4sv.thebasics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SurfaceViewExample extends Activity implements OnTouchListener{

	OurView v;
	Bitmap ball, blob, slob, directions;
	int x, y;
	Sprite sprite;
	GhostSprite gsprite,gsprite2,gsprite3,gsprite4,gsprite5,gsprite6;
	Rect src, dst;
	int dw,dh;

	
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		v = new OurView(this);
		v.setOnTouchListener(this);
		//ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		directions = BitmapFactory.decodeResource(getResources(), R.drawable.directions);
		dw=directions.getWidth();
		dh=directions.getHeight();
		blob = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		slob = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);


		x=y=0;
		setContentView(v);
	}
	
 	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}


	

	public class OurView extends SurfaceView implements Runnable{

		Thread t = null;
		SurfaceHolder holder;
		boolean isItOK = false;
		boolean spriteLoaded = false;
		
		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			holder = getHolder();
		}

		@Override
		public void run() {
			
			sprite = new Sprite(OurView.this, blob);
			gsprite = new GhostSprite(OurView.this, slob);
			gsprite2 = new GhostSprite(OurView.this, slob);
			gsprite3 = new GhostSprite(OurView.this, slob);
			gsprite4 = new GhostSprite(OurView.this, slob);
			gsprite5 = new GhostSprite(OurView.this, slob);
			gsprite6 = new GhostSprite(OurView.this, slob);

			while(isItOK == true) {
				
				if(!holder.getSurface().isValid()) {
					continue;
				}
			
				
				
				Canvas c = holder.lockCanvas();
				wonDraw(c);
				holder.unlockCanvasAndPost(c);
				
				
				
			}
		} 
		 
		protected void wonDraw(Canvas canvas) {
			
			
			canvas.drawARGB(255, 150, 150, 10);
			Rect src = new Rect(0,0,dw,dh);
			Rect dst = new Rect(0,v.getHeight()-dh,dw,v.getHeight());
			canvas.drawBitmap(directions, src, dst, null);
			
			sprite.wonDraw(canvas);
			gsprite.wonDraw(canvas);
			gsprite2.wonDraw(canvas);
			gsprite3.wonDraw(canvas);
			gsprite4.wonDraw(canvas);
			gsprite5.wonDraw(canvas);
			gsprite6.wonDraw(canvas);
		}
		
		public void pause () {
			isItOK = false;
			while(true){
				try{
					t.join();
				} catch ( InterruptedException e)  {
					e.printStackTrace();;
				}
				break;
			}
			t=null;
		}
		public void resume(){
			isItOK = true;
			t = new Thread(this);
			t.start();
		}
	}



	@Override
	public boolean onTouch(View v, MotionEvent me) {
		
		Rect left = new Rect(0, v.getHeight()-150, 100, v.getHeight()-51 );
		Rect top = new Rect(101, v.getHeight()-220, 200, v.getHeight()-151 );
		Rect right = new Rect(201, v.getHeight()-150, 300, v.getHeight()-51 );
		Rect bottom = new Rect(101, v.getHeight()-50, 200, v.getHeight() );
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x=(int)me.getX();
			y=(int)me.getY();
			
			if(left.contains(x, y)) {
				sprite.setDirection("left");
				sprite.setMove(true);
			}
			else if(top.contains(x, y)) {
				sprite.setDirection("up");
				sprite.setMove(true);
			}
			else if(right.contains(x, y)) {
				sprite.setDirection("right");
				sprite.setMove(true);
			}
			else if(bottom.contains(x, y)) {
				sprite.setDirection("down");
				sprite.setMove(true);
			}
			
			//sprite.setMove(true);
			
			
			break;
		
		case MotionEvent.ACTION_MOVE:
			break; 
		case MotionEvent.ACTION_UP:
			sprite.setMove(false);
			break;
		
		}
		
		return true;
	}

}
