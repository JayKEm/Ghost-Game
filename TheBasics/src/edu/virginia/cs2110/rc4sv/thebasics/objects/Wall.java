package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Wall extends Entity{
	
//	A wall is basically a line of tiles
	private ArrayList<Tile> segment;
	private ArrayList<Vector> skeletonTiles;
	public boolean hasDoor = false;
	
	//width and height are in number of tiles
	public Wall(OurView ov, Level level, int x, int y, int width, int height) {
		super();
		this.level = level;
		this.width = width;
		this.height = height;
		this.ov = ov;
		location = new Vector(x, y);
		bounds = new Rect(0,0,0,0);
		segment = new ArrayList<Tile>();
		skeletonTiles = new ArrayList<Vector>();
		id="wall";
		createSkeleton();
	}
	
	public void render(Canvas canvas) {}
	
	public boolean removeTile(Vector r){
		for (Vector v : skeletonTiles)
			if(r.equals(v)){
//				Log.d("removed tile", v.toString());
				skeletonTiles.remove(v);
				hasDoor = true;
				return true;
			}
		return false;
	}
	
	public ArrayList<Tile> getTiles(){
		return segment;
	}
	
	public ArrayList<Vector> getTileLocations() {
		return skeletonTiles;
	}
	
	public void createSkeleton(){
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				skeletonTiles.add(new Vector(location.x + i * (Tile.SIZE*2), location.y + j * (Tile.SIZE*2)));
	}
	
	public void create(){
		for(Vector v : skeletonTiles)
				segment.add(new Tile(ov, v.x, v.y));
	}

	public void addTile(Vector location) {
		skeletonTiles.add(location);
		segment.add(new Tile(ov, location.x, location.y));
	}

	public void interact(Player player) {}
}
