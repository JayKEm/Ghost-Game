package edu.virginia.cs2110.rlc4sv.thebasics;

import edu.virginia.cs2110.rlc4sv.thebasics.SurfaceViewExample.OurView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

	int x, y;
	int xSpeed, ySpeed;
	int height, width;
	Bitmap b;
	OurView ov;
	int currentFrame = 0;
	int changeFrame = 0;
	int direction = 0;
	boolean move = false;




	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
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



	public Sprite(OurView ourView, Bitmap blob) {
		// TODO Auto-generated constructor stub
		b = blob;
		ov = ourView;
		height = b.getHeight()/4; //4 rows
		width = b.getWidth()/4;  //4 columns
		x=y=0;
		xSpeed = 5;
		ySpeed = 0;
	}

	public void wonDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		update();
		int srcY = direction* height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX,srcY,srcX +width, srcY+ height);
		Rect dst = new Rect(x,y,x+width*2,y+height*2);
		canvas.drawBitmap(b, src, dst, null);
	}

	private void update() {

		//0 down 1 right 2 up 3 left

		//facing down
		if(x> ov.getWidth()-width*2-xSpeed){
			xSpeed = 0;
			ySpeed =5;
			direction = 0;
		}
		//facing left
		if(y>ov.getHeight() - height*2 - ySpeed) {
			xSpeed = -5;
			ySpeed = 0;
			direction = 3;
		}
		//facing up
		if (x + xSpeed <0) {
			x=0;
			xSpeed = 0;
			ySpeed = -5;
			direction = 2;
		}
		//facing right
		if( y + ySpeed < 0) {
			y=0;
			xSpeed =5;
			ySpeed =0;
			direction = 1; 
		}

		try {
			Thread.sleep(0);
		} catch (InterruptedException e)  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeFrame++;
		if(changeFrame==20){
			currentFrame = ++currentFrame % 4;
			changeFrame=0;
		}
		if(move==true){
			x+=xSpeed;
			y+=ySpeed;
		}

	}
	public void setDirection(String dir){
		if(dir.equals("left")){
			xSpeed = -5;
			ySpeed = 0;
			direction = 3;
		}
		if(dir.equals("right")){
			xSpeed =5;
			ySpeed =0;
			direction = 1; 
		}
		if(dir.equals("down")){
			xSpeed = 0;
			ySpeed =5;
			direction = 0;
		}
		if(dir.equals("up")){
			xSpeed = 0;
			ySpeed = -5;
			direction = 2;	
		}	
	}
}
