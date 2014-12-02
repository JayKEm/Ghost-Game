package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Tile extends Entity{
	
	public static final int SIZE = 32;
	
	public Tile (OurView ov, int x, int y){
		super(ov, BitmapFactory.decodeResource(ov.getResources(), R.drawable.wall_tile), x, y);

		width = height = SIZE;
		bounds = new Rect(x + width/4, y+height/4, x + width*ov.zoom, y + height*ov.zoom);
		id = "Tile";
	}

	public void render(Canvas canvas) {
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY + height/4, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		canvas.drawBitmap(image, null, dst, null);
//		drawBounds(canvas);
	}

	public void interact(Player player){}

}
