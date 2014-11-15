package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("WrongCall")
public class Level {

	private int width, height;
	private ArrayList<Room> rooms;
	private ArrayList<Sprite> world;
	private int numGhosts;

	private Rect bounds;

	public Level(int width, int height) {
		rooms = new ArrayList<Room>();
		world = new ArrayList<Sprite>();
		this.width = width;
		this.height = height;

		bounds = new Rect(0, 0, width, height);
	}

	public void update(){
		for(Sprite e: world){
			e.update();
		}
	}

	public void onDraw(Canvas canvas){
		for(Room r : rooms)
			r.onDraw(canvas);
	}

	public Rect getBounds() {
		return bounds;
	}

	public ArrayList<Room> getRooms(){
		return rooms;
	}

	public boolean addRoom(Room r){
		return rooms.add(r);
	}

	public boolean addToWorld(Sprite s){
		return world.add(s);
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
