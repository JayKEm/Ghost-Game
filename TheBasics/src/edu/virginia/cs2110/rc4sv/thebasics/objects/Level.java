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

//	private int width, height;
	private ArrayList<Room> rooms;
	private ArrayList<Entity> world, toRemove;
	private ArrayList<int[]> emptyCells;
	private Player p;

	public int MAX_ROOMS, NUM_GHOSTS;
	
	public Level(){
		
	}

	public Level(int maxRooms, int numGhosts) {
		rooms = new ArrayList<Room>();
		world = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
		emptyCells = new ArrayList<int[]>();
		this.MAX_ROOMS = maxRooms;
		this.NUM_GHOSTS = numGhosts;
	}

	public void onDraw(Canvas canvas){
		for(Room r : rooms)
			r.onDraw(canvas);
		for(Entity s : world){
			s.onDraw(canvas);
		}
		
		removeFromWorld();
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
	
	public boolean removeFromWorld(Entity e){
		return toRemove.add(e);
	}
	
	public boolean removeFromWorld(){
		boolean removed = world.removeAll(toRemove);
		toRemove = new ArrayList<Entity>();
		return removed;
	}
	
	public ArrayList<Entity> getWorld(){
		return world;
	}

	public Player spawnPlayer(OurView ourView, Bitmap playerSprites) {
//		Point size = new Point();
//		ourView.getDisplay().getSize(size);
		Player player = new Player(ourView, playerSprites, 300,500);
		addToWorld(player);
		return player;
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
		try{
			for (int i = 0; i < NUM_GHOSTS; i++)
				spawnGhost(ov, image);
		} catch(Exception e){
			Log.d("emptycells", ""+emptyCells.size());
		}
	}
	
	//spawn coins accros level
	public void spawnCoins(OurView ov, Bitmap image){
		int numCoins = (int)(Math.random()*(10 - 1) + 1);
		
		try {
			for (int i = 0; i < numCoins; i++)
				spawnCoin(ov, image);
		} catch(Exception e){
			Log.d("emptycells", ""+emptyCells.size());
		}
	}
	
	public void spawnWeapons(OurView ov, Bitmap image){
		int numWeapons = (int)(Math.random()*(10 - 1) + 1);
		for (int i = 0; i < numWeapons; i++)
			spawnWeapon(ov, image);
	}
	


	public Weapon spawnWeapon(OurView ov, Bitmap weaponsprite){
		Weapon c = null;
		int[] cell = (int[]) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];
		
		c = new Weapon(ov, weaponsprite, cell[0], cell[1]);
		world.add(c);
		emptyCells.remove(cell);
		
		return c;
	}
	
	public Coin spawnCoin(OurView ov, Bitmap coinSprites){
		Coin c = null;
		int[] cell = (int[]) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];
		
		c = new Coin(ov, coinSprites, cell[0], cell[1]);
		world.add(c);
		emptyCells.remove(cell);
		
		return c;
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
	
	public Player getPlayer() {
		return this.p;
	}
}
