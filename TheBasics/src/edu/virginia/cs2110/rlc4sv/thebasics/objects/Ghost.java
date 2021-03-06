package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class Ghost extends Sprite {

	private int changeFrame2, unfreezeFrame = 0;
	private boolean frozen = false;

	public Ghost(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		id = "Ghost";
		width = image.getWidth() / 3;  //3 columns
		this.health = 1;
		bounds = new Rect(x + width/4, y, x + width*ov.zoom, y + height*ov.zoom);
		randomDirection();
	} 

	public void render(Canvas canvas) {
		update();

		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		
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
		if(frozen){
			unfreezeFrame++;
			if(unfreezeFrame==100) {
				setMove(false);
				frozen = false;
			}
		}
		//change VALUE for testing

		if(move == false){
			location.x += velocity.x;
			location.y += velocity.y;
			bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
					location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		}

		double distance = (new Vector(location.x + ov.offsetX - player.location.x,
				location.y + ov.offsetY - player.location.y)).magnitude();
		if(distance < 500)
			level.setWarn(true);

		handleCollision();
	}

	public void setDirection(int direction) {
		this.direction = direction;
		move = true;
	}
	
	
	public void randomDirection() {
		if (frozen) return;
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
			if(isColliding(s) && frozen && s instanceof Fireball){
					this.damage();
				}
			if(isColliding(s) && s instanceof Icebolt){
				if(!frozen)
					ov.playSound(R.raw.ghost_freeze);
				setFrozen();
			}
		}
	}
	
	public void setFrozen() {
		this.move=true;
		frozen = true;
		unfreezeFrame=0;
	}
	
	public void setHasWeapon() {}
	public void interact(Player player){}

	@Override
	public void loseHealth() {
		this.health --;
		
	}

	@Override
	public int getHealth() {
		return this.health;
	}

}
