package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Entity;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Level;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class OurView extends SurfaceView implements Runnable{
	
	private Thread t = null;
	private SurfaceHolder holder;
	private boolean paused = false;
	private Bitmap playerSprites, ghostSprites, warning;
	private Bitmap weaponLogo;
	private Bitmap heart;
	private Bitmap coin;
	private Bitmap button2;
	private Bitmap up, down, left, right;
	private Level myLevel;
	private Player player;
	
	public int dw, dh, zoom =0;
	public int offsetX, offsetY; //visual offset of level
	public boolean initialized = false;
	public Bitmap closedChest, openChest, goldCoin, silverCoin, bronzeCoin, weaponSprites;
	
	public OurView(Context context) {
		super(context);
		holder = getHolder();
	}

	public void run() {
		if(!initialized)
			create();
		while(!paused) {
			if(!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			this.render(c);
			holder.unlockCanvasAndPost(c);
			
			if (myLevel.ghosts <= 0)
				Log.i("no ghosts on level","");
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
		canvas.drawBitmap(button2, null, new Rect(dw*4, getHeight()- dh*2, dw*5, getHeight()-dh), null);

		//player health
		int hw = heart.getWidth(); int hh = heart.getHeight(); 	int p = 2;
		
//		Log.i("health",""+player.getHealth());
		for(int i = 0; i < player.getHealth(); i++)
			canvas.drawBitmap(heart,null, new Rect(p*i*hw*2, p, p*i*hw*2+hw*2, p+2*hh), null);
		
		//player score
		int cw = coin.getWidth(); int ch = coin.getWidth();
		canvas.drawBitmap(coin, null, new Rect(getWidth()-p-cw, p, getWidth()-p, p+ch), null);
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		paint.setTextSize(20f);
		canvas.drawText(""+player.score, getWidth() - cw - 4 - String.valueOf(player.score).length()*12, 23, paint);
		
		//weapon logo
		if (myLevel.getPlayer().hasWeapon()) {
			canvas.drawBitmap(weaponLogo,null, new Rect(p*6*hw*2, p, p*6*hw*2+hw*2, p+2*hh), null);
		}
		
		//ghost alert
		Vector v = player.getLocation();
		int ww = warning.getWidth(); int wh = warning.getHeight();
		if(myLevel.getWarn())
			canvas.drawBitmap(warning, null, new Rect(v.x + player.getWidth()/2 + ww/4 + ww/2, v.y-wh+2,
					v.x + player.getWidth()+ ww/2, v.y + 2), null);
		
		//debug
		String debugText = "<"+(int)((player.getLocation().x-offsetX)/64f)+","+(int)((player.getLocation().y-offsetY)/64f)+">";
		canvas.drawText(debugText, getWidth() - 4 - debugText.length()*12, 50, paint);
	}

	public void pause () {
		paused = true;
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
		paused = false;
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
		myLevel = new Level(this, 15, 8); //debug level
		
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
		button2 = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
		openChest = BitmapFactory.decodeResource(getResources(), R.drawable.chest_open);
		closedChest = BitmapFactory.decodeResource(getResources(), R.drawable.chest_closed);
		warning = BitmapFactory.decodeResource(getResources(),  R.drawable.warning);
		
		dw = up.getWidth();
		dh = up.getHeight();

		//create level
		myLevel.generate(this);
		player = myLevel.spawnPlayer(playerSprites);
		myLevel.spawnGhosts(ghostSprites);
		
		for(Entity s : myLevel.getWorld())
			if (s instanceof Sprite)
				((Sprite) s).setWorld(myLevel.getWorld());
	}
}
