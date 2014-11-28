package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Tile extends Entity{
	
	public static final int SIZE = 32;
	
	public Tile (OurView ov, int x, int y){
		super(ov, BitmapFactory.decodeResource(ov.getResources(), R.drawable.wall_tile), x, y);

		width = height = SIZE;
		bounds = new Rect(x + width/4, y+16, x + width*2, y + height*2);
		id = "Tile";
	}

	public void render(Canvas canvas) {
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
		
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY + height/4, 
				location.x + ov.offsetX + width*2, location.y + ov.offsetY + height*2);
		canvas.drawBitmap(image, null, dst, null);
	}
	
	public void renderBounds(Canvas canvas, Paint paint){
	    canvas.drawRect(bounds, paint);
	}

	public void interact(Player player){}

}
