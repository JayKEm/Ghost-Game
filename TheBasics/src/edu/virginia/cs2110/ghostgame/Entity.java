package edu.virginia.cs2110.ghostgame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class Entity extends ImageView {

	//==================================================
	//movable image view
	//collisions will be detected between image regions
	//==================================================
	
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
	
	//move this object in a certain direction
	public void left(){
		this.setX(this.getX() - 1f);
	}
	
	public void up() {
		this.setY(this.getY() + 1f);
	}
	
	public void down(){
		this.setY(this.getY() - 1f);
	}
	
	public void right(){
		this.setX(this.getX() + 1f);
	}
	
	public abstract void handleCollision();
}
