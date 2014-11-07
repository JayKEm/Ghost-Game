package edu.virginia.CS2110.ghostgame.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.CS2110.ghostgame.states.GSM;
import edu.virginia.CS2110.ghostgame.states.Title;

public class GhostGame extends ApplicationAdapter {
	public static final String title = "Ghost Game";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	
	private GSM manager;
	private SpriteBatch sb;
	
	public void create () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		manager = new GSM();
		sb = new SpriteBatch();
		manager.add(new Title(manager));
	}

	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		manager.update(Gdx.graphics.getDeltaTime());
		manager.render(sb);
	}
}
