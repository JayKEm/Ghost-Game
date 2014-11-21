package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Rect;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

public class Room {

	private OurView ov;
	private Player player;
	private ArrayList<Entity> items;
	private ArrayList<Vector> emptyCells;
	private HashMap<Vector, Integer> doors;
	private Level level;
	private Room[] adjacentRooms = new Room[4];
	private Wall[] walls = new Wall[4];
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
	
	public Room(OurView ov, Player p, Level level, int x, int y, boolean random){
		//Create a room with a random size, bettween max and min tiles
		this.ov = ov;
		this.player = p;
		this.level = level;
		this.x = 0;
		this.y = 0;
		this.width = (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2;
		this.height = (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2;
		doors = new HashMap<Vector, Integer>();

		bounds = new Rect(x, y, width, height);
		if(random)
			createRandom();
		else
			create();
	}
	
	public Room(OurView ov, Player player, Level level, int width, int height, int x, int y){
		this.ov = ov;
		this.player = player;
		this.level = level;
		this.height = height;
		this.width = width;
		doors = new HashMap<Vector, Integer>();
		bounds = new Rect(x, y, width, height);
	}
	
	//create all walls and items, and ghosts
	public void create() {
		emptyCells = new ArrayList<Vector>();
		for (int i = 0; i < width; i+=Tile.SIZE*2)
			for (int j = 0; j < height; j+=Tile.SIZE*2){
				emptyCells.add(new Vector(i+x*Tile.SIZE*2, j+y*Tile.SIZE*2));
				Log.d("hello","");
			}
		
		walls[UP] = new Wall(ov, level, x, y, width/(Tile.SIZE*2) - 1, 1);
		walls[DOWN] = new Wall(ov, level, x + Tile.SIZE*2, y + height - Tile.SIZE*2, width/(Tile.SIZE*2) - 1, 1);
		walls[LEFT] = new Wall(ov, level, x, y + Tile.SIZE*2, 1, height/(Tile.SIZE*2) - 1);//==============
		walls[RIGHT] = new Wall(ov, level, x + width - Tile.SIZE*2, y, 1, height/(Tile.SIZE*2) - 1); //============
		
		for(Wall w: walls)
			for(Tile t: w.getTiles()){
				boolean r = emptyCells.remove(t.getLocation());
				Log.d("removed cell", "<"+t.getLocation().x+","+t.getLocation().y+"> "+r);
				level.addToWorld(t);
			}
		
		for(Vector v : emptyCells){
			Log.d("created cell <"+v.x+","+v.y+">", "");
			level.addToWorld(new Floor(ov, v));
		}
				
		items = new ArrayList<Entity>();
		// instantiate objects
		// remove item cells from emptycells
		
		removeDoors();
	}
	
	public void createRandom(){
		create();
		
		int numDoors = (int) Math.round((Math.random()*2));
		for(int d = 0; d < numDoors; d++){
			int fails = 0;
			boolean created = false;
			while (fails < 3 && !created){
				int doorSide = (int) Math.round((Math.random()*3));
				if(!walls[doorSide].hasDoor){
					Vector doorLocation = (Vector) walls[doorSide].getTileLocations().
							toArray()[(int) Math.random()*walls[doorSide].getTiles().size()];
					walls[doorSide].removeTile(doorLocation);
					created = generateAdjacentRoom(doorSide, doorLocation);
					if (!created)
						fails++;
				} else
					fails++;
			}
		}
	}
	
	//makes a room if there isn't already a room in that location
	//generated room must be inside the world boundaries
	public boolean generateAdjacentRoom(int location, Vector doorLocation){
//		if(adjacentRooms[location]!= null) return false;
		if(level.getRooms().size() == level.MAX_ROOMS) return false;  //MOVE ME
		boolean hasIntersections = false;
		boolean created = false;
		
		int failed = 0;
		while(!hasIntersections && failed < 50){
			adjacentRooms[location] = new Room(ov, player, level, (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2, 
					(int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2, x, y);
			int rx, ry;
			
			switch(location){
			case UP:
				rx = 0;
				ry = -1 * adjacentRooms[location].width;
				break;
			case DOWN:
				rx = width - adjacentRooms[location].width;
				ry = height;
				break;
			case LEFT:
				rx = -1 * adjacentRooms[location].width;
				ry = height - adjacentRooms[location].height;
				break;
			case RIGHT:
				rx = width;
				ry = 0;
				break;
			default:
				rx = 0;
				ry = 0;
			}
			
			Rect newBounds = new Rect();
			newBounds.set(adjacentRooms[location].getBounds());
			newBounds.offset(rx, ry);
			adjacentRooms[location].setBounds(newBounds);
			
			hasIntersections = isIntersecting(adjacentRooms[location]);
			if(hasIntersections)
				failed++;
			else {
				created = true;
				
				int adjacent = 0;
				if(location == UP) adjacent = DOWN;
				if(location == DOWN) adjacent = UP;
				if(location == LEFT) adjacent = RIGHT;
				if(location == RIGHT) adjacent = LEFT;
				int dx, dy;
				
				switch(location){
				case UP:
					dx = 0;
					dy = -1;
					break;
				case DOWN:
					dx = 0;
					dy = 1;
					break;
				case LEFT:
					dx = -1;
					dy = 0;
					break;
				case RIGHT:
					dx = 1;
					dy = 0;
					break;
				default:
					dx = 0;
					dy = 0;
				}
				
				doorLocation.x += dx*Tile.SIZE*2;
				doorLocation.y += dy*Tile.SIZE*2;
				adjacentRooms[location].setAdjacentRoom(this, adjacent, doorLocation);
				adjacentRooms[location].createRandom();
				adjacentRooms[location].setLocation(new Vector(x + rx, y + ry));
			}
		}
		
		return created;
	}
	
	public void setAdjacentRoom(Room r, int adjacent, Vector doorLocation) {
		adjacentRooms[adjacent] = r;
		addDoor(doorLocation, adjacent);
	}
	
	public void addDoor(Vector doorLocation, int doorSide){
		doors.put(doorLocation, doorSide);
	}
	
	public void removeDoors(){
		for (Vector v : doors.keySet())
			walls[doors.get(v)].removeTile(v);
	}
	
	public ArrayList<Entity> getItems(){
		return items;
	}
	
	public Rect getBounds(){
		return bounds;
	}
	
	public void setBounds(Rect newBounds){
		bounds.set(newBounds);
	}
	
	public void setLocation(Vector v){
		x = v.x;
		y = v.y;
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
