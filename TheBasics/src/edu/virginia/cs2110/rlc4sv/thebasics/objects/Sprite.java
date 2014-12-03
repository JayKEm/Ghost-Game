package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public abstract class Sprite extends Entity {

	//2d vector format
	//magnitude must be less than MAX_SPEED, which means we have to implement the
	//location of the hit box with floats or doubles, not ints
	protected Vector velocity; 
	protected int currentFrame = 0;
	protected int changeFrame = 0;
	protected int direction = 0;
	protected int health;
	protected boolean move = false;
	protected boolean isDead = false;
	
	protected static final int DEFAULT_SPEED = 5;
	protected static int MAX_SPEED;
	
	public Sprite(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		
		height = image.getHeight() / 4; //4 rows
		width = image.getWidth() / 4;  //4 columns
		
		bounds = new Rect(x + width/4, y, x + width*ov.zoom, y + height*ov.zoom);
		
		MAX_SPEED = DEFAULT_SPEED;
		velocity = new Vector(DEFAULT_SPEED, 0);
	}
	
	public abstract void update();
	
	public void setVelocity(int x, int y){
		double speed = Math.sqrt((double) (x*x + y*y));
		if (speed > MAX_SPEED){
			double theta = Math.acos(x / speed);
			x = (int) (MAX_SPEED * Math.cos(theta));
			y = (int) (MAX_SPEED * Math.sin(theta));
		}
			
		velocity.x = x;
		velocity.y = y;
	}
	
	public void setVelocity(Vector v){
		this.velocity=v;
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getxSpeed() {
		return velocity.x;
	}

	public void setxSpeed(int xSpeed) {
		this.velocity.x = xSpeed;
	}

	public int getySpeed() {
		return velocity.y;
	}

	public void setySpeed(int ySpeed) {
		this.velocity.y = ySpeed;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
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
	
	public static String determineDirection(Vector v){
		if (v.x == 0){
			if(v.y>0) return "down";
			else return "up";
		} if (v.y == 0){
			if(v.x>0) return "right";
			else return "left";
		}
		return "";
	}

	//put the sprite back where it was before it collided
	public void reAdjust(){
		location.x = location.x - velocity.x;
		location.y = location.y - velocity.y;
		
		bounds.offset(-velocity.x*2, - velocity.y*2);
		move = false;
	}
	
	public void damage() {		
			this.loseHealth();
			if (this.getHealth() <= 0) {
				if (this instanceof Ghost) {
					level.getPlayer().killGhost();
				}
				level.removeFromWorld(this);
			}
		
	}
	
	public void setImage(int imageIndex){
		image = BitmapFactory.decodeResource(ov.getResources(), imageIndex);
	}
	
//	public void setImage(Bitmap image){
//		this.image = image;
//	}
	
	public abstract void handleCollision();
	public abstract void setHasWeapon();
	public abstract void loseHealth();
	public abstract int getHealth();
}
