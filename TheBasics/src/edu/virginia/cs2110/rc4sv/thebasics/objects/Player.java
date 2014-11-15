package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Player extends Sprite {

	public int score;
	public int health;
	private int cooldown;
	public boolean hasWeapon;
	
	public static int MAX_HEALTH = 3;
	public static final int MIN_HEALTH = 0;
	public static final int DAMAGE_TIMER = 3000;
	
	public Player(OurView ov, Bitmap src, int x, int y){
		super(ov, src , x, y);
		this.health = MAX_HEALTH;
		this.hasWeapon = false;
		this.cooldown = DAMAGE_TIMER;
	}
	
	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawText("" + health, 0, 0, new Paint(Color.BLUE));
	}
	
	public void handleCollision() {
		this.cooldown =- 100;
		for (Entity s : world)
			if(isColliding(s)){
				if(s instanceof Ghost && this.hasWeapon == false)
					if (this.cooldown <= 0) {
						damage();
						this.cooldown = DAMAGE_TIMER;
					}
//				if(s instanceof Wall)
//					reAdjust();
			}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean getHasWeapon() {
		return this.hasWeapon;
	}
	
	public void getWeapon() {
		this.hasWeapon = true;
	}
}
