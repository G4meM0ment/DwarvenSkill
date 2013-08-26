package me.G4meM0ment.DwarvenSkill.Map.Landscape;

import android.graphics.Color;


public class Type {
	
	private int defaultTexture = 0;
	private int texture;
	private int color;
	private int foggedTexture = 32;
	private int foggedColor = Color.BLACK;
	private int defaultColor = Color.CYAN;
	private Types type;
	private Types defaultType = Types.AIR;
	
	public Type(Types type) {
		this.type = type;
		processType();
		processFogged();
	}
	public Type() {
		this.texture = defaultTexture;
		this.color = defaultColor;
		this.type = defaultType;
		processType();
		processFogged();
	}
	public Type(Types type, int texture, int c) {
		this.type = type;
		this.texture = texture;
		this.color = c;
		processFogged();
	}
	public int getDefaultTexture() {
		return defaultTexture;
	}
	
	public void setDefaultTexture() {
		this.texture = defaultTexture;
	}
	
	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public Types getType() {
		return type;
	}
	public void setType(Types type) {
		this.type = type;
		processType();
	}
	
	public Types getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(Types type) {
		this.defaultType = type;
	}
	
	public int getFoggedTexture() {
		return foggedTexture;
	}
	public void setFoggedTexture(int foggedTexture) {
		this.foggedTexture = foggedTexture;
	}
	
	public int getFoggedColor() {
		return foggedColor;
	}
	public void setFoggedColor(int foggedColor) {
		this.foggedColor = foggedColor;
	}
	
	private void processType() {
		
		switch(type) {
			case FELSITE: {
				texture = 132;
				color = Color.rgb(128, 128, 128); //Dark Grey
				break;
			}
			case SOIL: {
				texture = 126;
				color = Color.rgb(204, 102, 0); //Brown
				break;
			}
			case SAND: {
				texture = 126;
				color = Color.rgb(204, 102, 0); //Sandy
				break;
			}
			case AIR: {
				texture = 250;
				color = Color.CYAN;
				break;
			}
		}
	}
	
	private void processFogged() {
		int rand = (int) (Math.random()*100);
		if(rand >= 80)
			foggedColor = Color.rgb(128, 128, 128);	
		if(rand >= 95)
			foggedTexture = 28;
	}
}
