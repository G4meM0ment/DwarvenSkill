package me.G4meM0ment.DwarvenSkill.Map.Coordinates;

import me.G4meM0ment.DwarvenSkill.Draw.BackgroundRect;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Landscape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Shape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Types;

public class Coordinate {
	
	private int x, y, z, xS, yS;
	private Landscape l = null;
	private BackgroundRect bRect;
	private CoordsHandler ch;
	
	public Coordinate() {
		xS = -1;
		yS = -1;
		l = new Landscape();
		bRect = new BackgroundRect();
		ch = new CoordsHandler();
	}
	
	public Coordinate(int x, int y, int z, int xS, int yS, Landscape l, BackgroundRect bRect) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xS = xS;
		this.yS = yS;
		this.l = l;
		this.bRect = bRect;
		ch = new CoordsHandler();
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

	public int getxS() {
		return xS;
	}

	public void setxS(int xS) {
		this.xS = xS;
	}

	public void setyS(int yS) {
		this.yS = yS;
	}
	public int getyS() {
		return yS;
	}

	public Landscape getLandscape() {
		return l;
	}

	public void setLandscape(Landscape l) {
		this.l = l;
	}
	
	public BackgroundRect getbRect() {
		return bRect;
	}
	public void setbRect(BackgroundRect bRect) {
		this.bRect = bRect;
	}
	
	public boolean isVisible() {
		if((xS < 0) || (yS < 0))
			return false;
		else if ((xS > -1) && (yS > -1))
			return true;
		else
			return false;
	}
	
	public boolean isCorner() {
		if((x == 0 && y == 0) 
				||(x == 0 && y == Map.getMapY()) 
				|| (x == Map.getMapX() && y == 0) 
				|| (x == Map.getMapX() && y == Map.getMapY()))
			return true;
		else
			return false;
	}
	
	public boolean isEdge() {
		if(x == 0 || y == 0 || x == Map.getMapX() || y == Map.getMapY())
			return true;
		else
			return false;
	}
	
	public boolean isDiscovered() {
		if(getLandscape().getShape() != Shape.AIR && getLandscape().getShape() != Shape.LIQUID && getLandscape().getShape() != Shape.GROUND) {
			Coordinate xplusC = ch.getCoord(x+1, y, z);
			Coordinate xminusC = ch.getCoord(x-1, y, z);
			Coordinate yplusC = ch.getCoord(x, y+1, z);
			Coordinate yminusC = ch.getCoord(x, y-1, z);
			Coordinate dia1C = ch.getCoord(x+1, y+1, z);
			Coordinate dia2C = ch.getCoord(x+1, y-1, z);
			Coordinate dia3C = ch.getCoord(x-1, y-1, z);
			Coordinate dia4C = ch.getCoord(x-1, y+1, z);
			Coordinate above = ch.getCoord(x, y, z+1);
			Shape s1, s2, s3, s4, s5, s6, s7, s8, s9;
			if(xplusC != null) s1 = xplusC.getLandscape().getShape();
			else s1 = Shape.SOLID;
			if(xminusC != null) s2 = xminusC.getLandscape().getShape();
			else s2 = Shape.SOLID;
			if(yplusC != null) s3 = yplusC.getLandscape().getShape();
			else s3 = Shape.SOLID;
			if(yminusC != null) s4 = yminusC.getLandscape().getShape();
			else s4 = Shape.SOLID;
			if(dia1C != null) s5 = dia1C.getLandscape().getShape();
			else s5 = Shape.SOLID;
			if(dia2C != null) s6 = dia2C.getLandscape().getShape();
			else s6 = Shape.SOLID;
			if(dia3C != null) s7 = dia3C.getLandscape().getShape();
			else s7 = Shape.SOLID;
			if(dia4C != null) s8 = dia4C.getLandscape().getShape();
			else s8 = Shape.SOLID;
			if(above != null) s9 = above.getLandscape().getShape();
			else s9 = Shape.SOLID;
			if((s1 != Shape.AIR && s2 != Shape.AIR && s3 != Shape.AIR && s4 != Shape.AIR && s5 != Shape.AIR && s6 != Shape.AIR && s7 != Shape.AIR && s8 != Shape.AIR && s9 != Shape.AIR)
					&&(s1 != Shape.LIQUID && s2 != Shape.LIQUID && s3 != Shape.LIQUID && s4 != Shape.LIQUID && s5 != Shape.LIQUID && s6 != Shape.LIQUID && s7 != Shape.LIQUID && s8 != Shape.LIQUID) 
					&&(s1 != Shape.GROUND && s2 != Shape.GROUND && s3 != Shape.GROUND && s4 != Shape.GROUND && s5 != Shape.GROUND && s6 != Shape.GROUND && s7 != Shape.GROUND && s8 != Shape.GROUND))				
				return false;
			else
				return true;
		}
		return true;
	}
}
