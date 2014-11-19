package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Player extends Sprite {

	public int score;
	public int health;
	private int cooldown;
	private boolean hasWeapon;
	private int ghostsKilled;
	
	public static int MAX_HEALTH = 5;
	public static final int MIN_HEALTH = 0;
	public static final int DAMAGE_TIMER = 3000;
	
	public Player(OurView ov, Bitmap src, int x, int y){
		super(ov, src , x, y);
		this.health = MAX_HEALTH;
		id = "Player";
		this.cooldown = DAMAGE_TIMER;
		hasWeapon = false;
	}

	public void update() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e)  {
			e.printStackTrace();
		}
		
		changeFrame++;
		if(changeFrame == 20){
			currentFrame = ++currentFrame % 4;
			changeFrame=0;
		}
		
		if(move){
			ov.offsetX -= velocity[0];
			ov.offsetY -= velocity[1];
		}
		
		try{
			handleCollision();
		} catch(NullPointerException e){
			Log.d(id, "World must be set before collision can handled.");
		}
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		update();
		
		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width*2, y + height*2);
		//canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
		canvas.drawText("" + health, 0, 0, new Paint(Color.BLUE));
	}

	@SuppressWarnings("unchecked")
	public void handleCollision() {this.cooldown =- 100;
		for (Entity s : world)
			if(isColliding(s) && !this.equals(s)){
				if(s instanceof Ghost && !this.hasWeapon)
					if (this.cooldown <= 0) {
						//damage();
						this.cooldown = DAMAGE_TIMER;
					}
				if(s instanceof Tile)
					reAdjust();
				if(s instanceof Coin){
					score += ((Coin) s).getValue();
					level.removeFromWorld(s);
					
				}
				if(s instanceof Weapon){
					setHasWeapon();
					level.removeFromWorld(s);
				}
			}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void reAdjust(){
		ov.offsetX += velocity[0];
		ov.offsetY += velocity[1];
	}
	
	public void setHasWeapon(){
		hasWeapon = true;
	}
	
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	public void killGhost() {
		this.ghostsKilled++;
		Log.i("kill" , this.ghostsKilled + "");
	}
	
	public int getGhostsKilled () {
		return this.ghostsKilled;
	}
}
