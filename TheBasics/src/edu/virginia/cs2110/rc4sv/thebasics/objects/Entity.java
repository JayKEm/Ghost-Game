package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

@SuppressLint("DrawAllocation")
public abstract class Entity {

	//entities do not move, therefore do not handle collisions
	
	protected String id;
	protected int x, y;
	protected int height, width;
	protected Bitmap image;
	protected OurView ov;
	
	protected Rect bounds;
	
	//all the entities on the level
	protected ArrayList<Entity> world;
	
	public Entity(OurView ourView, Bitmap src, int x, int y) {
		image = src;
		ov = ourView;
		this.x = x; 
		this.y = y;
	}

	public Entity() {}

	public void onDraw(Canvas canvas) {
//		Rect src = new Rect(0, 0, width, height);
		Rect dst = new Rect(x + ov.offsetX, y + ov.offsetY, 
				x + ov.offsetX + width*2, y + ov.offsetY + height*2);
		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, null, dst, null);
		
		bounds.set(x + ov.offsetX + width/4, y + ov.offsetY, 
				x + ov.offsetX + width*2, y + ov.offsetY + height*2);
	}

	public boolean isColliding(Entity s){
		return Rect.intersects(bounds, s.getBounds());
	}
	
	public String getId(){
		return id;
	}
	
	public Rect getBounds() {
		return bounds;
	}
	
	public int[] getLocationVector(){
		int[] location = new int[2];
		location[0] = x;
		location[1] = y;
		
		return location;
	}
}
