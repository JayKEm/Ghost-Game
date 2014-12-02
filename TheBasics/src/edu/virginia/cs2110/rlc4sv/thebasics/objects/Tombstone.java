package edu.virginia.cs2110.rlc4sv.thebasics.objects;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Tombstone extends Entity {
	
	long usageTimer;
	
	public Tombstone(OurView ov, Level level, Bitmap image, int x, int y){
		super(ov, image, x, y);
		this.level = level;
		width = image.getWidth();
		height = image.getHeight();
		id ="Tombstone";
		usageTimer = System.currentTimeMillis();
		bounds = new Rect(x + width/4, y, x + width*ov.zoom, y + height*ov.zoom);
	}

	//explosion!!!
	public void interact(Player player) {
		player.remove();
		if(System.currentTimeMillis()-usageTimer<100)
			return;

		MediaPlayer.create(ov.getContext(), R.raw.tombstone).start();
		usageTimer = System.currentTimeMillis();
		Fireball f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, 0));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, 0));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(0, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(0, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(f);
		f = new Fireball(ov, ov.getFireball(), location.x, location.y);
		f.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(f);
		Icebolt i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, 0));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, 0));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(0, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(0, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, Sprite.DEFAULT_SPEED*2));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*2, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(i);
		i = new Icebolt(ov, ov.getIcebolt(), location.x, location.y);
		i.setVelocity(new Vector(Sprite.DEFAULT_SPEED*-2, Sprite.DEFAULT_SPEED*-2));
		level.addToWorld(i);
	}
}
