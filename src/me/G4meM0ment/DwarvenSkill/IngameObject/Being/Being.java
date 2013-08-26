package me.G4meM0ment.DwarvenSkill.IngameObject.Being;

import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.Dwarf;

public class Being {
	
	private BeingHandler bh;
	
	private int id = -1;
	private BeingType type;
	private Dwarf dwarf = null;

	public Being(BeingType type) {
		bh = new BeingHandler();
		
		this.type = type;
		createSpecificBeing(type);
		id = bh.getUnusedID();
		bh.addBeing(this);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public BeingType getType() {
		return type;
	}
	public void setType(BeingType type) {
		this.type = type;
	}
	
	public Dwarf getDwarf() {
		return dwarf;
	}
	
	public void createSpecificBeing(BeingType type) {
		switch(type) {
			case DWARF:
				dwarf = new Dwarf();
			case ANIMAL:
				//TODO create other beings
		}
	}

}
