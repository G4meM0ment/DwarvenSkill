package me.G4meM0ment.DwarvenSkill.Map.Landscape;

import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import android.graphics.Color;

public class LandscapeHandler {
	
	private Map map;
	private CoordsHandler ch;
	
	private boolean landscapeReady;
	
	public LandscapeHandler() {
		map = new Map();
		ch = new CoordsHandler();
	}
	
	@SuppressWarnings("static-access")
	public void setupLandscape() {
		landscapeReady = false;
	   	  int y = 1;
	  	  int x = 1;
	  	  int z = 1;
	  	  int rectX = map.getMapX();
	  	  int rectY = map.getMapY();
	  	  int rectZ = map.getMapZ();
	  	  int mapSize = map.getMapSize();
//	  	  int texture = 250;
	  	  int color = Color.CYAN;  //Color.rgb(76, 153, 0); //Dark Green
	  	  Shape shape = Shape.AIR;
	  	  Types type = Types.AIR;
	  	  
	  	  for(int i = 0; i <= mapSize; i++) {
	  		  
	  		  //Just for some testing
	  		  int rand = (int)(Math.random()*100);
		  	  /*texture = 142;
		  	  color = Color.rgb(51, 102, 0);
		  	  shape = Shape.AIR; //GROUND
		  	  if(rand > 75)
		  		  color = Color.LTGRAY;
		  	  if(rand > 85)
		  		  color = Color.rgb(128, 128, 128); //Dark grey
	  		  if(rand > 90) {
	  			  texture = 6;
	  			  shape = Shape.AIR; //SOLID
	  			  if(rand > 93)
	  				  texture = 7;
	  		  }
	  		  if(rand > 95) {
	  			  texture = 252;
	  			  shape = Shape.AIR; //GROUND
	  			  color = Color.YELLOW;
	  		  }
	  		  if(rand > 98) {
	  			  texture = 18;
	  			  shape = Shape.AIR; //GROUND
	  			  color = Color.rgb(153, 0, 0); //Dark red
	  		  }
	  		  
	  		  color = Color.BLACK; */
	  		  if(rand >= 90)
	  			  color = Color.LTGRAY;
	          
	          if(mapSize <= 1) {
		           Coordinate c = ch.getCoord(x, y, z);
		           c.getLandscape().getType().setType(type);
//		           c.getLandscape().getType().setTexture(texture);
		           c.getLandscape().getType().setColor(color);
		           c.getLandscape().setShape(shape);

	  			  break;
	          }
	  		  //When z reaches end
	          // using '>=' because if x is as big as the number of rectengulars is, it had added always one more (adds every round @ the else statement)
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
		      Coordinate c = ch.getCoord(x, y, z);
	          c.getLandscape().getType().setType(type);
//		      c.getLandscape().getType().setTexture(texture);
		      c.getLandscape().getType().setColor(color);
		      c.getLandscape().setShape(shape);
	  		  x++;
	  	  }
	  	landscapeReady = true;
	}
	
	public boolean isLandscapeReady() {
		return landscapeReady;
	}
	public void setLandscapeReady(boolean landscapeReady) {
		this.landscapeReady = landscapeReady;
	}
	

}
