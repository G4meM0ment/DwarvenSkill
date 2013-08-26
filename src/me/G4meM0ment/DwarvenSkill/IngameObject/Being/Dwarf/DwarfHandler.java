package me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf;

import java.util.ArrayList;
import java.util.List;

import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Shape;


public class DwarfHandler {
	
	private Map map;
	private CoordsHandler ch;
	
	private static List<Dwarf> dwarves = new ArrayList<Dwarf>();
	
	public DwarfHandler() {
		map = new Map();
		ch = new CoordsHandler();
	}
	
	public synchronized List<Dwarf> getAllDwarves() {
		return dwarves;
	}
	public void addDwarf(Dwarf dwarf) {
		getAllDwarves().add(dwarf);
	}
	public void removeDwarf(Dwarf dwarf) {
		dwarf.setId(-1);
	} //TODO create method to remove unused dwarfs (id -1)
	
	public int getDwarfListLoc(Dwarf dwarf) {
		if(dwarf.getId() <= -1)
			return -1;
		
		return dwarf.getId() -1;
	}
	
	public int getUnusedId() {
		return getAllDwarves().size();
	}
	
	public void removeUnusedDwarfs() {
		for(int i = 0; i < getAllDwarves().size(); i++) {
			Dwarf d = getAllDwarves().get(i);
			if(d.getId() <= -1) {
				for(int o = i; o < getAllDwarves().size(); o++) {
					Dwarf other = getAllDwarves().get(o);
					other.setId(other.getId()-1);
				}
				getAllDwarves().remove(i);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void firstTestMove() {
		for(Dwarf d : getAllDwarves()) { //TODO make movable on z-axis
			int rand = (int)(Math.random()*100);
			int rand2 = (int)(Math.random()*100);
			int x, y, z;
			x = d.getX();
			y = d.getY();
			z = d.getZ();
			if(rand <= 50) {
				if(rand2 <= 50)
					x += 1;
				else
					x -= 1;
				if(x >= map.getMapX())
					x = 1;
				if(x <= 1)
					x = map.getMapX();
			}
			if(rand >= 50 && rand <= 100) {
				if(rand2 <= 50)
					y += 1;
				else
					y -= 1;
				if(y >= map.getMapY())
					y = 1;
				if(y <= 1)
					y = map.getMapY();
			}
			Shape s = ch.getCoord(x, y, z).getLandscape().getShape();
			if(s != Shape.SOLID && s != Shape.AIR) {
				d.setX(x);
				d.setY(y);
				d.setZ(z);
			}
		}
	}
}
