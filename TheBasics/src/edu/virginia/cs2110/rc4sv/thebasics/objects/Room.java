package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Room {

	private OurView ov;
	private Player player;
	private ArrayList<Entity> items;
	private ArrayList<Vector> emptyCells;
	private Level level;
	private Room[] adjacentRooms = new Room[4];
	private Wall[] walls = new Wall[4];
//	private int[][] doorLocations;
	public int x, y, width, height;
	
	private Rect bounds;
	
	//locations for adjacent rooms in room array
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int MIN_TILES = 6;
	public static final int MAX_TILES = 12;
	
	public Room(){
		
	}
	
	public Room(OurView ov, Player p, Level level){
		this(ov, p, level, 0, 0);
	}
	
	public Room(OurView ov, Player player, Level level, int x, int y){
		//Create a room with a random size, from 10 to 20 tiles
		//for now, we're only testing that a room can be created, so it's within the bounds of the view
		this(ov, player, level, x, y, (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2, 
				(int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2);
	}
	
	public Room(OurView ov, Player player, Level level, int x, int y, int width, int height){
		this.ov = ov;
		this.player = player;
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		bounds = new Rect(x, y, width, height);
		create();
	}
	
	//makes a room if there isn't already a room in that location
	//generated room must be inside the world boundaries
	public void generateAdjacentRoom(int location){
		if(adjacentRooms[location]!= null) return;
		if(level.getRooms().size() == level.MAX_ROOMS) return;
		boolean noIntersections = false;
		
		while(!noIntersections){
			adjacentRooms[location] = new Room(ov, player, level, x, y);
			noIntersections = isIntersecting(adjacentRooms[location]);
		}
		
		int adjacent = 0;
		if(location == UP) adjacent = DOWN;
		if(location == DOWN) adjacent = UP;
		if(location == LEFT) adjacent = RIGHT;
		if(location == RIGHT) adjacent = LEFT;
		
		adjacentRooms[location].setAdjacentRoom(this, adjacent);
		adjacentRooms[location].create();
	}
	
	//create all walls and items, and ghosts
	public void create() {
		emptyCells = new ArrayList<Vector>();
		for (int i = 0; i < width; i+=Tile.SIZE*2)
			for (int j = 0; j < height; j+=Tile.SIZE*2){
				emptyCells.add(new Vector(i+x*Tile.SIZE*2, j+y*Tile.SIZE*2));
				Log.d("hello","");
			}
		
//		Log.d("Cells X:"+(width/(Tile.SIZE*2)), "Cells Y: "+height/(Tile.SIZE*2));
		walls[UP] = new Wall(ov, x, y, width/(Tile.SIZE*2) - 1, 1);
		walls[DOWN] = new Wall(ov, x + Tile.SIZE*2, y + height - Tile.SIZE*2, width/(Tile.SIZE*2) - 1, 1);
		walls[LEFT] = new Wall(ov, x, y + Tile.SIZE*2, 1, height/(Tile.SIZE*2) - 1);//==============
		walls[RIGHT] = new Wall(ov, x + width - Tile.SIZE*2, y, 1, height/(Tile.SIZE*2) - 1); //============
		
		for(Wall w: walls){
			if(w != null)
				for(Tile t: w.getTiles()){
					boolean r = emptyCells.remove(t.getLocation());
					Log.d("removed cell", "<"+t.getLocation().x+","+t.getLocation().y+"> "+r);
					level.addToWorld(t);
				}
//			level.addToWorld(w);
		}
		
		for(Vector v : emptyCells){
			Log.d("created cell <"+v.x+","+v.y+">", "");
			level.addToWorld(new Floor(ov, v));
		}
				
		items = new ArrayList<Entity>();
		// instantiate objects
		// remove item cells from emptycells 
	}
	
	public void setAdjacentRoom(Room r, int adjacent) {
		adjacentRooms[adjacent] = r;
	}
	
	public ArrayList<Entity> getItems(){
		return items;
	}
	
	public Rect getBounds(){
		return bounds;
	}
	
	public ArrayList<Vector> getEmptyCells(){
		return emptyCells;
	}
	
	public boolean isIntersecting(Room room){
		for(Room r : level.getRooms())
			if(Rect.intersects(room.getBounds(), r.getBounds()))
				return true;
		return false;
	}
}
