package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
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
	private boolean warn;
	private int id;

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

	public void updateRender(Canvas canvas){
		addToWorld();

		warn = false;
		for(Entity f : world)
			if(f instanceof Floor){
				f.render(canvas);
			}
		for(Entity s : world)
			if (!(s instanceof Floor))
				if(s instanceof Player){
					if(!((Player)s).isDead)
						s.render(canvas);
				} else s.render(canvas);

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.MAGENTA);

		for(Room r: rooms){
			r.update();
		}

		removeFromWorld();
	}

	public ArrayList<Room> getRooms(){
		return rooms;
	}

	public boolean addRoom(Room r){
		if(rooms.size()==MAX_ROOMS)
			return false;
		return rooms.add(r);
	}

	public void generate(OurView ov){
		determineID();

		if (id >=1){
			addRoom(new Room(ov, player, this));

			int fails = 0;
			while(rooms.size()<MAX_ROOMS&& fails <300){
				rooms.get(0).createRandom();
				fails++;
			}

			for(Room r : rooms){
				r.build();
				emptyCells.addAll(r.getEmptyCells());
			}

			for(Room r: rooms){
				r.createItems();
			}
			spawnGhosts(ov.getGhostSprites());
			saveLevel();
		} else{
			loadFromFile("level1.txt");
		}

	}

	public boolean addToWorld(Entity e){
		e.setWorld(world);
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
				//				Toast toast = Toast.makeText(ov.getContext(), "Game over! You Win!", Toast.LENGTH_LONG);
				//				toast.show();
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

			for(Entity e : world)
				e.setPlayer(player);

			return player;
		} catch(Exception e){
			player = new Player(ov, playerSprites, size.x/2,size.y/2, 64,64);
			addToWorld(player);

			for(Entity s : world)
				s.setPlayer(player);

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
			g.setPlayer(player);
			world.add(g);
			emptyCells.remove(cell);

			return g;
		} catch(Exception e){
			Log.w("could not spawn Ghost", "cells: "+emptyCells.size());
			return null;
		}
	}

	public Fireball spawnFireball(Bitmap image) {
		Fireball g = new Fireball(ov, image, player.location.x-ov.offsetX, player.location.y-ov.offsetY);
		g.setVelocity(Vector.clone(player.velocity));
		addToWorld(g);

		return g;
	}

	public Icebolt spawnIcebolt(Bitmap image) {
		Icebolt g = new Icebolt(ov, image, player.location.x-ov.offsetX, player.location.y-ov.offsetY);
		g.setVelocity(Vector.clone(player.velocity));
		addToWorld(g);

		return g;
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

	public void spawnCoins(int max, Bitmap image){
		int numCoins = (int) (Math.random()*max);
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
			Log.w("could not spawn Weapon", "cells: "+emptyCells.size());
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
			Log.w("could not spawn Coin", "cells: "+emptyCells.size());
			return c;
		}
	}

	//load a predefinied level
	@SuppressWarnings("deprecation")
	public boolean loadFromFile(String filename){
		//TODO complete file parsing
		
		if(!ov.isExternalStorageReadable())
			return false;
		
		FileInputStream fis = null;
		emptyCells = new ArrayList<Vector>();
		world = new ArrayList<Entity>();

		try {
			fis = ov.getContext().openFileInput(filename);
			DataInputStream dataIO = new DataInputStream(fis);
			String[] data, lines;
			String file = "";

			while ((file = dataIO.readLine()) != null) {
				lines = file.split(":");

				for(String line: lines){
					data = line.split(";");
					Rect bounds = Rect.unflattenFromString(data[1]);
					Vector location = Vector.unflatten(data[2]);

					if(data[0].equals("room")){
						Room r;
						Vector dimensions = Vector.unflatten(data[3]);
						String[] doors = data[4].split("/");

						r = new Room(ov, player, this, location.x, location.y, dimensions.x, dimensions.y);
						r.setBounds(bounds);
						for(String s : doors){
							String[] tmp = s.split(" ");
							r.addDoor(Vector.unflatten(tmp[0]), Integer.parseInt(tmp[1]));
						}

						r.createCells();
						r.createWalls();
						r.buildDoors();
						r.buildWalls();
						emptyCells.addAll(r.getEmptyCells());
						rooms.add(r);
					} if (data[0].equals("ghost")){
						Ghost g = new Ghost(ov, ov.getGhostSprites(), location.x, location.y);
						g.setDirection(Sprite.determineDirection(Vector.unflatten(data[3])));
						world.add(g);
					} if(data[0].equals("coin")){
						int value = Integer.parseInt(data[1]);
						Bitmap image = null;

						switch(value){
						case Coin.BRONZE_VALUE:
							image = ov.bronzeCoin; 
							break;
						case Coin.SILVER_VALUE:
							image = ov.silverCoin; 
							break;
						case Coin.GOLD_VALUE:
							image = ov.goldCoin; 
							break;
						}

						world.add(new Coin(ov, image, location.x, location.y));
					} if(data[0].equals("chest")){
						ArrayList<Coin> coins = new ArrayList<Coin>();
						String[] tmp = data[3].split(" ");

						for(String s : tmp){
							int value = Integer.parseInt(s);
							Bitmap image = null;

							switch(value){
							case Coin.BRONZE_VALUE:
								image = ov.bronzeCoin; 
								break;
							case Coin.SILVER_VALUE:
								image = ov.silverCoin; 
								break;
							case Coin.GOLD_VALUE:
								image = ov.goldCoin; 
								break;
							}

							coins.add(new Coin(ov, image, location.x, location.y));
						}

						world.add(new Chest(ov, this, player, location.x, location.y, coins));
					} if(data[0].equals("weapon")){
						world.add(new Weapon(ov, ov.weaponSprites, location.x, location.y));
					} if(data[0].equals("tombstone")){
						world.add(new Tombstone(ov, this, ov.tombstone, location.x, location.y));
					}
					Log.e("reading",""+file);
				}
			}

			dataIO.close();
			fis.close();

			logWorldContents();
			return true;
		}
		catch  (IOException e) { 
			Log.e("failed to load level", e.toString());
			return false; 
		}
	}

	public boolean saveLevel(){
		//TODO
		String line = "", filename = "level" + id + ".txt";
		ArrayList<String> data = new ArrayList<String>();
		
		if(!ov.isExternalStorageWritable())
			return false;

		try {
			FileOutputStream writer = ov.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
			for(Room r : rooms){
				line = "Room;";
				line += r.getBounds().flattenToString()+";";
				line += (new Vector(r.x,r.y)).flatten()+";";
				line += (new Vector(r.width, r.height)).flatten()+";";

				for(Vector v : r.getDoors().keySet()){
					String door = ""+v.flatten();
					door += " "+r.getDoors().get(v) +"/";
					line += door;
				}

				line = line.substring(0, line.length()-1);
				data.add(line);
			}

			for(Entity e : world){
				line = e.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) +";";
				line += e.getLocation().flatten()+";";

				if(e instanceof Ghost){
					line+=((Ghost) e).velocity.flatten();
				} if(e instanceof Coin){
					line += ((Coin) e).getValue();
				} if (e instanceof Chest){
					for(Coin c : ((Chest) e).getCoins())
						line += c.getValue() +" ";
				} if (e instanceof Weapon){

				} if (e instanceof Tombstone){

				}if (!(e instanceof Tile) && !(e instanceof Player)){
					if(line.endsWith(";"))
						line = line.substring(0, line.length() - 1);
					line += ":";
					data.add(line);
				}
			}

			for(String s : data)
				writer.write(s.getBytes());

			writer.close();
		} catch (Exception e) {
			Log.e("failed", e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	private void determineID(){
		id = 1;
		boolean failed = false;
		FileInputStream fis = null;

		while(!failed){
			try{
				String filename = "level"+id+".txt";
				fis = ov.getContext().openFileInput(filename);
				fis.close();
				id++;
			} catch (Exception e){
				Log.e("determining id", "level"+id+".txt not found");
				failed = true;
			}
		}
	}

	public int getNumSavedLevels(){
		int id = 1;
		boolean failed = false;
		FileInputStream fis = null;

		while(!failed){
			try{
				String filename = "level"+id+".txt";
				fis = ov.getContext().openFileInput(filename);
				fis.close();
				id++;
			} catch (Exception e){
				Log.e("determining id", "level"+id+".txt not found");
				failed = true;
			}
		}
		return id;
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
			w+=e.id+",";
		Log.e("world contents",w);
	}

	public boolean getWarn(){
		return warn;
	}

	public int getId(){
		return id;
	}

	public void setWarn(boolean warn){
		this.warn = warn;
	}
}
