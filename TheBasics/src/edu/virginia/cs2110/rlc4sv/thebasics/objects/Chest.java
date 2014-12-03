package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class Chest extends Entity {
	
	public boolean open = false, timing = false;
	private int time;
	private Bitmap openImage;
	private ArrayList<Coin> coins;
	private Coin current;
	
	private static final int MAX_COINS = 20;
	private static final int MIN_COINS = 5;
	
	public Chest(OurView ov, Level level, Player player, int x, int y){
		super(ov, ov.closedChest, x, y);
		width = image.getWidth();
		height = image.getHeight();

		bounds = new Rect(x + width/4, y, x + width*ov.zoom, y + height*ov.zoom);
		this.level = level;
		this.player = player;
		this.openImage = ov.openChest;
		this.coins = randomizeContents();
		
		id = "Chest";
	}
	
	public Chest(OurView ov, Level level, Player player, int x, int y, ArrayList<Coin> coins){
		super(ov, ov.closedChest, x, y);
		width = image.getWidth();
		height = image.getHeight();

		bounds = new Rect(x + width/4, y, x + width*ov.zoom, y + height*ov.zoom);
		this.level = level;
		this.player = player;
		this.openImage = ov.openChest;
		this.coins = coins;
		
		id = "Chest";
	}
	
	public void render(Canvas canvas){
		update();

		Rect dst = new Rect(location.x + ov.offsetX, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
		bounds.set(location.x + ov.offsetX + width/4, location.y + ov.offsetY, 
				location.x + ov.offsetX + width*ov.zoom, location.y + ov.offsetY + height*ov.zoom);
//		drawBounds(canvas);
		canvas.drawBitmap(image, null, dst, null);
	}
	
	public void update(){
		if(timing){
			time++;
			current.location.y = location.y - (int)(time/1.5f);
			if(time>=10){
				Coin c = Coin.clone(current);
				if (!removeCoin()){
					timing = false;
					player.locked = false;
					level.removeFromWorld(c);
					player.remove();
				}
			}
		}
	}
	
	public void interact(Player player){
		if(open)
			return;
		open = true;
		image = openImage;
		ov.playSound(R.raw.chest);
		
		this.player = player;
		player.locked = true;
		timing = true;
		
		removeCoin();
	}
	
	private boolean removeCoin(){
		try {
			level.removeFromWorld(current);
			current = (Coin) coins.toArray()[(int) Math.random()*coins.size()];
			
			ov.playSound(R.raw.coin);
			level.addToWorld(current);
			coins.remove(current);
			player.score += current.getValue();
			time = 0;
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	private ArrayList<Coin> randomizeContents(){
		ArrayList<Coin> coins = new ArrayList<Coin>();
		
		int numCoins = (int) (Math.random()*(MAX_COINS-MIN_COINS)+ MIN_COINS);
		for (int i = 0; i < numCoins; i++){
			int value = (int) (Math.random()*100);
			if (value < 40)
				coins.add(new Coin(ov, ov.bronzeCoin, location.x, location.y));
			else if (value < 75)
				coins.add(new Coin(ov, ov.silverCoin, location.x, location.y));
			else
				coins.add(new Coin(ov, ov.goldCoin, location.x, location.y));
		}
		
		return coins;
	}
	
	public ArrayList<Coin> getCoins(){
		return coins;
	}
}
