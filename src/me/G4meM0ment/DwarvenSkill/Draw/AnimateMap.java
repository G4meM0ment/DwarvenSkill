package me.G4meM0ment.DwarvenSkill.Draw;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Landscape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Shape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Type;

public class AnimateMap {
	
	private Context context;
	private FilesHandler fh;
	private CoordsHandler ch;
	private ScreenHandler sh;
	private Map map;
	
	private static final String tag = "AnimateMap";
	
	public AnimateMap(Context context) {
		this.context = context;
		fh = new FilesHandler(context);
		ch = new CoordsHandler();
		sh = new ScreenHandler();
		map = new Map();
	}
	public AnimateMap() {
		
	}
	
	public synchronized void animateXYDistance(int xDistance, int yDistance) {
		if(xDistance > 1) xDistance = 1;
		if(yDistance > 1) yDistance = 1;
		if(xDistance < -1) xDistance = -1;
		if(yDistance < -1) yDistance = -1;
		Coordinate mCoord = ch.getScreenMiddleCoordinate();
		if(mCoord == null) return; 
		List<Coordinate> allCoords = ch.getVisibleCoords();
		for(Coordinate c : allCoords) {
			c.setxS(-1);
			c.setyS(-1);
		}
		int x = mCoord.getX() + xDistance;
		int y = mCoord.getY() - yDistance;
		int z = Map.getZ();
		mCoord = ch.getCoord(x, y, z);
		ch.setScreenMiddleCoordinate(mCoord);
		ch.setupScreenCoords();
	}
	
	public void animateXYZ(int x, int y, int z) {
		Coordinate mCoord = ch.getScreenMiddleCoordinate();
		List<Coordinate> allCoords = ch.getAllCoords();
		for(Coordinate c : allCoords) {
			c.setxS(-1);
			c.setyS(-1);
		}
		mCoord = ch.getCoord(x, y, z);
		ch.setScreenMiddleCoordinate(mCoord);
		ch.setupScreenCoords();
	}
	
	public void animateZ(int zDistance) {
		if(Map.getZ()+zDistance > Map.getMapZ() || Map.getZ()+zDistance < 1)
			return;
		Coordinate mCoord = ch.getScreenMiddleCoordinate();
		List<Coordinate> allCoords = ch.getAllCoords();
		for(Coordinate c : allCoords) {
			c.setxS(-1);
			c.setyS(-1);
		}
		int z = mCoord.getZ() + zDistance;
		mCoord = ch.getCoord(mCoord.getX(), mCoord.getY(), z);
		Map.setZ(Map.getZ()+zDistance);
		ch.setScreenMiddleCoordinate(mCoord);
		ch.setupScreenCoords();		
	}
	
	@SuppressWarnings("static-access")
	public void tellCoordinate(float sX, float sY) {
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
    	y = middle.getY() + diffY;
    	z = map.getZ();
    	
    	if((x >= 1 && y >= 1 && z >= 1) && (x <= map.getMapX() && y <= map.getMapY() && z <= map.getMapZ())) {
    		Coordinate touched = ch.getCoord(x, y, z);
    		Toast.makeText(context, "Coordinate: x"+x+", y"+y+", z"+z, Toast.LENGTH_SHORT).show();
    		Log.v(tag, "Coordinate: x"+x+", y"+y+", z"+z);
    		touched.getLandscape().getType().setTexture(33);
    		touched.getLandscape().getType().setColor(Color.MAGENTA);
    	}
	}
	
	public boolean checkRightEdgeVisible() {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getX() == Map.getMapX())
				return true;
		}
		return false;
	}
	public boolean checkLeftEdgeVisible() {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getX() <= 1)
				return true;
		}
		return false;
	}
	public boolean checkTopEdgeVisible() {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getY() <= 1)
				return true;
		}
		return false;
	}
	public boolean checkBottomEdgeVisible() {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getY() == Map.getMapY())
				return true;
		}
		return false;
	}
	
	public boolean checkRightMovement(int dist) {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getX()+dist > Map.getMapX())
				return true;
		}
		return false;
	}
	public boolean checkLeftMovement(int dist) {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getX()-dist < 1)
				return true;
		}
		return false;
	}
	public boolean checkTopMovement(int dist) {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getY()- dist < 1)
				return true;
		}
		return false;
	}
	public boolean checkBottomMovement(int dist) {
		for(Coordinate c : ch.getVisibleCoords()) {
			if(c.getY()+dist > Map.getMapY())
				return true;
		}
		return false;
	}
	
	public boolean checkEdge(Coordinate c) {
		if(c.isEdge() && c.isVisible())
			return true;
		else
			return false;
	}

}
