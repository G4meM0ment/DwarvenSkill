package me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf;

import android.graphics.Color;


public class Dwarf{
	
	private DwarfHandler dh;
	
	private int id = -1;
	private String fName = "Thonak";
	private String lName = "Strongarm";
	private String professionName = "Peasent";
	private Profession profession = Profession.PEASENT;
	private int texture = 1;
	private int x = -1;
	private int y = -1;
	private int z = -1;
	private int color = 0;
	private int defaultColor = Color.YELLOW;

	public Dwarf() {
		dh = new DwarfHandler();
		
		color = defaultColor;
		id = dh.getUnusedId();
		dh.addDwarf(this);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	
	public Profession getProfession() {
		return profession;
	}
	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	
	public int getTexture() {
		return texture;
	}
	public void setTexture(int texture) {
		this.texture = texture;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}
