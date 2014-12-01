package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import edu.virginia.cs2110.rc4sv.thebasics.objects.Profile;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.R.id;
import edu.virginia.cs2110.rlc4sv.thebasics.R.layout;
import edu.virginia.cs2110.rlc4sv.thebasics.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TheProfileSelector extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_profile_selector);
		
		
		Button startG = (Button)findViewById(R.id.profile_button_one);
		startG.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		Button startG1 = (Button)findViewById(R.id.profile_button_two);
		startG1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		Button startG2 = (Button)findViewById(R.id.profile_button_three);
		startG2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		Button startG3 = (Button)findViewById(R.id.profile_button_four);
		startG3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		Button startG4 = (Button)findViewById(R.id.profile_button_five);
		startG4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		Button startG5 = (Button)findViewById(R.id.default_temporary_button);
		startG5.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//buttonSound.start();	
				startActivity(new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME"));	
			}
		});
		
		
		Profile Zakey = new Profile("Zakey" /*, spritesheet... for all profiles);*/);
		Profile Julian = new Profile("Julian");
		Profile Ryan = new Profile("Ryan");
		Profile Alex = new Profile("Alex");
		Profile Davy = new Profile("Davy");
		
		((TextView)(findViewById(R.id.profile_1))).setText(Zakey.getProfileName());
		((TextView)(findViewById(R.id.profile_2))).setText(Julian.getProfileName());
		((TextView)(findViewById(R.id.profile_3))).setText(Ryan.getProfileName());
		((TextView)(findViewById(R.id.profile_4))).setText(Alex.getProfileName());
		((TextView)(findViewById(R.id.profile_5))).setText(Davy.getProfileName());
		
		
	}
	
	
	

}
