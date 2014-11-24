package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
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
		if(coinSprites.equals(ov.getGoldCoin()))
			value = 10;
		if(coinSprites.equals(ov.getSilverCoin()))
			value = 5;
		if(coinSprites.equals(ov.getBronzeCoin()))
			value = 3;

		id = "Coin " + value;
	}

	public int getValue(){
		return value;
	}

	public void update() {
		bounds.set(v.x + ov.offsetX + width/4, v.y + ov.offsetY, 
				v.x + ov.offsetX + width*2, v.y + ov.offsetY + height*2);

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
		Rect dst = new Rect(v.x + ov.offsetX, v.y + ov.offsetY, 
				v.x + ov.offsetX + width*2, v.y + ov.offsetY + height*2);
		//canvas.drawRect(bounds, new Paint(Color.RED));
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
