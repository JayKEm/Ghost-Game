package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;
import java.util.HashSet;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

@SuppressLint("WrongCall")
public class Level {

	private int width, height;
	private ArrayList<Room> rooms;
	private ArrayList<Entity> world;
	private HashSet<int[]> emptyCells;

	public int MAX_ROOMS, NUM_GHOSTS;
	
	public Level(){
		
	}

	public Level(int maxRooms, int numGhosts) {
		rooms = new ArrayList<Room>();
		world = new ArrayList<Entity>();
		emptyCells = new HashSet<int[]>();
		this.MAX_ROOMS = maxRooms;
		this.NUM_GHOSTS = numGhosts;
	}

	public void onDraw(Canvas canvas){
		for(Room r : rooms)
			r.onDraw(canvas);
		for(Entity s : world){
			s.onDraw(canvas);
		}
	}

	public ArrayList<Room> getRooms(){
		return rooms;
	}

	public boolean addRoom(Room r){
		boolean added = rooms.add(r);
		emptyCells.addAll(r.getEmptyCells());
		return added;
	}

	public boolean addToWorld(Entity e){
		return world.add(e);
	}
	
	public ArrayList<Entity> getWorld(){
		return world;
	}

	public Player spawnPlayer(OurView ourView, Bitmap playerSprites) {
		int[] cell = (int[]) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

		emptyCells.remove(cell);
		return new Player(ourView, playerSprites, cell[0], cell[1]);
	}
	
	//spawn a ghost in a random empty cell
	public Ghost spawnGhost(OurView ov, Bitmap image) {
		Ghost g = null;
		int[] cell = (int[]) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];
		
		g = new Ghost(ov, image, cell[0], cell[1]);
		g.setWorld(world);
		world.add(g);
		emptyCells.remove(cell);
		
		return g;
	}
	
	// spawn ghosts across level 
	// maximum number of ghosts actually spawned is limited by 
	// the number of empty cells in level
	//
	// called only during init
	public void spawnGhosts(OurView ov, Bitmap image){
		for (int i = 0; i < NUM_GHOSTS; i++)
			spawnGhost(ov, image);
	}

	//load a predefinied level
	public boolean loadFromFile(String filename){
		int numRooms = 0;

		try {
			for(int i = 0; i < numRooms; i++){
				rooms.add(new Room());
			}
		} catch (Exception e){
			return false;
		}
		return false;
	}
}
