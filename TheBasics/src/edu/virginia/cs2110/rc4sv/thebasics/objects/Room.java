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
	
	public String id;
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
		id = x+":"+y+":"+width+":"+height;
		
		bounds = new Rect(x, y, width, height);
		if(random)
			createRandom();
		else
			build();
	}
	
	public Room(OurView ov, Player player, Level level, int x, int y){
		this.ov = ov;
		this.player = player;
		this.level = level;
		this.height = (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2;
		this.width = (int) (Math.random()*(MAX_TILES - MIN_TILES) + MIN_TILES) * Tile.SIZE*2;
		doors = new HashMap<Vector, Integer>();
		bounds = new Rect(x, y, width, height);
	}
	
	//create all walls and items, and ghosts
	public void build() {
		emptyCells = new ArrayList<Vector>();
		for (int i = 0; i < width; i+=Tile.SIZE*2)
			for (int j = 0; j < height; j+=Tile.SIZE*2){
				emptyCells.add(new Vector(i+x*Tile.SIZE*2, j+y*Tile.SIZE*2));
//				Log.d("hello",id);
			}
		
		if(walls[0]==null)
			createWalls();
		
		removeDoors();
		for(Wall w: walls){
			w.create();
			Log.d(id,""+w.getTiles().size());
			for(Tile t: w.getTiles()){
				emptyCells.remove(t.getLocation());
				level.addToWorld(t);
			}
		}
		
		for(Vector v : emptyCells){
//			Log.d("floor", v+"");
			level.addToWorld(new Floor(ov, v));
		}
				
		items = new ArrayList<Entity>();
		// instantiate objects
		// remove item cells from emptycells
	}
	
	private void createWalls() {
		walls[UP] = new Wall(ov, level, x, y, width/(Tile.SIZE*2) - 1, 1);
		walls[DOWN] = new Wall(ov, level, x + Tile.SIZE*2, y + height - Tile.SIZE*2, width/(Tile.SIZE*2) - 1, 1);
		walls[LEFT] = new Wall(ov, level, x, y + Tile.SIZE*2, 1, height/(Tile.SIZE*2) - 1);
		walls[RIGHT] = new Wall(ov, level, x + width - Tile.SIZE*2, y, 1, height/(Tile.SIZE*2) - 1);
	}
	
	public void createRandom(){
		int numDoors = (int) Math.round((Math.random()*2));
		createWalls();

		if(level.getRooms().size() == level.MAX_ROOMS) return;
		for(int d = 0; d < numDoors; d++){
			int fails = 0;
			boolean created = false;
			while (fails < 3 && !created){
				int doorSide = fails;
				if(!walls[doorSide].hasDoor){
					int max = walls[doorSide].getTileLocations().size() - 1;
					int i = (int) ((Math.random()*(max - 1) + 1));
					Vector doorLocation = (Vector) walls[doorSide].getTileLocations().toArray()[i];
					Log.d("index: "+i+"  max: "+max,"location: "+doorLocation);
					walls[doorSide].removeTile(doorLocation); 
					created = generateAdjacentRoom(doorSide, doorLocation);
					if (!created)
						fails++;
					else
						addDoor(doorLocation, doorSide);
				} else
					fails++;
			}
		}
	}

	//makes a room if there isn't already a room in that location
	//generated room must be inside the world boundaries
	public boolean generateAdjacentRoom(int location, Vector doorLocation){
		if(adjacentRooms[location]!= null) return false; 
		if(level.getRooms().size() == level.MAX_ROOMS) return false;
		boolean hasIntersections = false;
		boolean created = false;
		
		int failed = 0;
		while(!hasIntersections && failed < 50){
			adjacentRooms[location] = new Room(ov, player, level, x, y);
			int rx=0, ry=0;
			
			switch(location){
			case UP:
				rx = 0;
				ry = -1 * adjacentRooms[location].height;
				break;
			case DOWN:
				rx = width - adjacentRooms[location].width;
				ry = height+Tile.SIZE*2;
				break;
			case LEFT:
				rx = -1 * adjacentRooms[location].width;
				ry = height - adjacentRooms[location].height;
				break;
			case RIGHT:
				rx = width;
				ry = 0;
				break;
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
				int dx=0, dy=0; //door offset
				
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
				}
				
				doorLocation.x += dx*Tile.SIZE*2;
				doorLocation.y += dy*Tile.SIZE*2;
				Log.d("doorLocation", ""+doorLocation);
				adjacentRooms[location].setAdjacentRoom(this, adjacent, doorLocation);
				adjacentRooms[location].setLocation(new Vector(x + rx, y + ry));
				level.addRoom(adjacentRooms[location]);
				Log.d("created",adjacentRooms[location].toString());
//				adjacentRooms[location].createRandom();
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
		for (Vector v : doors.keySet()){
			walls[doors.get(v)].removeTile(v);
		}
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
		id = x+":"+y+":"+width+":"+height;
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
	
	public String toString(){
		return id;
	}
}
