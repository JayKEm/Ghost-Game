package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Weapon extends Entity {

	public Weapon(OurView ourView, Bitmap src, int x, int y) {
		super(ourView, src, x, y);
		// TODO Auto-generated constructor stub
	}

	public void handleCollision() {
		for (Entity s : world) {
			if(this.isColliding(s) && s instanceof Player) {
				Player p = (Player) s;
				p.getWeapon();
				//remove weapon from view
			}
		}
	}

}
