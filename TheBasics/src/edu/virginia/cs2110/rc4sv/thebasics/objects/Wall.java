package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Wall extends Entity{
	
//	A wall is basically a line of tiles
	private ArrayList<Tile> segment;
	public boolean hasDoor = false;
	
	//width and height are in number of tiles
	public Wall(OurView ov, Level level, int x, int y, int width, int height) {
		super();
		this.level = level;
		this.width = width*Tile.SIZE;
		this.width = width*Tile.SIZE;
		bounds = new Rect(x, y, x + this.width, y + this.height);
		segment = new ArrayList<Tile>();
		id="wall";
		
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				segment.add(new Tile(ov, x + i * (Tile.SIZE*2), y + j * (Tile.SIZE*2)));
	}
	
	public void render(Canvas canvas) {}
	
	public boolean removeTile(Vector v){
		if(hasDoor)
			return false;
		for (Tile t : segment)
			if(t.getLocation().equals(v)){
				level.removeFromWorld(t);
				hasDoor = true;
				return segment.remove(t);
			}
		return false;
	}
	
	public ArrayList<Tile> getTiles(){
		return segment;
	}
	
	public ArrayList<Vector> getTileLocations() {
		ArrayList<Vector> cells = new ArrayList<Vector>();
		for(Tile t : segment)
			cells.add(t.getLocation());
		return cells;
	}
}
