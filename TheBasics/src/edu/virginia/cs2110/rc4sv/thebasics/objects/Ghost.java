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
public class Ghost extends Sprite {

	private int changeFrame2 = 0;

	public Ghost(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		id = "Ghost";
		width = image.getWidth() / 3;  //3 columns
		this.health = 1;
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
		randomDirection();
	} 

	public void onDraw(Canvas canvas) {
		update();
		
		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x + ov.offsetX, y + ov.offsetY, 
				x + ov.offsetX + width*2, y + ov.offsetY + height*2);
		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}

	public void update() {
		//0 down 1 right 2 up 3 left

		//facing down
//		if(x> ov.getWidth()-width*2-velocity[0]){
//			//x=ov.getWidth()-width*2-speed[0];
//			reAdjust();
//			randomDirection();
//		}
//
//		//facing left
//		if(y>ov.getHeight() - height*2 - velocity[1]) {
//			//y=ov.getHeight() - height*2 - speed[1];
//			reAdjust();
//			randomDirection();
//		}
//
//		//facing up
//		if (x + velocity[0] <0) {
//			//x=0;
//			reAdjust();
//			randomDirection();
//		}
//
//		//facing right
//		if(y + velocity[1] < 0) {
//			//y=0;
//			reAdjust();
//			randomDirection();
//		}

		try {
			Thread.sleep(0);
		} catch (InterruptedException e){}
		
		changeFrame++;
		if(changeFrame==20){
			currentFrame = ++currentFrame % 3;
			changeFrame=0;
		} 
		
		changeFrame2+=2;
		if(changeFrame2 == 100) {
			randomDirection();	
			changeFrame2=0; 
		}
		//change VALUE for testing

		if(move == false){
			x += velocity[0];
			y += velocity[1];
			bounds.set(x + ov.offsetX + width/4, y + ov.offsetY, 
					x + ov.offsetX + width*2, y + ov.offsetY + height*2);
		}
		
		handleCollision();
	}

	public void setDirection(int direction) {
		this.direction = direction;
		move = true;
	}

	public void randomDirection() {
		int x = (int) ((Math.random()*100));

		if(x < 25)
			setDirection("left");
		else if(x < 50)
			setDirection("up");
		else if(x < 75)
			setDirection("right");
		else
			setDirection("down");
	}

	public void handleCollision() {
		for(Entity s : world){
			if(isColliding(s) && !(s instanceof Coin) && !this.equals(s)){
				if(!(s instanceof Player)){
					reAdjust();
					randomDirection();
				}
				else
					if (((Player) s).hasWeapon() == true) {
						this.damage();
				}
			}
		}
	}

	@Override
	public void setHasWeapon() {
		// TODO Auto-generated method stub
		
	}
}
