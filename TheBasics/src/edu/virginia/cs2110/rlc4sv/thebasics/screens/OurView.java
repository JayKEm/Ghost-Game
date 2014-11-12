package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Ghost;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

@SuppressLint({ "WrongCall", "DrawAllocation", "ClickableViewAccessibility" })
public class OurView extends SurfaceView implements Runnable{
	
	private Thread t = null;
	private SurfaceHolder holder;
	private boolean isItOK = false;
	private Bitmap playerSprites, ghostSprites, directions;
	
	private Player player;
	private Ghost[] ghosts = new Ghost[6];
	private ArrayList<Sprite> world;
	private int dw, dh;
	
	public OurView(Context context) {
		super(context);
		holder = getHolder();
		world = new ArrayList<Sprite>();

		playerSprites = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		ghostSprites = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);
		
		directions = BitmapFactory.decodeResource(getResources(), R.drawable.directions);
		dw = directions.getWidth();
		dh = directions.getHeight();
	}

	public void run() {
		player = new Player(this, playerSprites, 0, 400);
		world.add(player);
		
		for (Ghost g : ghosts){
			g = new Ghost(this, ghostSprites, (int)(Math.random()*700), (int)(Math.random()*1100));
			world.add(g);
		}

		for(Sprite s : world)
			s.setWorld(world);

		while(isItOK == true) {
			if(!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			onDraw(c);
			holder.unlockCanvasAndPost(c);
		}
	}
	
	public boolean addToWorld(Sprite s){
		return world.add(s);
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawARGB(255, 150, 150, 10);
		Rect src = new Rect(0, 0, dw, dh);
		Rect dst = new Rect(0, getHeight()- dh, dw, getHeight());
		canvas.drawBitmap(directions, src, dst, null);

		for (Sprite s : world)
			s.onDraw(canvas);
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
		t = null;
	}
	
	public void resume(){
		isItOK = true;
		t = new Thread(this);
		t.start();
	}
	
	public Player getPlayer() {
		return player;
	}
}
