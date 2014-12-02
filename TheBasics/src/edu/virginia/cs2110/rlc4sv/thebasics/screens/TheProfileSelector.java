package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Profile;


public class TheProfileSelector extends Activity {

	public final static String PROFILE_ONE = "com.example.myfirstapp.MESSAGE1";
	public final static String PROFILE_TWO = "com.example.myfirstapp.MESSAGE2";
	public final static String PROFILE_THREE = "com.example.myfirstapp.MESSAGE3";
	public final static String PROFILE_FOUR = "com.example.myfirstapp.MESSAGE4";
	public final static String PROFILE_FIVE = "com.example.myfirstapp.MESSAGE5";

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
				intent.putExtra(PROFILE_ONE, playerName);
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
				intent.putExtra(PROFILE_TWO, playerName);
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
				intent.putExtra(PROFILE_THREE, playerName);
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
				intent.putExtra(PROFILE_FOUR, playerName);
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
				intent.putExtra(PROFILE_FIVE, playerName);
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

		Profile Zakey = new Profile("Zakey");
		Profile Julian = new Profile("Julian");
		Profile Ryan = new Profile("Ryan");
		Profile Alex = new Profile("Alex");
		Profile Davy = new Profile("Davy");

		((TextView) (findViewById(R.id.profile_1))).setText(Zakey.getProfileName());
		((TextView) (findViewById(R.id.profile_2))).setText(Julian.getProfileName());
		((TextView) (findViewById(R.id.profile_3))).setText(Ryan.getProfileName());
		((TextView) (findViewById(R.id.profile_4))).setText(Alex.getProfileName());
		((TextView) (findViewById(R.id.profile_5))).setText(Davy.getProfileName());

	}

}
