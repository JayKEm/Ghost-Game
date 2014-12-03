package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Profile;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class TheProfileSelector extends Activity {

	public final static String PROFILE = "com.example.myfirstapp.MESSAGE1";
	//public final static String PROFILE_TWO = "com.example.myfirstapp.MESSAGE2";
	//public final static String PROFILE_THREE = "com.example.myfirstapp.MESSAGE3";
	//public final static String PROFILE_FOUR = "com.example.myfirstapp.MESSAGE4";
	//public final static String PROFILE_FIVE = "com.example.myfirstapp.MESSAGE5";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_profile_selector);

		Button startG = (Button) findViewById(R.id.profile_button_one);
		startG.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();

				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				String playerName = ((TextView) findViewById(R.id.profile_1))
						.getText().toString();
				intent.putExtra(PROFILE, playerName);
				startActivity(intent);
			}
		});

		Button startG1 = (Button) findViewById(R.id.profile_button_two);
		startG1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();
				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				String playerName = ((TextView) findViewById(R.id.profile_2))
						.getText().toString();
				intent.putExtra(PROFILE, playerName);
				startActivity(intent);
			}
		});

		Button startG2 = (Button) findViewById(R.id.profile_button_three);
		startG2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();
				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				String playerName = ((TextView) findViewById(R.id.profile_3))
						.getText().toString();
				intent.putExtra(PROFILE, playerName);
				startActivity(intent);
			}
		});

		Button startG3 = (Button) findViewById(R.id.profile_button_four);
		startG3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();
				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				String playerName = ((TextView) findViewById(R.id.profile_4))
						.getText().toString();
				intent.putExtra(PROFILE, playerName);
				startActivity(intent);
			}
		});

		Button startG4 = (Button) findViewById(R.id.profile_button_five);
		startG4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();
				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				String playerName = ((TextView) findViewById(R.id.profile_5))
						.getText().toString();
				intent.putExtra(PROFILE, playerName);
				startActivity(intent);
			}
		});

		Button startG5 = (Button) findViewById(R.id.default_temporary_button);
		startG5.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// buttonSound.start();

				Intent intent = new Intent(
						"edu.virginia.cs2110.rlc4sv.thebasics.MAINGAME");
				startActivity(intent);
			}
		});

		Profile Zakey = new Profile("Zakey", this);
		Profile Julian = new Profile("Julian", this);
		Profile Ryan = new Profile("Ryan", this);
		Profile Alex = new Profile("Alex", this);
		Profile Davy = new Profile("Davy", this);

		((TextView) (findViewById(R.id.profile_1))).setText(Zakey.getProfileName());
		((TextView) (findViewById(R.id.profile_2))).setText(Julian.getProfileName());
		((TextView) (findViewById(R.id.profile_3))).setText(Ryan.getProfileName());
		((TextView) (findViewById(R.id.profile_4))).setText(Alex.getProfileName());
		((TextView) (findViewById(R.id.profile_5))).setText(Davy.getProfileName());

		((TextView) (findViewById(R.id.hs1))).setText("Most Recent Score: " + Zakey.getProfileHighScore());
		((TextView) (findViewById(R.id.hs2))).setText("Most Recent Score: " + Julian.getProfileHighScore());
		((TextView) (findViewById(R.id.hs3))).setText("Most Recent Score: " + Ryan.getProfileHighScore());
		((TextView) (findViewById(R.id.hs4))).setText("Most Recent Score: " + Alex.getProfileHighScore());
		((TextView) (findViewById(R.id.hs5))).setText("Most Recent Score: " + Davy.getProfileHighScore());

	}

}
