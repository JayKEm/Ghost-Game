package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

@SuppressLint("WrongCall")
public class Wall {
	
//	A wall is basically a line of tiles
	ArrayList<Tile> segments;
	
	//width and height are in number of tiles
	public Wall(OurView ov, int x, int y, int width, int height) {
		segments = new ArrayList<Tile>();
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				segments.add(new Tile(ov, i * Tile.SIZE, i * Tile.SIZE));
	}
	
	public void onDraw(Canvas canvas) {
		for(Tile t : segments)
			t.onDraw(canvas);
	}
}
