package edu.virginia.cs2110.rlc4sv.thebasics.objects;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;

public class Profile {

	private String profileName;
	private int profileHighScore;

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
		} catch (Exception e){
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
