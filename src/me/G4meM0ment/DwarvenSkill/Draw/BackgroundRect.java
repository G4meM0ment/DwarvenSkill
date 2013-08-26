package me.G4meM0ment.DwarvenSkill.Draw;

import android.graphics.Color;

public class BackgroundRect {
	
	private int color;
	private int defaultColor = Color.GREEN;
	
	public BackgroundRect() {
		color = defaultColor;	
	}
	public BackgroundRect(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}
