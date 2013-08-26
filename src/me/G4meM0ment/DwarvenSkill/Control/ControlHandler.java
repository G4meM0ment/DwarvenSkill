package me.G4meM0ment.DwarvenSkill.Control;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import me.G4meM0ment.DwarvenSkill.Draw.AnimateMap;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;

public class ControlHandler {
	
	private AnimateMap animateMap;
	private CoordsHandler ch;
	private ScreenHandler sh;
	private Map map;
	
	private static Coordinate start = null;
	private static Coordinate end = null;
	private static List<Coordinate> designated = new ArrayList<Coordinate>();
	
	private static ControlMode ctrlMode = ControlMode.MOVE;
	
	public ControlHandler() {
		animateMap = new AnimateMap();
		ch = new CoordsHandler();
		sh = new ScreenHandler();
		map = new Map();
	}
	
	@SuppressWarnings("static-access")
	public void setCtrlMode(ControlMode mode) {
		this.ctrlMode = mode;
	}
	public ControlMode getCtrlMode() {
		return ctrlMode;
	}
	
	public void processMode() {
		switch(ctrlMode) {
			case MOVE:
				
			case CREATE:
		}
	}
	
	private void designate(Coordinate c) {
		if(start == null) {
			start = c;
			c.getLandscape().getType().setTexture(88);
			c.getLandscape().getType().setColor(Color.BLACK);
		}
		else {
			end = c;
			c.getLandscape().getType().setTexture(88);
			c.getLandscape().getType().setColor(Color.BLACK);
		}
		if(start == null || end == null)
			return;
		 
		int startX = start.getX();
		int startY = start.getY();
		int startZ = start.getZ();
		int endX = end.getX();
		int endY = end.getY();
		int endZ = end.getZ();
		int diffX = Math.abs(startX-endX)+1;
		int diffY = Math.abs(startY-endY)+1;
		int diffZ = Math.abs(startZ-endZ)+1;
		int x, y, z, fX, fY, fZ, dX, dY, dZ;
		if(startX > endX) {
			x = dX = endX;
			fX = startX;
		}
		else {
			x = dX = startX;
			fX = endX;
		}
		if(startY > endY) {
			y = dY = endY;
			fY = startY;
		}
		else {
			y = dY = startY;
			fY = endY;
		}
		if(startZ > endZ) {
			z = dZ = endZ;
			fZ = startZ;
		}
		else {
			z = dZ = startZ;
			fZ = endZ;
		}
		if(diffZ <= 0)
			diffZ = 1;
		
//		unmarkDesignated(start, end);
		designated.removeAll(designated);
		for(int i = 0; i <= (diffX*diffY*diffZ); i++) {
	        //if an x-line is completed
	        if(x > fX) {
	        	y++;
	        	x = dX;
	        	if(y > fY) y++;
	        }
	        if(y > fY) {
	        	z++;
	        	x = dX;
	        	y = dY;
	        }
	        if(z > fZ) break;
	        //fills x-line
	        Coordinate add = ch.getCoord(x, y, z);
	        designated.add(add);
	        x++;
		}
		markDesignated(start, end);
	}
	
	private void markDesignated(Coordinate s, Coordinate e) {
		for(Coordinate c : designated) {
			c.getLandscape().getType().setTexture(95);
		}
	}
	
	private void unmarkDesignated(Coordinate s, Coordinate e) {
		for(Coordinate c : designated) {
			c.getLandscape().getType().setTexture(95);
		}
		s.getLandscape().getType().setTexture(88);
		e.getLandscape().getType().setTexture(88);
		s.getLandscape().getType().setColor(Color.BLACK);
		e.getLandscape().getType().setColor(Color.BLACK);
	}
	
	@SuppressWarnings("static-access")
	public void processDesignatedCoord(float sX, float sY) {
		if(sX <= 0 || sY <= 0) return;
    	int touchedxS, touchedyS, rX, rY, xRect, yRect, mXRect, mYRect, diffX, diffY, x, y, z, tSize, side;
    	tSize = sh.getTSize();
    	side = sh.getSideSize();
    	
    	Coordinate middle = ch.getScreenMiddleCoordinate();
    	mXRect = middle.getxS()/tSize;
    	mYRect = middle.getyS()/tSize;
    	
    	touchedxS = (int) sX;
    	touchedyS = (int) sY;
    	xRect = (int) Math.floor((touchedxS/tSize)-1);
    	yRect = (int) Math.floor((touchedyS/tSize)-1);
    	rX = touchedxS%tSize;
    	rY = touchedyS%tSize;
    	
    	if(sX > side && rX > 0)
    		xRect += 1;
    	if(rY > 0)
    		yRect += 1;
    	
    	diffX = mXRect - xRect;
    	diffY = mYRect - yRect;
    	
    	x = middle.getX() - diffX;
    	y = middle.getY() - diffY;
    	z = map.getZ();
    	
    	if((x >= 1 && y >= 1 && z >= 1) && (x <= map.getMapX() && y <= map.getMapY() && z <= map.getMapZ())) {
    		designate(ch.getCoord(x, y, z));
    	}
	}
}
