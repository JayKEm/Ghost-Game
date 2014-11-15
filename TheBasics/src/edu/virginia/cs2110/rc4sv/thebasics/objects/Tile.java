package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Tile extends Entity{
	
	public static final int SIZE = 32;
	
	public Tile (OurView ov, int x, int y){
		super(ov, BitmapFactory.decodeResource(ov.getResources(), R.drawable.wall_tile), x, y);

		width = height = SIZE;
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
		id = "Tile";
	}

}
