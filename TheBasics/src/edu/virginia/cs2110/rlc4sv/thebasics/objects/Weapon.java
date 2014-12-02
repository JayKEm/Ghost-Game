package edu.virginia.cs2110.rlc4sv.thebasics.objects;

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
		bounds = new Rect(x + width/(2*ov.zoom), y, x + width*ov.zoom, y + height*ov.zoom);	
	}

	@Override
	public void update() {
		bounds.set(location.x + ov.offsetX + width/(2*ov.zoom), location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);

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
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		//canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}

	public void setHasWeapon() {}
	public void handleCollision() {}
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
