package me.G4meM0ment.DwarvenSkill.Waiter;

public class WaiterModeHolder {
	private static WaiterMode mode;
	
	public WaiterModeHolder() {
		
	}
	
	public WaiterMode getMode() {
		return mode;
	}
	@SuppressWarnings("static-access")
	public void setMode(WaiterMode mode) {
		this.mode = mode;
	}
}
