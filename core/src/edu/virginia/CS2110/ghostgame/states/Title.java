package edu.virginia.CS2110.ghostgame.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Title extends State {

	public Title(GSM manager){
		super(manager);
	}
	
	public void handleInput() {

	}

	public void update(float dt) {
		System.out.println("title screen is updating!");
		handleInput();
	}

	public void render(SpriteBatch sb) {
		System.out.println("title screen is \"rendering\"!");
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
			//sb.draw(TextureRegion, x, y);
		sb.end();
	}

}
