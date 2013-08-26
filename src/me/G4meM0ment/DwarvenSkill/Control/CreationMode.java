package me.G4meM0ment.DwarvenSkill.Control;

import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.Dwarf;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Landscape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Type;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

public class CreationMode {
	
	private ScreenHandler sh;
	private CoordsHandler ch;
	private Map map;
	
	public CreationMode() {
		sh = new ScreenHandler();
		ch = new CoordsHandler();
		map = new Map();
	}
	
	@SuppressWarnings("static-access")
	public void setDwarfToPos(float sX, float sY, Dwarf dwarf) {
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
    		dwarf.setX(x);
    		dwarf.setY(y);
    		dwarf.setZ(z);
    	}
	}

}
