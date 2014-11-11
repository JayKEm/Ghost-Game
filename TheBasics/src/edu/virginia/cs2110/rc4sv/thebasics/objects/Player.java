package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Player extends Sprite {

	public int score;
	private int health;
	
	public static int MAX_HEALTH = 3;
	public static final int MIN_HEALTH = 0;
	
	public Player(OurView ov, Bitmap src, int x, int y){
		super(ov, src , x, y);
	}
	
	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawText("" + health, 0, 0, new Paint(Color.BLUE));
	}
	
	public void handleCollision() {
		for (Entity s : world)
			if(isColliding(s)){
				if(s instanceof Ghost)
//					if(time >= damageDelay)
						damage();
//				if(s instanceof Wall)
//					reAdjust();
			}
	}
	
	public void damage(){
		if(health > MIN_HEALTH)
			health--;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
}
