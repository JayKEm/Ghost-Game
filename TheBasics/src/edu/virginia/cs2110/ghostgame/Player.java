package edu.virginia.cs2110.ghostgame;


public class Player extends Entity {

	public int score;
	private int health;
	
	public static int MAX_HEALTH = 10;
	public static final int MIN_HEALTH = 0;
	
	public Player(){
		super(null, null, 0);
	}
	
	public void handleCollision() {
		for (Entity e : world)
			if(isColliding(e)){
				if(e instanceof Ghost)
//					if(time >= damageDelay)
						damage();
				if(e instanceof Wall)
					reAdjust();
			}
	}
	
	public void damage(){
		if(health > MIN_HEALTH)
			health--;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

}
