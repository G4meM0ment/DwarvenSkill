package me.G4meM0ment.DwarvenSkill.WorldGenerator;

import java.util.List;

import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Shape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Type;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Types;

public class WorldGenerator {

	private MidPointDisplacement mdp;
	private CoordsHandler ch;
	
	private boolean isConverted = false;
	private boolean isGenerated = false;
	
	public WorldGenerator() {
		mdp = new MidPointDisplacement();
		ch = new CoordsHandler();
	}
	
	public boolean isConverted() {
		return isConverted;
	}
	public void setConverted(boolean isConverted) {
		this.isConverted = isConverted;
	}

	public boolean isGenerated() {
		return isGenerated;
	}
	public void setGenerated(boolean isRegenrated) {
		this.isGenerated = isRegenrated;
	}

	public void convert() {
		isConverted = false;
		int[][] heights = mdp.generate();
		
		int x = 1;
		int y = 1;
		int z = 1;
		for(int i = 0; i <= Map.getMap2DSize(); i++) {
			
	        if(x > Map.getMapX()) {
	        	y++;
	        	x = 1;
	        	if(y > Map.getMapY()) break;
	        }
	        if(y > Map.getMapY()) break;
			
			z = heights[x-1][y-1];
			if(z > Map.getMapZ()) z = Map.getMapZ();
			Coordinate c = ch.getCoord(x, y, z);
			c.getLandscape().getType().setType(Types.SOIL);
			c.getLandscape().setShape(Shape.GROUND);
			x++;
		}
		isConverted = true;
	}
	
	public void regenerateLandscape() {
		isGenerated = false;
		List<Coordinate> coords = ch.getAllCoords();
		
		int x = 1;
		int y = 1;
		int z = 1;
		boolean top = false;
		for(int i = 0; i <= Map.getMapSize(); i++) {
			if(z > Map.getMapZ()) {
				x++;
				z = 1;
				top = false;
			}
	        if(x > Map.getMapX()) {
	        	y++;
	        	x = 1;
	        	if(y > Map.getMapY()) break;
	        }
	        if(y > Map.getMapY()) break;
			
	        Coordinate c = ch.getCoord(x, y, z);
	        if(c.getLandscape().getType().getType() == Types.SOIL) {
	        	top = true;
	        	z++;
	        	continue;
	        }
			if(!top) {
				c.getLandscape().getType().setType(Types.FELSITE);
				c.getLandscape().setShape(Shape.SOLID);
			} else {
				c.getLandscape().getType().setType(Types.AIR);
				c.getLandscape().setShape(Shape.AIR);
			}
			z++;
		}
		isGenerated = true;
	}
}
