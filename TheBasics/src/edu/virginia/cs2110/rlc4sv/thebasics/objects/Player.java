package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.util.ArrayList;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Player extends Sprite {

	public int score;
	public int health;
	public boolean locked = false, isDead = false;
	public Entity interactable;
	private boolean hasWeapon;
	public int ghostsKilled;
	private long weaponTimer;
	private boolean canGetHurt;
	private long damageTimer;
	private Rect interactBounds;
	private static ArrayList<String> interactableList = new ArrayList<String>();
	
	static {
		interactableList.add("chest");
	}
	
	public static int MAX_HEALTH = 6;
	public static final int MIN_HEALTH = 0;
	
	public Player(OurView ov, Bitmap src, int x, int y, int cellX, int cellY){
		super(ov, src , x, y);
		bounds = new Rect(x + width/4, y+8, x + width*ov.zoom, y + 28*ov.zoom+8);
		
		ov.offsetX = x - cellX;
		ov.offsetY = y - cellY;
		
		this.health = MAX_HEALTH;
		this.damageTimer = System.currentTimeMillis();
		id = "Player";
		hasWeapon = false;
		
		interactBounds = new Rect();
		setVelocity(0,5);
	}

	public void update() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e)  {
			e.printStackTrace();
		}
		
		changeFrame++;
		if(changeFrame == 20){
			currentFrame = ++currentFrame % 4;
			changeFrame=0;
		}
		
		if(hasWeapon() && (System.currentTimeMillis() - weaponTimer > 25000)) {
			MediaPlayer.create(ov.getContext(), R.raw.power_down).start();
			loseWeapon();
		}
		
		if(System.currentTimeMillis() - damageTimer > 5000) {
			canGetHurt = true;
			damageTimer = System.currentTimeMillis();
		}
		
		
		if(move){
			ov.offsetX -= velocity.x;
			ov.offsetY -= velocity.y;
		}
		
		try{
			remove();
			handleCollision();
		} catch(NullPointerException e){
			Log.i(id, "World must be set before collision can handled.");
		}
	}

	public void render(Canvas canvas) {
		update();
		
		int srcY = direction * height;
		int srcX = currentFrame * width;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(location.x, location.y, location.x + width*ov.zoom, location.y + height*ov.zoom);
		
		updateIBounds();
//		Paint p = drawBounds(canvas);
//		canvas.drawRect(interactBounds, p);
		canvas.drawBitmap(image, src, dst, null);
	}

	public void handleCollision() {
		for (Entity s : world){
			if(isColliding(s) && !this.equals(s)){
				if(s instanceof Ghost && !hasWeapon)
					if (canGetHurt == true) {
						damage();
						MediaPlayer.create(ov.getContext(), R.raw.player_hurt).start();
					}
				if(s instanceof Tile || s instanceof Chest){
					reAdjust();
				} if(s instanceof Coin && !locked){
					score += ((Coin) s).getValue();
					MediaPlayer.create(ov.getContext(), R.raw.coin).start();
					level.removeFromWorld(s);
				} if(s instanceof Weapon){
					setHasWeapon();
					level.removeFromWorld(s);
					MediaPlayer.create(ov.getContext(), R.raw.power_up).start();
				} 
			} if(Rect.intersects(s.getBounds(), interactBounds))
				if(interactableList.contains(s.id.toLowerCase(Locale.getDefault())))
					interactable = s;
		}
	}
	
	public void damage() {		
		this.loseHealth();
		setCanGetHurt(false);


		if (this.getHealth() <= 0) {
			isDead = true;
		}
	}

	public void interact() {
		if(interactable!=null){
			try{
				interactable.interact(this);
			} catch(Exception e){}
		}
	}
	
	public void updateIBounds(){
		int offset = 5;
		if(velocity.x>0 && velocity.y==0){
			interactBounds.set(location.x, location.y, location.x + (int)(width*3/4)*ov.zoom, location.y + height*ov.zoom);
			interactBounds.offset(width*ov.zoom + offset, 0);
		} if(velocity.x<0 && velocity.y==0){
			interactBounds.set(location.x, location.y, location.x + (int)(width*3/4)*ov.zoom, location.y + height*ov.zoom);
			interactBounds.offset(interactBounds.width()*-1, 0);
		} if(velocity.x==0 && velocity.y>0){
			interactBounds.set(location.x, location.y, location.x + width*ov.zoom, location.y + (int)(height*3/4)*ov.zoom);
			interactBounds.offset(2, height*ov.zoom);
		} if(velocity.x==0 && velocity.y<0){
			interactBounds.set(location.x, location.y, location.x + width*ov.zoom, location.y + (int)(height*3/4)*ov.zoom);
			interactBounds.offset(2, interactBounds.height()*-1);
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void reAdjust(){
		ov.offsetX += velocity.x;
		ov.offsetY += velocity.y;
	}
	
	public void setHasWeapon(){
		hasWeapon = true;
		this.weaponTimer = System.currentTimeMillis();
	}
	
	public void loseWeapon() {
		this.hasWeapon = false;
	}
	
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	public void killGhost() {
		this.ghostsKilled++;
		score+=100;
		MediaPlayer.create(ov.getContext(), R.raw.ghost).start();
	}
	
	public int getGhostsKilled () {
		return this.ghostsKilled;
	}
	
	public long getWeaponTimer() {
		return this.weaponTimer;
	}
	
	public long getDamageTimer() {
		return this.damageTimer;
	}
	
	public void setDamageTimer() {
		this.damageTimer = System.currentTimeMillis();
	}
	
	public void setCanGetHurt(boolean a) {
		this.canGetHurt = a;
	}

	public void interact(Player player) {}

	public void remove() {
		interactable = null;
	}

	@Override
	public void loseHealth() {
		this.health --;
		
	}
}
