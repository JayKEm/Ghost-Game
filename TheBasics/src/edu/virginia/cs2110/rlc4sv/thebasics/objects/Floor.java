package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class Floor extends Entity{
	
	public Floor(OurView ov, Vector v){
		this(ov, v.x, v.y);
	}
	
	public Floor (OurView ov, int x, int y){
		super(ov, BitmapFactory.decodeResource(ov.getResources(), R.drawable.grass_tile), x, y);

		width = height = Tile.SIZE;
		bounds = new Rect(0,0,0,0);
		id = "Floor";
	}

	public void interact(Player player){}
}
