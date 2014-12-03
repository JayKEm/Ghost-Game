package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Entity;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Level;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Vector;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class OurView extends SurfaceView implements Runnable{
	
	private Thread t = null;
	private SurfaceHolder holder;
	private boolean paused = false;
	private Bitmap weaponLogo;
	private Bitmap heart;
	private Bitmap coin;
	private Bitmap button2;
	private Bitmap blueball;
	private Bitmap pausebutton, quit, resume, newGame;
	private Bitmap playerSprites, ghostSprites, warning;
	private Bitmap up, down, left, right, vignette, shield, fireball, icebolt;
	private Level myLevel;
	private Player player;
	private ArrayList<MediaPlayer> sounds, soundsToRemove, soundsToAdd;
	private boolean isPaused;
	private float volume;
	
	public static final int DEFAULT_ZOOM = 2;
	public int dw, dh, bw, bh, zoom = DEFAULT_ZOOM;
	public int offsetX, offsetY; //visual offset of level
	public boolean initialized = false;
	public Bitmap closedChest, openChest, goldCoin, silverCoin, bronzeCoin, weaponSprites, tombstone;
	
	public OurView(Context context) {
		super(context);
		holder = getHolder();
		sounds = new ArrayList<MediaPlayer>();
		soundsToRemove = new ArrayList<MediaPlayer>();
		soundsToAdd = new ArrayList<MediaPlayer>();
	}

	public void run() {
		if(!initialized){
			create();
		}
		
		while(!paused) {
			if(!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			this.render(c);
			holder.unlockCanvasAndPost(c);
			
			if (myLevel.ghosts <= 0)
				Log.i("no ghosts on level","");
			
			sounds.addAll(soundsToAdd);
			for(MediaPlayer m: sounds)
				if(!m.isPlaying()){
					m.release();
					soundsToRemove.add(m);
				}
			sounds.removeAll(soundsToRemove);
			
			soundsToAdd = new ArrayList<MediaPlayer>();
			soundsToRemove = new ArrayList<MediaPlayer>();
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
		if (this.isPaused == false) {
			myLevel.updateRender(canvas);
		}
		
		canvas.drawBitmap(vignette, null, new Rect(0,0,getWidth(),getHeight()), null);
		
		//gui
		if (!isPaused) {
			canvas.drawBitmap(up, null, new Rect(dw, getHeight()- dh*3, dw*2, getHeight()-dh*2), null);
			canvas.drawBitmap(down, null, new Rect(dw, getHeight()- dh, dw*2, getHeight()), null);
			canvas.drawBitmap(left, null, new Rect(0, getHeight()- dh*2, dw, getHeight()-dh), null);
			canvas.drawBitmap(right, null, new Rect(dw*2, getHeight()- dh*2, dw*3, getHeight()-dh), null);
			canvas.drawBitmap(button2, null, new Rect(dw*4, getHeight()- dh*2, dw*5, getHeight()-dh), null);
			canvas.drawBitmap(blueball, null, new Rect(dw*6, getHeight()- dh*2, dw*7, getHeight()-dh), null);
			canvas.drawBitmap(pausebutton , null, new Rect(dw*8, getHeight()- dh*2, dw*9, getHeight()-dh), null);
		} else {
			canvas.drawBitmap(resume, null, new Rect(getWidth()/2-bw/2, getHeight()/2-bh/2-bh*2, getWidth()/2 + bw/2, getHeight()/2+bh/2-bh*2), null);
			canvas.drawBitmap(newGame, null, new Rect(getWidth()/2-bw/2, getHeight()/2-bh/2, getWidth()/2 + bw/2, getHeight()/2+bh/2), null);
			canvas.drawBitmap(quit, null, new Rect(getWidth()/2-bw/2, getHeight()/2-bh/2+bh*2, getWidth()/2 + bw/2, getHeight()/2+bh/2+bh*2), null);
		}

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
		canvas.drawText(""+player.score, getWidth() - cw - 4 - String.valueOf(player.score).length()*10, 23, paint);
		
		//weapon logo
		if (myLevel.getPlayer().hasWeapon()) {
			canvas.drawBitmap(weaponLogo,null, new Rect(p*6*hw*2, p, p*6*hw*2+hw*2, p+2*hh), null);
		}
		
		//ghost alert
		Vector v = player.getLocation();
		int ww = warning.getWidth(); int wh = warning.getHeight();
		if(myLevel.getWarn()&&!isPaused)
			canvas.drawBitmap(warning, null, new Rect(v.x + player.getWidth()/2 + ww/4 + ww/2, v.y-wh+2,
					v.x + player.getWidth()+ ww/2, v.y + 2), null);
		
		//debug
//		String debugText = "       <"+(int)((player.getLocation().x-offsetX)/(Tile.SIZE*zoom))+
//				","+(int)((player.getLocation().y-offsetY)/(Tile.SIZE*zoom))+">";
//		String debugText = ""+player.isDead+" : "+player.health;
//		canvas.drawText(debugText, getWidth() - 4 - debugText.length()*10, 50, paint);
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
	
	public Bitmap getGhostSprites(){
		return ghostSprites;
	}
	
	public Bitmap getPlayerSprites(){
		return playerSprites;
	} 
	
	public Bitmap getShield(){
		return shield;
	}
	
	public void create(){

		initialized = true;
		myLevel = new Level(this, 10, 8); //debug level
		
		//load image resources
		weaponLogo = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_weaponsprite);
		weaponSprites = BitmapFactory.decodeResource(getResources(), R.drawable.weaponsprite);
		playerSprites = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		ghostSprites = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);
		closedChest = BitmapFactory.decodeResource(getResources(), R.drawable.chest_closed);
		pausebutton = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button);
		silverCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_silver);
		bronzeCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_bronze);
		openChest = BitmapFactory.decodeResource(getResources(), R.drawable.chest_open);
		tombstone = BitmapFactory.decodeResource(getResources(), R.drawable.tombstone);
		goldCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_gold);
		coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin_goldpart);
		blueball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		vignette = BitmapFactory.decodeResource(getResources(), R.drawable.vignette);
		right = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
		warning = BitmapFactory.decodeResource(getResources(),  R.drawable.warning);
		fireball = BitmapFactory.decodeResource(getResources(), R.drawable.explode);
		newGame = BitmapFactory.decodeResource(getResources(), R.drawable.new_game);
		icebolt = BitmapFactory.decodeResource(getResources(), R.drawable.icebolt);
		down = BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow);
		left = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
		resume = BitmapFactory.decodeResource(getResources(), R.drawable.resume);
		shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
		button2 = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
		heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
		up = BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow);
		quit = BitmapFactory.decodeResource(getResources(), R.drawable.quit);
		volume = 1f;
		
		dw = up.getWidth();
		dh = up.getHeight();
		bw = quit.getWidth()/3;
		bh = quit.getHeight()/3;

		//create level
		myLevel.generate(this);
		player = myLevel.spawnPlayer(playerSprites);
		((MainGame) getContext()).setPlayer(player);
		
		for(Entity s : myLevel.getWorld())
			if (s instanceof Sprite)
				((Sprite) s).setWorld(myLevel.getWorld());
	}

	public Bitmap getFireball() {
		return fireball;
	}
	
	public Bitmap getIcebolt(){
		return icebolt;
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

	public void playSound(int resid) {
		MediaPlayer m = MediaPlayer.create(getContext(), resid);
		m.setVolume(volume, volume);
		m.start();
		soundsToAdd.add(m);
	}
	
	public void setVolume(float v){
		volume = v;
		MediaPlayer m = ((MainGame) getContext()).getMusic();
		if (m!=null)
			m.setVolume(volume, volume);
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setIsPaused(boolean pause) {
		this.isPaused=pause;
	}
	
	public boolean getIsPaused() {
		return this.isPaused;
	}
}
