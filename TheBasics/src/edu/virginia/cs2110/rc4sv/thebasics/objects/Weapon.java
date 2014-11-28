package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
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
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);

		if (System.currentTimeMillis() - times > 100) {
			times = System.currentTimeMillis();

			if (sprX < 6) {
				sprX++;
			}

			else if (sprX == 6) {
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

	public void render(Canvas canvas) {
		update();

		int srcY = sprY * height;
		int srcX = sprX * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
		//canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}

	public void setHasWeapon() {}
	public void handleCollision() {}
	public void interact(Player player){}
}
