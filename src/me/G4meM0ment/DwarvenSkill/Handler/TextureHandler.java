package me.G4meM0ment.DwarvenSkill.Handler;

import me.G4meM0ment.DwarvenSkill.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TextureHandler {
	
	private ScreenHandler sh;
	private Resources resources;
	private int tSize;
	private int textureRectWidth = 16;
	private int textureRectHeight = 16;
	private int textureCount = textureRectWidth*textureRectHeight; //TODO read from file
	private static Bitmap texture = null;
	private static Bitmap[] textures;
	
	public TextureHandler(Resources resources) {
		this.resources = resources;
		sh = new ScreenHandler();
		
		texture = BitmapFactory.decodeResource(resources, R.drawable.default_texture); //TODO allow adding own textures
		tSize = sh.getTSize();
		textures = splitBitmap(texture);
	}
	public TextureHandler() {
	}
	
	public Bitmap[] getTextures() {
		return textures;
	}
	
	private Bitmap[] splitBitmap(Bitmap picture)
	{
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture, textureRectWidth*tSize, textureRectHeight*tSize, true);
		int count = (scaledBitmap.getHeight()/tSize)*(scaledBitmap.getWidth()/tSize); 
		Bitmap[] imgs = new Bitmap[count]; //removed -1
		int x = 0, y = 0;
		for(int i = 0; i <= count; i++) {
			if(y*tSize+tSize > scaledBitmap.getHeight())
				break;
			imgs[i] = Bitmap.createBitmap(scaledBitmap, x*tSize, y*tSize, tSize, tSize);
			x++;
			if(y > textureRectHeight-1)
				break;
			if(x >= textureRectWidth) {
				x = 0;
				y++;
			}
		}
		return imgs;
	}

}
