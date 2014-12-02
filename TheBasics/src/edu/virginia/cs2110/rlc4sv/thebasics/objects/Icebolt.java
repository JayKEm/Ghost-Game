package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Icebolt extends Sprite {
	private int row;
	int x=location.x-ov.offsetX;
	int y=location.y-ov.offsetY;

	public Icebolt(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		height = image.getHeight() / 6; //6 rows
		width = image.getWidth() / 5;  //5 columns
		id = "Icebolt";
		
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
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
			currentFrame = ++currentFrame % 6;
			changeFrame=0;
			if(currentFrame % 6==0){
				row=++row%6;
				if(row==5)
					level.removeFromWorld(this);
					
			}
			
		}

		location.x += velocity.x;
		location.y += velocity.y;
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);

		
		try{
			//handleCollision();
		} catch(NullPointerException e){
			Log.d(id, "World must be set before collision can handled.");
		}
	
	}	
	
	public void render(Canvas canvas) {
		update();
		
		int srcY = row * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x+ov.offsetX, location.y+ov.offsetY,location.x +ov.offsetX+ width*2, location.y+ov.offsetY + height*2);

//		drawBounds(canvas);
		canvas.drawBitmap(image, src, dst, null);
	}
	
	public void handleCollision() {}
	public void setHasWeapon() {}
	public void interact(Player player){}

	@Override
	public void loseHealth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
