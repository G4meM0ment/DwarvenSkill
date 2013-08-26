package me.G4meM0ment.DwarvenSkill.Map;

import java.util.ArrayList;
import java.util.List;

import me.G4meM0ment.DwarvenSkill.Draw.BackgroundRect;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Landscape;

public class CoordsHandler {
	
	private Map map;
	private ScreenHandler sh;
	
	private static List<Coordinate> coords = new ArrayList<Coordinate>();
	private static Coordinate middle = null;
	private static boolean coordsReady;
	private static boolean visReady;
	public CoordsHandler() {
		this.map = new Map();
		this.sh = new ScreenHandler();
	}
	
	public synchronized List<Coordinate> getVisibleCoords() {
		if(getAllCoords() == null) return null;
		List<Coordinate> vC = new ArrayList<Coordinate>();		
		for(Coordinate c : getAllCoords()) {
			if(c.isVisible())
				vC.add(c);
		}
		return vC;
	}
	
	public synchronized List<Coordinate> getAllCoords() {
		return coords;
	}
	
	public synchronized void setAllCoordinates(List<Coordinate> cL) {
		if(getAllCoords() == null)
			return;
		getAllCoords().removeAll(getAllCoords());
		getAllCoords().addAll(cL);
	}
	
	public Coordinate getCoord(int x, int y, int z) {
		if(x > Map.getMapX() || x < 1 || y > Map.getMapY() || y < 1 || z > Map.getMapZ() || z < 1) return null;
		int mX = Map.getMapX();
		int mapLayer = Map.getMap2DSize();
//		int loc = ((z*(-1))+1)+((y)*mX)+x-1;	Used if z uses negative values			
		int loc = ((z-1)*mapLayer)+((y-1)*mX)+(x-1);
		if(loc > getAllCoords().size()-1 || loc < 0) return null;
		return getAllCoords().get(loc);
	}
	
	public synchronized Coordinate getScreenMiddleCoordinate() {
		return middle;
	}
	@SuppressWarnings("static-access")
	public void setScreenMiddleCoordinate(Coordinate middle) {
		this.middle = middle;
	}
	public Coordinate unvanishCoord(int x, int y, int z, int xS, int yS) {
		Coordinate c = getCoord(x, y, z);
		if(c == null) return null;
		c.setxS(xS);
		c.setyS(yS);
		return c;
	}
	
	public void setupCoordinates() {
		coordsReady = false;
		List<Coordinate> nC = new ArrayList<Coordinate>();
		int y = 1;
	  	int x = 1;
	  	int z = 1;
	  	int xS = -1;
	  	int yS = -1;
	  	int rectX = Map.getMapX();
	  	int rectY = Map.getMapY();
	  	int rectZ = Map.getMapZ();
	  	int mapSize = Map.getMapSize();
	  	 
	  	for(int i = 0; i <= mapSize; i++) {
	  		Landscape l = new Landscape();
	  		BackgroundRect bRect = new BackgroundRect();
	        
	        if(mapSize <= 1) {
	        	nC.add(new Coordinate(x, y, z, xS, yS, l, bRect));
	        	break;
	        }
	        //When z reaches end
	        //if an x-line is completed
	        if(x > rectX) {
	        	y++;
	        	x = 1;
	        	if(y > rectY) y++;
	        }
	        if(y > rectY) {
	        	z++;
	        	x = 1;
	        	y = 1;
	        }
	        if(z > rectZ) break;
	        //fills x-line
	        nC.add(new Coordinate(x, y, z, xS, yS, l, bRect));
	        x++;
	  	}
		coords.addAll(nC);
	  	coordsReady = true;
	}
	
	@SuppressWarnings("static-access")

	public void setupMiddleScreenCoord() {
	    int mX, mY, x, y, z, xS, yS;
	    mX = map.getMapX();
	    mY = map.getMapY();
	    x = (int) Math.floor(mX/2+0.5);
	    y = (int) Math.floor(mY/2+0.5);
	    z = map.getZ();
	    
	    if(x <= 0) x = 1;
	    if(y <= 0) y = 1;
	    if(z <= 0) z = 1;
	    
        xS = ((sh.getScreenWidth()/2));
        yS = ((sh.getScreenHeight()/2));
	    Coordinate middle = getCoord(x, y, z);
	    setScreenMiddleCoordinate(middle);
        middle.setxS(xS);
        middle.setyS(yS);
	}
	
	@SuppressWarnings("static-access")
	public List<Coordinate> setupScreenCoords() {
		visReady = false;
		List<Coordinate> sC = new ArrayList<Coordinate>();
	        int x, y, z, mX, mY, mZ, xS, yS, xSDefault, drawedWidth, drawedHeight, screenHeight, screenWidth, xStart, tSize, side, screenCoords;
	        Coordinate middle = getScreenMiddleCoordinate();
	        if(middle == null) return null;
	        mX = middle.getX();
	        mY = middle.getY();
	        mZ = z = middle.getZ();
	        xS = xSDefault = side = sh.getSideSize();
	        yS = 0;
	        drawedWidth = sh.getDrawedWidth();
	        drawedHeight = sh.getDrawedHeight();
	        screenHeight = sh.getScreenHeight();
	        screenWidth = sh.getScreenWidth();
	        tSize = sh.getTSize();
	        screenCoords = sh.getScreenRects();

	        int cX = mX - (((drawedWidth - (screenWidth - drawedWidth))/2)/tSize)+1;
	        int cY = mY - (((drawedHeight - (screenHeight - drawedHeight))/2)/tSize)+1;
	        Coordinate corner = getCoord(cX, cY, mZ);
	        if(map.getMapX() < (screenWidth/tSize) || map.getMapY() < (screenHeight/tSize)) {
	        	x = xStart = cX;
	        	y = corner.getY();
	        	xS = (int) ((screenWidth/2)-((map.getMapX()*tSize)/2));
	        	yS = (int) ((screenHeight/2)-((map.getMapY()*tSize)/2));
	        	xSDefault = xS;
	        }
	        else {
		        drawedWidth -= side; //To avoid drawed textures under the GUI
	        	x = cX;
	        	y = cY;
	        	xStart = x;
	        	yS = drawedHeight-sh.getBottomGUISize()-sh.getTSize();
	        }        
	        for(int i = 0; i < screenCoords; i++) {

		  		//if an x-line is completed
		  		if(xS >= drawedWidth) {
		  			y++;
		  			x = xStart;
		  			xS = xSDefault;
		  			yS -= tSize;
		  		}
		  		if(yS < 0)
		  			break;
		  		//fills x-line
		  		Coordinate c = unvanishCoord(x, y, z, xS, yS);
		  		if(c == null) continue;
		  		sC.add(c);
		  		x++;
		  		xS += tSize;
	        }
	        visReady = true;
	    return sC;
	}
	
	public boolean isReady() {
		return coordsReady;
	}
	public void setReady(boolean cR) {
		coordsReady = cR;
	}
	
	public boolean isVisReady() {
		return visReady;
	}
	public void setVisReady(boolean cR) {
		visReady = cR;
	}
}
