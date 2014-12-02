package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

@SuppressLint("DrawAllocation")
public abstract class Entity {

	//entities do not move, therefore do not handle collisions
	
	protected String id;
	protected Vector location;
	protected int height, width;
	protected Bitmap image;
	protected OurView ov;
	protected Level level;
	protected int X_FRAMES, FRAMES_Y;
	protected Player player;
	
	protected Rect bounds;
	
	//all the entities on the level
	protected ArrayList<Entity> world;
	
	public Entity(OurView ourView, Bitmap src, int x, int y) {
		image = src;
		ov = ourView;
		location = new Vector(x, y);
		level = ov.getLevel();
		id="";
	}

	public Entity() {}

	public void render(Canvas canvas) {
		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		
		bounds.set(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		canvas.drawBitmap(image, null, dst, null);
//		drawBounds(canvas);
	}
	
	public Paint drawBounds(Canvas canvas){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
	    if(id.toLowerCase(Locale.ENGLISH).equals("floor"))
	    	paint.setColor(Color.BLUE);
	    else 
	    	paint.setColor(Color.GREEN);
	    canvas.drawRect(bounds, paint);
	    return paint;
	}

	public boolean isColliding(Entity s){
		return Rect.intersects(bounds, s.getBounds());
	}
	
	public String getId(){
		return id;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Rect getBounds() {
		return bounds;
	}
	
	public Vector getLocation(){
		return location;
	}
	
	public void setWorld(ArrayList<Entity> world){
		this.world = world;
	}
	
	public abstract void interact(Player player2);

	public void setPlayer(Player player) {
		this.player = player;
	}
}



