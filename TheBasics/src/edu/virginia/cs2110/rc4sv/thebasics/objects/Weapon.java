package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Weapon extends Entity {

	public Weapon(OurView ov, Bitmap weaponsprite, int x, int y) {
		super(ov, weaponsprite, x, y);
		width = weaponsprite.getWidth()/6;
		height = weaponsprite.getHeight()/2;
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);	
	}



}
