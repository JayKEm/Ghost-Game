package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public abstract class Entity {

	//entities do not move, therefore do not handle collisions
	
	protected int x, y;
	protected int height, width;
	protected Bitmap image;
	protected OurView ov;
	
	protected Rect bounds;
	
	//all the entities on the level
	protected ArrayList<Sprite> world;
	
	public Entity(OurView ourView, Bitmap src, int x, int y) {
		image = src;
		ov = ourView;
		height = image.getHeight() / 4; //4 rows
		width = image.getWidth() / 4;  //4 columns
		
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
	}

	public void onDraw(Canvas canvas) {
		Rect src = new Rect(0, 0, width, height);
		Rect dst = new Rect(x, y, x + width*2, y + height*2);
		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, src, dst, null);
	}

	public boolean isColliding(Entity s){
		return Rect.intersects(bounds, s.getBounds());
	}
	
	public Rect getBounds() {
		return bounds;
	}
}
