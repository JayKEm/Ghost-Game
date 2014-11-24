package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;
import edu.virginia.cs2110.rlc4sv.thebasics.util.Vector;

@SuppressLint("WrongCall")
public class Level {

	//	private int width, height;
	private ArrayList<Room> rooms;
	private ArrayList<Entity> world, toRemove, toAdd;
	private ArrayList<Vector> emptyCells;
	private Player player;
	private OurView ov;

	public int MAX_ROOMS, MAX_GHOSTS;
	public int ghosts;

	public Level(){

	}

	public Level(OurView ov, int maxRooms, int numGhosts) {
		rooms = new ArrayList<Room>();
		world = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		emptyCells = new ArrayList<Vector>();
		this.ov = ov;
		this.MAX_ROOMS = maxRooms;
		this.MAX_GHOSTS = numGhosts;

	}

	public void render(Canvas canvas){

		if(this.player.hasWeapon() && (System.currentTimeMillis() - this.player.getWeaponTimer() > 5000)) {
			this.player.loseWeapon();
		}
		
		if(System.currentTimeMillis() - this.player.getDamageTimer() > 5000) {
			this.player.setCanGetHurt(true);
		}
		addToWorld();
		

		for(Entity f : world)
			if(f instanceof Floor)
				f.render(canvas);
		for(Entity s : world)
			if (!(s instanceof Floor))
				s.render(canvas);

		removeFromWorld();
	}

	public ArrayList<Room> getRooms(){
		return rooms;
	}

	public boolean addRoom(Room r){
		if(rooms.size()==MAX_ROOMS)
			return false;
		boolean added = rooms.add(r);

		//		for (Vector cell : emptyCells)
		//			Log.d("cell location","<"+cell.x+","+cell.y+">");

		return added;
	}

	public void generate(OurView ov){
		Room center = new Room(ov, player, this, 0, 0, true); //debug room
		addRoom(center);
		for(Room r : rooms){
			r.build();
			emptyCells.addAll(r.getEmptyCells());
		}

		//		logWorldContents();
	}

	public boolean addToWorld(Entity e){
		return toAdd.add(e);
	}
	
	public boolean addToWorld(){
		boolean added = world.addAll(toAdd);
		toAdd = new ArrayList<Entity>();
		return added;
	}

	public boolean removeFromWorld(Entity e){
		if (e instanceof Ghost){
			ghosts--;
			if (ghosts<=0){
				Toast toast = Toast.makeText(ov.getContext(), "Game over! You Win!", Toast.LENGTH_LONG);
				toast.show();
			}
		}
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

	public Player spawnPlayer(Bitmap playerSprites) {
		Point size = new Point();
		ov.getDisplay().getSize(size);

		try{
			Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];
			player = new Player(ov, playerSprites, size.x/2,size.y/2, cell.x, cell.y);
			player.setWorld(world);
			emptyCells.remove(cell);
			addToWorld(player);
			return player;
		} catch(Exception e){
			player = new Player(ov, playerSprites, size.x/2,size.y/2, 64,64);
			addToWorld(player);
			return player;
		} 
	}
	
	

	//spawn a ghost in a random empty cell
	public Ghost spawnGhost(Bitmap image) {
		Ghost g = null;
		try{
			Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

			g = new Ghost(ov, image, cell.x, cell.y);
			g.setWorld(world);
			world.add(g);
			emptyCells.remove(cell);

			return g;
		} catch(Exception e){
			Log.d("could not spawn Ghost", "cells: "+emptyCells.size());
			return null;
		}
	}
	
	public Fireball spawnFireball(OurView ov, Bitmap image) {
		Fireball g = null;
		int x = player.velocity.x;
		int y = player.velocity.y;
		try{
			g = new Fireball(ov, image, player.v.x, player.v.y);
			g.setVelocity(x,y);
			g.setWorld(world);
			addToWorld(g);

			return g;
		} catch(Exception e){
			Log.d("could not spawn Fireball", "cells: "+emptyCells.size());
			return null;
		}
	}

	// spawn ghosts across level 
	// maximum number of ghosts actually spawned is limited by 
	// the number of empty cells in level
	//
	// called only during init
	public void spawnGhosts(Bitmap image){
		for (int i = 0; i < MAX_GHOSTS; i++)
			spawnGhost(image);
		countGhosts();
	}

	//spawn random number of coins coins accros level
	public void spawnCoins(Bitmap image){
		spawnCoins((int)(Math.random()*(10 - 1) + 1), image);
	}

	public void spawnCoins(int numCoins, Bitmap image){
		for (int i = 0; i < numCoins; i++)
			spawnCoin(image);
	}

	public void spawnWeapons(Bitmap image){
		int numWeapons = /*(int)(Math.random()*(10 - 1) + 1)*/ 1;
		for (int i = 0; i < numWeapons; i++)
			spawnWeapon(image);
	}

	public Weapon spawnWeapon(Bitmap weaponsprite){
		Weapon c = null;
		try{
			Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

			c = new Weapon(ov, weaponsprite, cell.x, cell.y);
			world.add(c);
			emptyCells.remove(cell);

			return c;
		} catch(Exception e){
			Log.d("could not spawn Weapon", "cells: "+emptyCells.size());
			return null;
		}
	}

	public Coin spawnCoin(Bitmap coinSprites){
		Coin c = null;
		try{
			Vector cell = (Vector) emptyCells.toArray()[(int) (Math.random()*emptyCells.size())];

			c = new Coin(ov, coinSprites, cell.x, cell.y);
			world.add(c);
			emptyCells.remove(cell);

			return c;
		} catch(Exception e){
			Log.d("could not spawn Coin", "cells: "+emptyCells.size());
			return null;
		}
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
		return this.player;
	}
	
	public void countGhosts(){
		ghosts = 0;
		for(Entity e : world)
			if(e instanceof Ghost)
				ghosts++;
	}

	public void logWorldContents(){
		String w = "";
		for(Entity e : world)
			//			w+=e.id+",";
			//			if(e instanceof Tile)
			w+=e.id+",";
		Log.d("world contents",w);
	}
}
