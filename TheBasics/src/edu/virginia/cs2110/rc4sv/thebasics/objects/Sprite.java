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

@SuppressLint("DrawAllocation")
public abstract class Sprite extends Entity {

	//2d vector format
	//magnitude must be less than MAX_SPEED, which means we have to implement the
	//location of the hit box with floats or doubles, not ints
	protected int[] velocity = new int[2]; 
	protected int currentFrame = 0;
	protected int changeFrame = 0;
	protected int direction = 0;
	protected boolean move = false;
	protected int health;
	protected boolean isDead = false;
	
	protected static final int DEFAULT_SPEED = 5;
	protected static int MAX_SPEED;
	
	public Sprite(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		this.x = x;
		this.y = y;
		
		height = image.getHeight() / 4; //4 rows
		width = image.getWidth() / 4;  //4 columns
		
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
		
		MAX_SPEED = DEFAULT_SPEED;
		velocity[0] = DEFAULT_SPEED;
		velocity[1] = 0;
		
		world = new ArrayList<Entity>();
	}
	
	public abstract void update();
	
	public void setVelocity(int x, int y){
		double speed = Math.sqrt((double) (x*x + y*y));
		if (speed > MAX_SPEED){
			double theta = Math.acos(x / speed);
			x = (int) (MAX_SPEED * Math.cos(theta));
			y = (int) (MAX_SPEED * Math.sin(theta));
		}
			
		velocity[0] = x;
		velocity[1] = y;
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getxSpeed() {
		return velocity[0];
	}

	public void setxSpeed(int xSpeed) {
		this.velocity[0] = xSpeed;
	}

	public int getySpeed() {
		return velocity[1];
	}

	public void setySpeed(int ySpeed) {
		this.velocity[1] = ySpeed;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public void setWorld(ArrayList<Entity> world){
		this.world = world;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(String dir){
		if(dir.equals("left")){
			setVelocity(-DEFAULT_SPEED, 0);
			direction = 3;
		}
		
		if(dir.equals("right")){
			setVelocity(DEFAULT_SPEED, 0);
			direction = 1; 
		}
		
		if(dir.equals("down")){
			setVelocity(0, DEFAULT_SPEED);
			direction = 0;
		}
		
		if(dir.equals("up")){
			setVelocity(0, -DEFAULT_SPEED);
			direction = 2;	
		}	
	}

	//put the sprite back where it was before it collided
	public void reAdjust(){
		x = x - velocity[0];
		y = y - velocity[1];
		
		bounds.offset(-velocity[0], -velocity[1]);
		move = false;
	}
	
	public void damage() {
		if (this.health <= 1) {
			this.isDead = true;
		}
		else {
			this.health --;
		}
	}
	
	public abstract void handleCollision();
	public abstract void setHasWeapon();
}
