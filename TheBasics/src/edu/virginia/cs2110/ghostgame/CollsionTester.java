package edu.virginia.cs2110.ghostgame;

import edu.virginia.cs2110.rlc4sv.thebasics.R;
import android.app.Activity;
import android.os.Bundle;

public class CollsionTester extends Activity {

	Ghost g;
	Player p;
	
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.collision_tester);
		
//		g = (Ghost) findViewById(R.id.ghost);
//		p = (Player) findViewById(R.id.player);
	}
}
