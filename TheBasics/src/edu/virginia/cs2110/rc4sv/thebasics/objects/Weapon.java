package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Weapon extends Sprite {
	
	int sprY = 0;
	int sprX = 0;
	long times = 0;

	public Weapon(OurView ov, Bitmap weaponsprite, int x, int y) {
		super(ov, weaponsprite, x, y);
		width = weaponsprite.getWidth()/6;
		height = weaponsprite.getHeight()/2;
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);	
	}

	@Override
	public void update() {
		bounds.set(x + ov.offsetX + width/4, y + ov.offsetY, 
				x + ov.offsetX + width*2, y + ov.offsetY + height*2);
		
		if (System.currentTimeMillis() - times > 100) {
			times = System.currentTimeMillis();
		
		if (sprX < 7) {
			sprX++;
		}
		
		else if (sprX == 7) {
			sprX = 0;
			if (sprY == 0) {
				sprY++;
			}
			else {
				sprY = 0;
			}
		}
		
		else {
			sprX = 0;
		}
		}
		
	}
	
	public void onDraw(Canvas canvas) {
		update();
		
		int srcY = sprY * height;
		int srcX = sprX * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x + ov.offsetX, y + ov.offsetY, 
				x + ov.offsetX + width*2, y + ov.offsetY + height*2);
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
