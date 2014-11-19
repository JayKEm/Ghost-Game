package edu.virginia.cs2110.rc4sv.thebasics.objects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

@SuppressLint("WrongCall")
public class Wall extends Entity{
	
//	A wall is basically a line of tiles
	private ArrayList<Tile> segments;
	
	//width and height are in number of tiles
	public Wall(OurView ov, int x, int y, int width, int height) {
		super();
		this.width = width*Tile.SIZE;
		this.width = width*Tile.SIZE;
		bounds = new Rect(x, y, x + this.width, y + this.height);
		segments = new ArrayList<Tile>();
		id="wall";
		
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				segments.add(new Tile(ov, x + i * (Tile.SIZE*2), y + j * (Tile.SIZE*2)));
	}
	
	public void onDraw(Canvas canvas) {
		for(Tile t : segments)
			t.onDraw(canvas);
//		canvas.drawRect(bounds, new Paint(Color.RED));
	}
	
	public ArrayList<Tile> getTiles() {
		return segments;
	}
}
