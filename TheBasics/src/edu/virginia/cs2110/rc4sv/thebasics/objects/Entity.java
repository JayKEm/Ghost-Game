package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

@SuppressLint("DrawAllocation")
public abstract class Entity {

	//entities do not move, therefore do not handle collisions
	
	protected String id;
	protected Vector v;
	protected int height, width;
	protected Bitmap image;
	protected OurView ov;
	protected Level level;
	protected int X_FRAMES, FRAMES_Y;
	
	protected Rect bounds;
	
	//all the entities on the level
	protected ArrayList<Entity> world;
	
	public Entity(OurView ourView, Bitmap src, int x, int y) {
		image = src;
		ov = ourView;
		v = new Vector(x, y);
		level = ov.getLevel();
	}

	public Entity() {}

	public void render(Canvas canvas) {
//		Rect src = new Rect(0, 0, width, height);
		Rect dst = new Rect(v.x + ov.offsetX, v.y + ov.offsetY, 
				v.x + ov.offsetX + width*2, v.y + ov.offsetY + height*2);
//		canvas.drawRect(bounds, new Paint(Color.RED));
		canvas.drawBitmap(image, null, dst, null);
		
		bounds.set(v.x + ov.offsetX + width/4, v.y + ov.offsetY, 
				v.x + ov.offsetX + width*2, v.y + ov.offsetY + height*2);
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
	
	public Vector getLocation(){
		return v;
	}
}



