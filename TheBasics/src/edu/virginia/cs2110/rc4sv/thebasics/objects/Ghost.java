package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

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

	public void render(Canvas canvas) {
		update();

		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
		
//		drawBounds(canvas);
		canvas.drawBitmap(image, src, dst, null);
	}

	public void update() {
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
			location.x += velocity.x;
			location.y += velocity.y;
			bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
					location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
		}
		
		double distance = (new Vector(location.x - player.location.x,
				location.y - player.location.y)).magnitude();
		Log.d(id+" distance",""+distance);
		if(distance > 16){
//			level.warn = true;
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
			if(isColliding(s) && (s instanceof Tile || s instanceof Chest)){
				reAdjust();
				randomDirection();
			}
			
			if(isColliding(s) && s instanceof Player)
				if (((Player) s).hasWeapon() == true) {
					this.damage();
				}
			if(isColliding(s) && s instanceof Fireball)
				{
					this.damage();
				}
		}
	}

	public void setHasWeapon() {}
	public void interact(Player player){}

}
