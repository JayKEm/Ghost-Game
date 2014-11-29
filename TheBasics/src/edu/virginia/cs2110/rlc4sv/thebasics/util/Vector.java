package edu.virginia.cs2110.rlc4sv.thebasics.util;

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
	
	public String toString(){
		return "<"+(x/64)+","+(y/64)+">";
	}
}
