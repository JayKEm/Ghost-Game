package edu.virginia.CS2110.ghostgame.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Play extends State {

	public Play(GSM manager){
		super(manager);
	}
	
	public void handleInput() {

	}

	public void update(float dt) {
		handleInput();
	}

	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
			//sb.draw(TextureRegion, x, y, width, height);
		sb.end();
	}

}
