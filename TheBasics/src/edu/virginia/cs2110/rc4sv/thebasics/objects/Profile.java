package edu.virginia.cs2110.rc4sv.thebasics.objects;


public class Profile {

	String profileName;
	int profileHighScore;
	//image SpriteSheet

	public Profile(String nm /*, image sprtsheet)*/)
	{
		profileName = nm;
		profileHighScore = 0; //pull saved data
		//spriteSheetX = sprtsheet;
	}


	public String getProfileName() {
		return profileName;
	}

	public int getProfileHighScore() {
		return profileHighScore;
	}
	
	
}
