package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Entity;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Level;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Room;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

public class OurView extends SurfaceView implements Runnable{
	
	private Thread t = null;
	private SurfaceHolder holder;
	private boolean isItOK = false;
	private Bitmap playerSprites, ghostSprites, goldCoin, silverCoin, bronzeCoin, weaponSprites, weaponLogo, heart, coin;
	private Bitmap up, down, left, right;
	private Level myLevel;
	private Player player;
	public int dw, dh;
	public int offsetX, offsetY; //visual offset of level
	public boolean initialized = false;
	
	public OurView(Context context) {
		super(context);
		holder = getHolder();
	}

	public void run() {
		if(!initialized)
			create();
		while(isItOK == true) {
			if(!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			render(c);
			holder.unlockCanvasAndPost(c);
			
			if (myLevel.ghosts <= 0)
				Log.d("no ghosts on level","");
		}
	}
	
	public Level getLevel(){
		return myLevel;
	}

	//order of draw matters
	protected void render(Canvas canvas) {
		//bg
		canvas.drawColor(Color.BLACK);
		
		//level
		myLevel.render(canvas);
		
		//gui
		canvas.drawBitmap(up, null, new Rect(dw, getHeight()- dh*3, dw*2, getHeight()-dh*2), null);
		canvas.drawBitmap(down, null, new Rect(dw, getHeight()- dh, dw*2, getHeight()), null);
		canvas.drawBitmap(left, null, new Rect(0, getHeight()- dh*2, dw, getHeight()-dh), null);
		canvas.drawBitmap(right, null, new Rect(dw*2, getHeight()- dh*2, dw*3, getHeight()-dh), null);
		//player health
		int hw = heart.getWidth(); int hh = heart.getHeight(); 	int p = 2;
		for(int i = 0; i < player.health; i++)
			canvas.drawBitmap(heart,null, new Rect(p*i*hw*2, p, p*i*hw*2+hw*2, p+2*hh), null);
		//player score
		int cw = coin.getWidth(); int ch = coin.getWidth();
		canvas.drawBitmap(coin, null, new Rect(getWidth()-p-cw, p, getWidth()-p, p+ch), null);
		
		//weapon logo
		if (myLevel.getPlayer().hasWeapon()) {
			canvas.drawBitmap(weaponLogo,null, new Rect(p*6*hw*2, p, p*6*hw*2+hw*2, p+2*hh), null);
		}
	}

	public void pause () {
		isItOK = false;
		while(true){
			try{
				t.join();
			} catch ( InterruptedException e)  {
				e.printStackTrace();;
			}
			break;
		}
		t = null;
	}
	
	public void resume(){
		isItOK = true;
		t = new Thread(this);
		t.start();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Level getMyLevel() {
		return this.myLevel;
	}
	
	public void create(){
		initialized = true;
		myLevel = new Level(4, 12); //debug level
		
		playerSprites = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		ghostSprites = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);
		goldCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_gold);
		silverCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_silver);
		bronzeCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_bronze);
		weaponSprites = BitmapFactory.decodeResource(getResources(), R.drawable.weaponsprite);
		heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
		coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_goldpart);
		up = BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow);
		down = BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow);
		left = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
		right = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
		weaponLogo = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_weaponsprite);
		
		dw = up.getWidth();
		dh = up.getHeight();

		//create level
		myLevel.addRoom(new Room(this, player, myLevel, 0, 0, false)); //debug room
		player = myLevel.spawnPlayer(this, playerSprites);
		myLevel.spawnGhosts(this, ghostSprites);
		myLevel.spawnCoins(this, 4, goldCoin);
		myLevel.spawnCoins(this, 5, silverCoin);
		myLevel.spawnCoins(this, 7, bronzeCoin);
		myLevel.spawnWeapons(this, weaponSprites);
		
		for(Entity s : myLevel.getWorld())
			if (s instanceof Sprite)
				((Sprite) s).setWorld(myLevel.getWorld());
	}
}
