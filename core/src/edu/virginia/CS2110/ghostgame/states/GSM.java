package edu.virginia.CS2110.ghostgame.states;

import java.util.Stack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Game state manager
// handles what state of the game is currently loaded
public class GSM {

	private Stack<State> states;
	
	public GSM(){
		states = new Stack<State>();
	}
	
	public void update(float dt) {
		states.peek().update(dt);
	}
	
	public void render(SpriteBatch sb){
		states.peek().render(sb);
	}
	
	public void add(State s){
		states.push(s);
	}
	
	public void pop(){
		states.pop();
	}
	
	public void set(State s){
		states.pop();
		states.push(s);
	}
}
