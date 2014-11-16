package edu.virginia.cs2110.rc4sv.thebasics.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.screens.OurView;

public class Coin extends Entity {
	
	private int value;

	public Coin(OurView ov, Bitmap coinSprites, int x, int y) {
		super(ov, coinSprites, x, y);
		width = coinSprites.getWidth()/8;
		height = coinSprites.getHeight();
		bounds = new Rect(x + width/4, y, x + width*2, y + height*2);
		
		//different coins have different values. if a coin
		//has a certain image, give it a value
		if(coinSprites.equals(BitmapFactory.decodeResource(ov.getResources(), R.drawable.coin_gold)))
			value = 10;

		id = "Coin " + value;
	}
	
	public int getValue(){
		return value;
	}

}
