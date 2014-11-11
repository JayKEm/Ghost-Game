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

	protected int[] speed = new int[2]; //2d vector format
	protected int currentFrame = 0;
	protected int changeFrame = 0;
	protected int direction = 0;
	protected boolean move = false;
	
	protected static final int DEFAULT_SPEED = 5;
	
	public Sprite(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		this.x = x;
		this.y = y;
		
		speed[0] = DEFAULT_SPEED;
		speed[1] = 0;
		
		world = new ArrayList<Sprite>();
	}

	public void onDraw(Canvas canvas) {
		update();
		
		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width*2, y + height*2);
		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}

	protected void update() {
		//0 down 1 right 2 up 3 left

		//facing down
		if(x > ov.getWidth() - width * 2 - speed[0]){
			speed[0] = 0;
			speed[1] = DEFAULT_SPEED;
			direction = 0;
		}
		
		//facing left
		if(y > ov.getHeight() - height * 2 - speed[1]) {
			speed[0] = -DEFAULT_SPEED;
			speed[1] = 0;
			direction = 3;
		}
		
		//facing up
		if (x + speed[0] <0) {
			x = 0;
			speed[0] = 0;
			speed[1] = -DEFAULT_SPEED;
			direction = 2;
		}
		//facing right
		if( y + speed[1] < 0) {
			y=0;
			speed[0] = DEFAULT_SPEED;
			speed[1] =0;
			direction = 1; 
		}

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
			x += speed[0];
			y += speed[1];
			
			bounds.offset(speed[0], speed[1]);
		}
		
		try{
			handleCollision();
		} catch(NullPointerException e){
			Log.d("Entity", "World must be set before collision can handled.");
		}
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getxSpeed() {
		return speed[0];
	}

	public void setxSpeed(int xSpeed) {
		this.speed[0] = xSpeed;
	}

	public int getySpeed() {
		return speed[1];
	}

	public void setySpeed(int ySpeed) {
		this.speed[1] = ySpeed;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public void setWorld(ArrayList<Sprite> world){
		this.world = world;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(String dir){
		if(dir.equals("left")){
			speed[0] = -DEFAULT_SPEED;
			speed[1] = 0;
			direction = 3;
		}
		
		if(dir.equals("right")){
			speed[0] = DEFAULT_SPEED;
			speed[1] = 0;
			direction = 1; 
		}
		
		if(dir.equals("down")){
			speed[0] = 0;
			speed[1] = DEFAULT_SPEED;
			direction = 0;
		}
		
		if(dir.equals("up")){
			speed[0] = 0;
			speed[1] = -DEFAULT_SPEED;
			direction = 2;	
		}	
	}

	//put the sprite back where it was before it tried to touch the wall
	public void reAdjust(){
		x = x - speed[0];
		y = y - speed[1];
		
		bounds.offset(-speed[0], -speed[1]);
		move = false;
	}
	
	public abstract void handleCollision();
}
