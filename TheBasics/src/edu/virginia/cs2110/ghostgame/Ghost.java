package edu.virginia.cs2110.ghostgame;


public class Ghost extends Entity {

	public Ghost(){
		super(null, null, 0);
	}
	
	public void handleCollision() {
		for(Entity e : world){
			if(e instanceof Wall){
				reAdjust();
			}
		}
	}

}
