package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.util.Log;

public class Profile {

	String profileName;
	int profileHighScore;

	@SuppressWarnings("deprecation")
	public Profile(String nm, Context cx) {
		profileName = nm;
		profileHighScore = 0;
			
		try{
			String path = profileName +".txt";
			FileInputStream fis = cx.openFileInput(path);
			DataInputStream dataIO = new DataInputStream(fis);
			String line = null;

			while ((line = dataIO.readLine()) != null) {
				profileHighScore = Integer.parseInt(line);

			}
			dataIO.close();
		} catch (FileNotFoundException e) {
			profileHighScore = 0;
			Log.e("failed not found", e.getMessage());
		} catch (Exception e){
			Log.e("failed other error", e.getMessage());
			profileHighScore = 0;
		}
	}

	public String getProfileName() {
		return profileName;
	}

	public int getProfileHighScore() {
		return profileHighScore;
	}

}
