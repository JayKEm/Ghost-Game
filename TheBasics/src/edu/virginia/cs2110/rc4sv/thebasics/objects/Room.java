package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
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
	
	public Room(OurView ov, Player p, Level level){
		//Create a room with a random size, bettween max and min tiles
		this.ov = ov;
		this.player = p;
		this.level = level;
		this.x = 0;
		this.y = 0;
		this.width = (MAX_TILES-MIN_TILES)*Tile.SIZE*2;
		this.height = (MAX_TILES-MIN_TILES)*Tile.SIZE*2;
		doors = new HashMap<Vector, Integer>();
		id = x/64+":"+y/64+":"+width/64+":"+height/64;

		bounds = new Rect(x+ov.offsetX, y+ov.offsetY, x+ov.offsetX + width, y+ov.offsetY + height);
//		createCenter();
		createRandom();
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
	
	public void update(){
		bounds = new Rect(x+ov.offsetX, y+ov.offsetY, x+ov.offsetX + width, y+ov.offsetY + height);
	}
	
	public void build() {
		//create cell grid
		emptyCells = new ArrayList<Vector>();
		for (int i = 0; i < width; i+=Tile.SIZE*2)
			for (int j = 0; j < height; j+=Tile.SIZE*2)
				emptyCells.add(new Vector(i+x, j+y));
		
		if(walls[0]==null)
			createWalls();
		
		//remove unwanted doors
		for(int w = 0; w < 4; w++)
			if(adjacentRooms[w]==null)
				for(Vector v : doors.keySet()){
					Log.d("removing door on wall: "+getWall(w), id+": "+v+" "+getWall(doors.get(v)));
					if(doors.get(v) == w)
						doors.remove(v);
					}
		
		//remove doors from walls
		buildDoors();
		for(Wall w: walls){
			w.create();
			for(Tile t: w.getTiles()){
				emptyCells.remove(t.getLocation());
				level.addToWorld(t);
			}
		}
		
		for(Vector v : emptyCells)
			level.addToWorld(new Floor(ov, v));
			
		//spawn items
		items = new ArrayList<Entity>();
		spawnChest();
		spawnCoins(ov.bronzeCoin, 5);
		spawnCoins(ov.silverCoin, 4, -3);
		spawnCoins(ov.goldCoin, 4);
		spawnWeapon();
	}
	
	private void createWalls() {
		walls[UP] = new Wall(ov, level, x, y, width/(Tile.SIZE*2) - 1, 1);
		walls[DOWN] = new Wall(ov, level, x + Tile.SIZE*2, y + height - Tile.SIZE*2, width/(Tile.SIZE*2) - 1, 1);
		walls[LEFT] = new Wall(ov, level, x, y + Tile.SIZE*2, 1, height/(Tile.SIZE*2) - 1);
		walls[RIGHT] = new Wall(ov, level, x + width - Tile.SIZE*2, y, 1, height/(Tile.SIZE*2) - 1);
	}
	
	public void createCenter(){
		createWalls();
		
		for(int doorSide = 0; doorSide < 4; doorSide++){
			Vector doorLocation = (Vector) walls[doorSide].getTileLocations().toArray()
					[walls[doorSide].getTileLocations().size()/2];
			walls[doorSide].removeTile(doorLocation);
			addDoor(doorLocation, doorSide);
			generateAdjacentRoom(doorSide, doorLocation);
		}
		
		for(int r = 0; r < 4; r++)
			if (adjacentRooms[r]==null){
				Vector doorLocation = (Vector) walls[r].getTileLocations().toArray()
						[walls[r].getTileLocations().size()/2];
				removeDoor(doorLocation, r);
			}
	}
	
	public void createRandom(){
		int numDoors = (int) Math.round((Math.random()*2));
		if(level.getRooms().size() >= level.MAX_ROOMS -1) return;

		if(walls[0]==null)
			createWalls();

		for(int d = 0; d < numDoors; d++){
			int fails = 0, doorSide = -1;
			boolean created = false;
			
			while (fails < 4 && !created && level.getRooms().size() <= level.MAX_ROOMS-1){
				if(doorSide==-1) doorSide = (int)(Math.random()*3);
				else
					if(doorSide < 3) doorSide++;
					else doorSide = 0;
					
				if(!walls[doorSide].hasDoor){
					int max = walls[doorSide].getTileLocations().size() - 1;
					int i = (int) ((Math.random()*(max - 1) + 1));
					Vector doorLocation = (Vector) walls[doorSide].getTileLocations().toArray()[i];
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
		if(adjacentRooms[location]!= null) return false; 
		if(level.getRooms().size() >= level.MAX_ROOMS -1) return false;
		boolean hasIntersections = true;
		boolean created = false;
		
		int failed = 0;
		while(hasIntersections && failed < 50){
			adjacentRooms[location] = new Room(ov, player, level, x, y);
			int rx=0, ry=0;
			
			switch(location){
			case UP:
				rx = 0;
				ry = -1 * adjacentRooms[location].height;
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
				
				addDoor(doorLocation, location);
				doorLocation.x += dx*Tile.SIZE*2;
				doorLocation.y += dy*Tile.SIZE*2;
				adjacentRooms[location].setAdjacentRoom(this, adjacent, doorLocation);
				adjacentRooms[location].setLocation(new Vector(x + rx, y + ry));
				level.addRoom(adjacentRooms[location]);
				if (level.getRooms().size() <= level.MAX_ROOMS-1)
					adjacentRooms[location].createRandom();
			}
		}
		
		return created;
	}
	
	//spawn chest near a wall
	//<30% chance a chest will actually spawn
	public void spawnChest(){
		double chest = Math.random()*100;
		if(chest<30){
			int wall = (int) (Math.random()*3);
			int dx =0, dy =0;
			int max = walls[wall].getTiles().size() - 1;
			int i = (int) ((Math.random()*(max - 1) + 1));
			
			Vector location = Vector.clone((Vector) ((Entity) walls[wall].getTiles().toArray()[i]).getLocation());
			
			switch(wall){
			case UP:
				dx = 0; dy = 1; 
				break;
			case DOWN:
				dx = 0; dy = -1; 
				break;
			case LEFT:
				dx = 1; dy = 0; 
				break;
			case RIGHT:
				dx = -1; dy = 0; 
				break;
			}
			
			location.x += dx*Tile.SIZE*2;
			location.y += dy*Tile.SIZE*2;
			
			if(emptyCells.contains(location)){
				emptyCells.remove(location);
				items.add(new Chest(ov, level, player, location.x, location.y));
			}	
		}	
	}
	
	public void spawnCoins(Bitmap image, int max){
		spawnCoins(image, max, 0);
	}
	
	public void spawnCoins(Bitmap image, int max, int min){
		int numCoins = (int) (Math.random()*(max-min)) + min;
		for(int i = 0; i < numCoins; i++){
			Coin c = null;
			try{
				Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

				c = new Coin(ov, image, cell.x, cell.y);
				emptyCells.remove(cell);
				items.add(c);
			} catch(Exception e){
				Log.i("could not spawn Coin", "cells: "+emptyCells.size());
			}
		}
	}
	
	public void spawnWeapon(){
		double weapon = Math.random()*100;
		if(weapon <20){
			Weapon w = null;
			try{
				Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

				w = new Weapon(ov, ov.weaponSprites, cell.x, cell.y);
				emptyCells.remove(cell);
				items.add(w);
			} catch (Exception e){
				Log.i("could not spawn Coin", "cells: "+emptyCells.size());
			}
		}
	}
	
	public void setAdjacentRoom(Room r, int adjacent, Vector doorLocation) {
		adjacentRooms[adjacent] = r;
		addDoor(doorLocation, adjacent);
	}
	
	public void addDoor(Vector location, int wall){
		doors.put(location, wall);
	}
	
	public void buildDoors(){
		for (Vector v : doors.keySet()){
			walls[doors.get(v)].removeTile(v);
		}
	}
	
	public void removeDoor(Vector location, int wall){
		walls[wall].addTile(location);
		doors.remove(location);
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
		id = x/64+":"+y/64+":"+width/64+":"+height/64;
	}
	
	public String getWall(int w){
		String wall= null;

		switch(w){
		case UP: wall = "UP";break;
		case DOWN: wall = "DOWN";break;
		case LEFT: wall = "LEFT";break;
		case RIGHT: wall = "RIGHT";break;
		}
		
		return wall;
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
	
	public void createItems(){
		for (Entity e : items){
			level.addToWorld(e);
		}
	}
}
