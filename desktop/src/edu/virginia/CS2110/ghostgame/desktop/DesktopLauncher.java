package edu.virginia.CS2110.ghostgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.virginia.CS2110.ghostgame.main.GhostGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GhostGame.WIDTH ;
		config.height = GhostGame.HEIGHT;
		config.title = GhostGame.title;
		
		new LwjglApplication(new GhostGame(), config);
	}
}
