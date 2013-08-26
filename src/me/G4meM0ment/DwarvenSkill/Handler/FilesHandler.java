package me.G4meM0ment.DwarvenSkill.Handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import me.G4meM0ment.DwarvenSkill.Draw.BackgroundRect;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Landscape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Shape;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.Type;
import me.G4meM0ment.DwarvenSkill.Schedule.Schedule;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class FilesHandler {
	
	private Context context;
	private CoordsHandler ch;
	private Map map;
	private ScreenHandler sh;
	private Schedule sched;
	
// TODO load these infos from files and make the textures fit with phone screen size
	private static final String splitter = "_";
	private static final String screenCoordFinder = "ScreenCoord_";
	private static final String dateFinder = "Date_";
	
	private static final String coordsFileName ="coordsData.txt";
	private static final String gameFileName = "gameData.txt";
	private static final String landscapeFileName = "landscapeData.txt";
	private static final String tag = "Load";	
	
	public FilesHandler (Context context) {
		this.context = context;
		ch = new CoordsHandler();
		map = new Map();
		sh = new ScreenHandler();
		sched = new Schedule();
	}
	public FilesHandler() {
		
	}
	
	@SuppressWarnings("static-access")
	public void saveScreenCoordData() {
		try {             
			FileOutputStream fOut = context.openFileOutput(gameFileName, context.MODE_PRIVATE);
		    OutputStreamWriter osw = new OutputStreamWriter(fOut);
		    
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
		    Coordinate middle = ch.getCoord(x, y, z);
		    ch.setScreenMiddleCoordinate(middle);
	        middle.setxS(xS);
	        middle.setyS(yS);
	        
			osw.write(screenCoordFinder+splitter+x+splitter+y+splitter+z
  					 +System.getProperty("line.separator"));
		    osw.flush();
		    osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public Coordinate loadScreenCoord() {
		Coordinate middle = ch.getScreenMiddleCoordinate();
	    try {
	        FileInputStream fis = context.openFileInput(gameFileName);
	        InputStreamReader inputStreamReader = new InputStreamReader(fis);
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String line = bufferedReader.readLine();
	        
	        int x = 1, y = 1, z = map.getZ();
	        int xS = ((sh.getScreenWidth()/2));
	        int yS = ((sh.getScreenHeight()/2));
	        int middleX = (int)(map.getMapX()/2+0.5);
	        int middleY = (int)(map.getMapY()/2+0.5);
	        
	        while (line != null) {
	        	if(line.startsWith(screenCoordFinder)) {
	 	           x = Integer.parseInt(line.split(splitter)[1]);
		           y = Integer.parseInt(line.split(splitter)[2]);
		           z = Integer.parseInt(line.split(splitter)[3]);
				   fis.close();
				   break;
	        	}
	        	else {
	    		    x = middleX;
	    		    y = middleY;
	        	}
		        line = bufferedReader.readLine();
	        }
	        middle = ch.getCoord(x, y, z);
	        ch.setScreenMiddleCoordinate(middle);
	        middle.setxS(xS);
	        middle.setyS(yS);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return middle;
	}
	
	@SuppressWarnings("static-access")
	public void saveCoordData() {
		try {             
		       FileOutputStream fOut = context.openFileOutput(coordsFileName, context.MODE_PRIVATE);
		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

		       // Write the string to the file
		   	  int y = 1;
		  	  int x = 1;
		  	  int z = 1;
		  	  int xS = -1;
		  	  int yS = -1;
		  	  int rectX = map.getMapX();
		  	  int rectY = map.getMapY();
		  	  int rectZ = map.getMapZ();
		  	  int mapSize = map.getMapSize();
		  	  
		  	  for(int i = 0; i <= mapSize; i++) {
		          
		          if(mapSize <= 1) {
		  			  osw.write(x+splitter+y+splitter+z+splitter+xS+splitter+yS
		  					  +System.getProperty("line.separator"));
		  			  break;
		          }
		  		  //When z reaches end
		          // using '>=' because if x is as big as the number of rectengulars is, it had added always one more (adds every round @ the else statement)
		  		  if(z > rectZ) break;
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
		  		  //fills x-line
		  		  else {
		  			  osw.write(x+splitter+y+splitter+z+splitter+xS+splitter+yS
		  					  +System.getProperty("line.separator"));
		  			  x++;
		  		  }
		  	  }
		  	  
		      osw.flush();
		      osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public List<Coordinate> loadCoordData() {
		int x = 1;
		int y = 1;
		int z = 1;
		int xS = -1;
		int yS = -1;
		
		Landscape ls;
		List<Coordinate> allCoords = new ArrayList<Coordinate>();
		
	    try {
	        FileInputStream fis = context.openFileInput(coordsFileName);
	        InputStreamReader inputStreamReader = new InputStreamReader(fis);
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String line = bufferedReader.readLine();

	        while (line != null) {
	    	   BackgroundRect bRect = new BackgroundRect();
	   		   Landscape l = new Landscape();
	           x = Integer.parseInt(line.split(splitter)[0]);
	           y = Integer.parseInt(line.split(splitter)[1]);
	           z = Integer.parseInt(line.split(splitter)[2]);
	           xS = Integer.parseInt(line.split(splitter)[3]);
	           yS = Integer.parseInt(line.split(splitter)[4]);
	           
	           Coordinate c = new Coordinate(x, y, z, xS, yS, l, bRect);
	           
	           allCoords.add(c);
	           line = bufferedReader.readLine();
	        }
	        fis.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return allCoords;
	}
	
	@SuppressWarnings("static-access")
	public void saveLandscapeData() {
		try {             
		       FileOutputStream fOut = context.openFileOutput(landscapeFileName, context.MODE_PRIVATE);
		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

		       // Write the string to the file
		   	  int y = 1;
		  	  int x = 1;
		  	  int z = 1;
		  	  int rectX = map.getMapX();
		  	  int rectY = map.getMapY();
		  	  int rectZ = map.getMapZ();
		  	  int mapSize = map.getMapSize();
		  	  int texture = 252;
		  	  int color = Color.rgb(76, 153, 0); //Dark Green
		  	  String shape = "AIR";
		  	  
		  	  for(int i = 0; i <= mapSize; i++) {
		  		  
		  		  //Just for some testing
		  		  int rand = (int)(Math.random()*100);
			  	  texture = 142;
			  	  color = Color.rgb(51, 102, 0);
			  	  shape = "AIR"; //GROUND
			  	  if(rand > 75)
			  		  color = Color.LTGRAY;
			  	  if(rand > 85)
			  		  color = Color.rgb(128, 128, 128); //Dark grey
		  		  if(rand > 90) {
		  			  texture = 6;
		  			  shape = "AIR"; //SOLID
		  			  if(rand > 93)
		  				  texture = 7;
		  		  }
		  		  if(rand > 95) {
		  			  texture = 252;
		  			  shape = "AIR"; //GROUND
		  			  color = Color.YELLOW;
		  		  }
		  		  if(rand > 98) {
		  			  texture = 18;
		  			  shape = "AIR"; //GROUND
		  			  color = Color.rgb(153, 0, 0); //Dark red
		  		  }
		          
		          if(mapSize <= 1) {
		  			  osw.write(x+splitter+y+splitter+z+splitter+texture+splitter+color+splitter+shape
		  					  +System.getProperty("line.separator"));
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
		  		  osw.write(x+splitter+y+splitter+z+splitter+texture+splitter+color+splitter+shape
		  				  +System.getProperty("line.separator"));
		  		  x++;
		  	  }
		  	  
		      osw.flush();
		      osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadLandscapeData() {
		int x = 1;
		int y = 1;
		int z = 1;
		int texture = 0;
		int color = 0;
		String sShape = "SOLID";	
		Shape shape = Shape.SOLID;
		
	    try {
	        FileInputStream fis = context.openFileInput(landscapeFileName);
	        InputStreamReader inputStreamReader = new InputStreamReader(fis);
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String line = bufferedReader.readLine();

	        while (line != null) {
	        	try {
	           x = Integer.parseInt(line.split(splitter)[0]);
	           y = Integer.parseInt(line.split(splitter)[1]);
	           z = Integer.parseInt(line.split(splitter)[2]);
	           texture = Integer.parseInt(line.split(splitter)[3]);
	           color = Integer.parseInt(line.split(splitter)[4]);
	           sShape = (line.split(splitter)[5]);
	           shape = Shape.valueOf(sShape);
	        	} catch(ArrayIndexOutOfBoundsException e) {
	        		e.printStackTrace();
	        	}
	           
	           Coordinate c = ch.getCoord(x, y, z);
	           c.getLandscape().getType().setTexture(texture);
	           c.getLandscape().getType().setColor(color);
	           c.getLandscape().setShape(shape);
	           
	           line = bufferedReader.readLine();
	        }
	        fis.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public String loadSchedule() {
		String date = null;
	    try {
	        FileInputStream fis = context.openFileInput(gameFileName);
	        InputStreamReader inputStreamReader = new InputStreamReader(fis);
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String line = bufferedReader.readLine();
	        	        
	        while (line != null) {
	        	if(line.startsWith(dateFinder)) {
	 	           date = line.split(splitter)[1];
				   fis.close();
				   break;
	        	}
	        	else {
	    		    date = null;
	        	}
		        line = bufferedReader.readLine();
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return date;
	}
	
	@SuppressWarnings("static-access")
	public void saveDate() {
		try {             
			FileOutputStream fOut = context.openFileOutput(gameFileName, context.MODE_PRIVATE);
		    OutputStreamWriter osw = new OutputStreamWriter(fOut);
	        
			osw.write(dateFinder+splitter+sched.getCurrentDate()
  					 +System.getProperty("line.separator"));
		    osw.flush();
		    osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void transferCoords() {
		List<Coordinate> aC = loadCoordData();
		Log.v(tag, Integer.toString(aC.size()));
		ch.setAllCoordinates(aC);
	}

}
