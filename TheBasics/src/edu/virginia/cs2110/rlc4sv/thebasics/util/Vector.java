package edu.virginia.cs2110.rlc4sv.thebasics.util;

public class Vector {

	public int x;
	public int y;
	
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o){
		if(o instanceof Vector){
			Vector v = (Vector) o;
			return v.x == x && v.y == y;
		}
		return false;
	}
	
	public String toString(){
		return "<"+x+","+y+">";
	}
}
