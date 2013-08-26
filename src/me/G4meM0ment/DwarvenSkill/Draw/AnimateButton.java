package me.G4meM0ment.DwarvenSkill.Draw;

public class AnimateButton {
	
	private static boolean pause = true;
	private static boolean left = false;
	
	public AnimateButton() {
		
	}
	
	public void changePPState() {
		if(pause) pause = false;
		else pause = true;
	}
	public boolean getPPState() {
		return pause;
	}
	public void setPPState(boolean s) {
		pause = s;
	}
	
	public void changeLeftState() {
		if(left) left = false;
		else left = true;
	}
	public boolean getLeftState() {
		return left;
	}
	public void setLeftState(boolean s) {
		left = s;
	}
}
