package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Coin extends Sprite {

	int sprY = 0;
	int sprX = 0;
	long times = 0;
	private int value;

	public Coin(OurView ov, Bitmap coinSprites, int x, int y) {
		super(ov, coinSprites, x, y);
		width = coinSprites.getWidth()/8;
		height = coinSprites.getHeight();
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);

		//different coins have different values. if a coin
		//has a certain image, give it a value
		if(coinSprites.equals(ov.goldCoin))
			value = 10;
		if(coinSprites.equals(ov.silverCoin))
			value = 5;
		if(coinSprites.equals(ov.bronzeCoin))
			value = 3;

		id = "Coin " + value;
	}

	public int getValue(){
		return value;
	}

	public void update() {
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);

		if (System.currentTimeMillis() - times > 100) {
			times = System.currentTimeMillis();

			if (sprX >= 7) {
				sprX = 0;
			}

			else {
				sprX++;
			}
		}
	}

	public void render(Canvas canvas) {
		update();

		int srcY = sprY * height;
		int srcX = sprX * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
//		drawBounds(canvas);
		canvas.drawBitmap(image, src, dst, null);
	}

	public void handleCollision() {}
	public void setHasWeapon() {}
	public void interact(Player player){}

	public static Coin clone(Coin c) {
		return new Coin(c.ov, c.image, c.location.x, c.location.y);
	}

}
