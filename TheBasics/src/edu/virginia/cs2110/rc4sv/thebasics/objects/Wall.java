package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
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
		v = new Vector(x, y);
		bounds = new Rect(x, y, x + this.width*Tile.SIZE, y + this.height*Tile.SIZE);
		segment = new ArrayList<Tile>();
		skeletonTiles = new ArrayList<Vector>();
		id="wall";
		createSkeleton();
	}
	
	public void render(Canvas canvas) {}
	
	public boolean removeTile(Vector r){
		for (Vector v : skeletonTiles)
			if(r.equals(v)){
				Log.d("removed wall", v.toString());
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
				skeletonTiles.add(new Vector(v.x + i * (Tile.SIZE*2), v.y + j * (Tile.SIZE*2)));
	}
	
	public void create(){
		for(Vector v : skeletonTiles)
				segment.add(new Tile(ov, v.x, v.y));
	}
}
