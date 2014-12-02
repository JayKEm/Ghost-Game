package edu.virginia.cs2110.rlc4sv.thebasics.util;

import edu.virginia.cs2110.rlc4sv.thebasics.objects.Tile;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Vector {

	public int x;
	public int y;
	
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public double magnitude(){
		return Math.sqrt(x*x + y*y);
	}
	
	public boolean equals(Object o){
		if(o instanceof Vector){
			Vector v = (Vector) o;
			return v.x == x && v.y == y;
		}
		return false;
	}
	
	public static Vector clone(Vector c){
		return new Vector(c.x, c.y);
	}
	
	public String flatten(){
		return x+" "+y;
	}
	
	public static Vector unflatten(String s){
		Vector v = null;
		String[] data = s.split(" ");
		
		try{
			v = new Vector(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
		} catch (Exception e){
			
		}
		return v;
	}
	
	public String toString(){
		return "<"+(x/(Tile.SIZE*OurView.DEFAULT_ZOOM))+","+(y/(Tile.SIZE*OurView.DEFAULT_ZOOM))+">";
	}
}
