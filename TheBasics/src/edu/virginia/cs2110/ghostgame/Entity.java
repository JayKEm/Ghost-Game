package edu.virginia.cs2110.ghostgame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public abstract class Entity extends ImageView {

	//==================================================
	//movable image view
	//collisions will be detected between image regions
	//==================================================
	
	//object will face right by default
	public boolean isFacingLeft = false;
	
	//all the entities on the level
	protected ArrayList<Entity> world;
	
	protected float[] speed = new float[2];
	
	//the following constructors are temporary
	//they are necessary for image views
	public Entity(Context context) {
		super(context);
	}
	
	public Entity(Context context, AttributeSet set) {
		super(context, set);
	}
	
	public Entity(Context context, AttributeSet set, int n) {
		super(context, set, n);
	}
	
	public void onDraw(Canvas c){
		try{
			handleCollision();
		} catch(NullPointerException e){
			Log.d("Entity", "World must be set before collision can handled.");
		}
		
		super.onDraw(c);
	}
	
	//move this object in a certain direction
	public void left(){
		setX(getX() - 1f);
		if(!isFacingLeft)
			changeDirection();
		speed[0] = -1f;
		speed[1] = 0;
	}
	
	public void up() {
		setY(getY() + 1f);
		speed[1] = 1f;
		speed[0] = 0;
	}
	
	public void down(){
		setY(getY() - 1f);
		speed[1] = -1f;
		speed[0] = 0;
	}
	
	public void right(){
		setX(getX() + 1f);
		if(isFacingLeft)
			changeDirection();
		speed[0] = 1f;
		speed[1] = 0;
	}
	
	public void changeDirection(){
		isFacingLeft = !isFacingLeft;
		Matrix m = this.getImageMatrix();
		
		m.preScale(-1f, 1f);
		this.setImageMatrix(m);
	}
	
	public abstract void handleCollision();
	
	public boolean isColliding(Entity e){
		Rect r1 = new Rect(), r2 = null;
		getDrawingRect(r1);
		e.getDrawingRect(r2);
	
		return Rect.intersects(r1, r2);
	}
	
	//put the entity back where it was before it tried to touch the wall
	public void reAdjust(){
		setX(getX()-speed[0]);
		setY(getY()-speed[1]);
	}
	
	
}
