package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Floor extends Entity{
	
	public Floor(OurView ov, Vector v){
		this(ov, v.x, v.y);
	}
	
	public Floor (OurView ov, int x, int y){
		super(ov, BitmapFactory.decodeResource(ov.getResources(), R.drawable.grass_tile), x, y);

		width = height = Tile.SIZE;
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
		id = "Floor";
	}

	public void interact(Player player){}


}
