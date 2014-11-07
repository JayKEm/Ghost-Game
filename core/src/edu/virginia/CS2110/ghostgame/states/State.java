package edu.virginia.CS2110.ghostgame.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.CS2110.ghostgame.main.GhostGame;

public abstract class State {

	protected GSM manager;
	protected OrthographicCamera cam;
	
	public State(GSM manager){
		this.manager = manager;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GhostGame.WIDTH, GhostGame.HEIGHT);
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
