package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

@SuppressLint("WrongCall")
public class Room {

	private OurView ov;
	private Player player;
	private ArrayList<Entity> items;
	private Level level;
	private Room[] adjacentRooms = new Room[4];
	private Wall[] walls = new Wall[4];
	private int[][] doorLocations;
	public int x, y, width, height;
	
	private Rect bounds;
	
	//locations for adjacent rooms in room array
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public Room(){
		
	}
	
	public Room(OurView ov, Player p, Level level){
		this(ov, p, level, 0, 0);
	}
	
	public Room(OurView ov, Player player, Level level, int x, int y){
		this.ov = ov;
		this.player = player;
		this.level = level;
		this.x = x;
		this.y = y;
		
		//Create a room with a random size, from 10 to 20 tiles
		width = (int) (Math.random()*20 - Math.random()*10) * Tile.SIZE;
		height = (int) (Math.random()*20 - Math.random()*10) * Tile.SIZE;
		bounds = new Rect(x, y, width, height);
	}
	
	public void onDraw(Canvas canvas) {
		for(Entity item : items)
			item.onDraw(canvas);
		for(Wall w : walls)
			w.onDraw(canvas);
	}
	
	//makes a room if there isn't already a room in that location
	//generated room must be inside the world boundaries
	public void generateAdjacentRoom(int location){
		if(adjacentRooms[location]!= null) return;
		
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
		walls[UP] = new Wall(ov, x, y, width/Tile.SIZE - 1, 1);
		walls[DOWN] = new Wall(ov, x + Tile.SIZE, y + height - Tile.SIZE, width/Tile.SIZE - 1, 1);
		walls[LEFT] = new Wall(ov, x + width - Tile.SIZE, y, 1, height/Tile.SIZE - 1);
		walls[RIGHT] = new Wall(ov, x, y + Tile.SIZE, 1, height/Tile.SIZE - 1);
		
		items = new ArrayList<Entity>();
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
	
	public boolean isIntersecting(Room room){
		if(Rect.intersects(room.getBounds(), level.getBounds()))
			return true;
		for(Room r : level.getRooms())
			if(Rect.intersects(room.getBounds(), r.getBounds()))
				return true;
		return false;
	}
}