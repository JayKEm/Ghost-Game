package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Fireball extends Sprite {
	private int row;
	int x=v.x-ov.offsetX;
	int y=v.y-ov.offsetY;

	public Fireball(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		height = image.getHeight() / 5; //4 rows
		width = image.getWidth() / 5;  //4 columns	}
		//bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
	}

	@Override
	public void update() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e)  {
			e.printStackTrace();
		}
		
		changeFrame++;
		if(changeFrame == 2){
			currentFrame = ++currentFrame % 5;
			changeFrame=0;
			if(currentFrame % 5==0){
				row=++row%5;
				if(row==4)
					level.removeFromWorld(this);
					
			}
			
		}
		
			
		
			
			x += velocity.x;
			y += velocity.y;
			bounds.set(x+ov.offsetX, y+ov.offsetY,x +ov.offsetX+ width*2, y+ov.offsetY + height*2);
		
		
		try{
			//handleCollision();
		} catch(NullPointerException e){
			Log.d(id, "World must be set before collision can handled.");
		}
	
	}	
	
	public void render(Canvas canvas) {
		update();
		
		//Log.d("hi","here");
		int srcY = row * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x+ov.offsetX, y+ov.offsetY,x +ov.offsetX+ width*2, y+ov.offsetY + height*2);
		//Rect dst = new Rect(x, y,x + width*2, y + height*2);
//		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}
	
	
	@Override
	public void handleCollision() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHasWeapon() {
		// TODO Auto-generated method stub

	}

}
