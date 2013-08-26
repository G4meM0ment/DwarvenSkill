package me.G4meM0ment.DwarvenSkill.Draw;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.G4meM0ment.DwarvenSkill.R;
import me.G4meM0ment.DwarvenSkill.Control.ControlHandler;
import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Handler.TextureHandler;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Being;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.BeingHandler;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.Dwarf;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.DwarfHandler;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Coordinates.Coordinate;
import me.G4meM0ment.DwarvenSkill.Schedule.Schedule;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameBoard extends SurfaceView implements Runnable{
	
		private static final String tag = "GameBoard";
		
        private Paint p;
        private FilesHandler fh;
        private CoordsHandler ch;
        private ScreenHandler sh;
    	private SurfaceHolder holder;
    	private AnimateMap animateMap;
    	private AnimateButton animateButton;
    	private TextureHandler th;
    	private Schedule sched;
    	private ControlHandler ctrlH;
    	private BeingHandler bh;
    	private DwarfHandler dh;
    	
        private Handler frame = new Handler();
        private static final int FRAME_RATE = 50; //50 frames per second
        private static int FRAME_COUNTER = 0;
    	// maximum number of frames to be skipped
    	private final static int MAX_FRAME_SKIPS = 5;
    	private static final int FRAME_PERIOD = 1000 / FRAME_RATE;
    	private Thread thread = null;
    	private boolean isRunning = false;
    	public static HashMap<String, Float> movedDist = new HashMap<String, Float>();
    	private float startX, startY, finX, finY;
    	private boolean textureRectsSetup = false;
    	private List<ShapeDrawable> textureColorRects = new ArrayList<ShapeDrawable>();
    	private static final String splitter = "_";
    	
    	// Stuff for stats */
        private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    	// we'll be reading the stats every second
    	private final static int 	STAT_INTERVAL = 1000; //ms
    	// the average will be calculated by storing
    	// the last n FPSs
    	private final static int	FPS_HISTORY_NR = 10;
    	// last time the status was stored
    	private long lastStatusStore = 0;
    	// the status time counter
    	private long statusIntervalTimer	= 0l;
    	// number of frames skipped since the game started
    	private long totalFramesSkipped			= 0l;
    	// number of frames skipped in a store cycle (1 sec)
    	private long framesSkippedPerStatCycle 	= 0l;

    	// number of rendered frames in an interval
    	private int frameCountPerStatCycle = 0;
    	private long totalFrameCount = 0l;
    	// the last FPS values
    	private double 	fpsStore[];
    	// the number of times the stat has been read
    	private long 	statsCount = 0;
    	// the average FPS since the game started
    	private double 	averageFps = 0.0;
        
        public GameBoard(Context context) {
                super(context);
                //it's best not to create any new objects in the on draw
                //initialize them as class variables here
                p = new Paint();
                fh = new FilesHandler(context);
                ch = new CoordsHandler();
                sh = new ScreenHandler();
                animateMap = new AnimateMap(context);
                animateButton = new AnimateButton();
                th = new TextureHandler(getResources());
                sched = new Schedule();
                ctrlH = new ControlHandler();
                dh = new DwarfHandler();
                bh = new BeingHandler();
                
                holder = getHolder();
//        		Handler h = new Handler();
//        		h.postDelayed(thread, 1000);
				movedDist.put("startX", (float) 0);
				movedDist.put("startY", (float) 0);
				movedDist.put("finX", (float) 0);
				movedDist.put("finY", (float) 0);
         }
        
    	public void pause() {
    		isRunning = false;
    		while(true) {
    			try {
    				thread.join();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			break;
    		}
    		thread = null;
    	}
    	public void resume() {
    		isRunning = true;
    		thread = new Thread(this);
    		thread.start();
//    		frame.removeCallbacks(thread);
//    	    frame.postDelayed(thread, FRAME_RATE);
    	}

		@SuppressWarnings("static-access")
		@Override
    	public void run() {
			initTimingElements();
    	    frame.removeCallbacks(thread);

    		long beginTime;		// the time when the cycle begun
    		long timeDiff;		// the time it took for the cycle to execute
    		int sleepTime;		// ms to sleep (<0 if we're behind)
    		int framesSkipped;	// number of frames being skipped 

    	    
    		while(isRunning) {
    			if(animateButton.getLeftState())
    				continue;
    			if(!holder.getSurface().isValid())
    				continue;
			
    			Canvas canvas = holder.lockCanvas();
    			beginTime = System.currentTimeMillis();
			
    			p.setColor(Color.BLACK);
			
    			preProcessRects();
    			List<Coordinate> vC = ch.getVisibleCoords();
    			synchronized(vC) {
    				if(textureRectsSetup) {
    					int i = 0;
    					for(Coordinate c : vC) {
    						if(i > textureColorRects.size()-1)
    							continue;
    						ShapeDrawable sD = textureColorRects.get(i);
    						Paint color = new Paint();
    						color.setColor(c.getbRect().getColor()); 
    						color.setStyle(Paint.Style.FILL);
    						sD.getPaint().set(color);
    						sD.draw(canvas);
    						i++;
    					}

					List<Coordinate> visible = ch.getVisibleCoords();
					if(visible != null) {
						for(Coordinate dC : visible) {
							drawLandscape(dC, canvas);
						}
					}
						drawItems(canvas);
						drawBeings(canvas);
						drawGUI(canvas);
    				}
    			}	
			
    			if(true) {
    				String date = sched.getCurrentDate();
    				int year = Integer.parseInt(date.split(splitter)[0]);
    				int month = Integer.parseInt(date.split(splitter)[1]);
    				int week = Integer.parseInt(date.split(splitter)[2]);
    				int day = Integer.parseInt(date.split(splitter)[3]);
    				int hour = Integer.parseInt(date.split(splitter)[4]);
				
    				Paint paint = new Paint();
    				paint.setStyle(Paint.Style.FILL);
					paint.setAntiAlias(true);
					paint.setTextSize(16);
					paint.setColor(Color.WHITE);
					canvas.drawText("Year: "+year+", month: "+month+", week: "+week+", day: "+day+", hour: "+hour, 16, 462, paint);
				
					String sMode = ctrlH.getCtrlMode().toString().toLowerCase(new Locale(ctrlH.getCtrlMode().toString()));
					canvas.drawText("Mode: "+sMode, sh.getScreenWidth()-208, 462, paint);
					canvas.drawText("FPS "+averageFps, 32, 32, paint);
    			}
			
    			framesSkipped = 0;
    			timeDiff = System.currentTimeMillis() - beginTime;
    			sleepTime = (int)(FRAME_PERIOD - timeDiff);

    			if (sleepTime > 0) {
    				try {
    					Thread.currentThread().sleep(sleepTime);
    				} catch (InterruptedException e) {}
    			}

    			while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
    				// we need to catch up
    				//this.gamePanel.update(); // update without rendering
    				sleepTime += FRAME_PERIOD;	// add frame period to check if in next frame
    				framesSkipped++;
    			}
    			framesSkippedPerStatCycle += framesSkipped;
    			// calling the routine to store the gathered statistics
    			storeStats();
			
    			holder.unlockCanvasAndPost(canvas);
    		}
    		
    	}
          
        private void drawLandscape(Coordinate c, Canvas canvas) {
        	int texture;
        	if(c.isDiscovered())
        		texture = c.getLandscape().getType().getTexture();
        	else
        		texture = c.getLandscape().getType().getFoggedTexture();
        	Bitmap b = th.getTextures()[texture];
        	canvas.drawBitmap(b, c.getxS(), c.getyS(), p);
        }
          
        private void drawItems(Canvas canvas) {
        	
        }
          
        private void drawBeings(Canvas canvas) {
        	List<Being> beings = bh.getAllBeings();
        	synchronized(beings) {
        		for(Being b : beings) {
        			Dwarf d = b.getDwarf();
        			Coordinate c = ch.getCoord(d.getX(), d.getY(), d.getZ());
        		
        			Bitmap bitmap = th.getTextures()[d.getTexture()];
          	  		if(c.getxS() != -1 && c.getyS() != -1)
          	  			canvas.drawBitmap(bitmap, c.getxS(), c.getyS(), p);     		
        		}
        	}
        }
          
        private void drawGUI(Canvas canvas) {
            Bitmap side=BitmapFactory.decodeResource(getResources(), R.drawable.n_side);
            Bitmap leftArrow=BitmapFactory.decodeResource(getResources(), R.drawable.n_reverse_arrow_left);
            Bitmap bottom = null;
            Bitmap upArrow=BitmapFactory.decodeResource(getResources(), R.drawable.n_arrow_up);
            Bitmap downArrow=BitmapFactory.decodeResource(getResources(), R.drawable.n_arrow_down);
            if(animateButton.getPPState())
            	bottom=BitmapFactory.decodeResource(getResources(), R.drawable.n_pause);
            else
            	bottom=BitmapFactory.decodeResource(getResources(), R.drawable.n_play);
        	canvas.drawBitmap(side, 0, 0, p);
        	canvas.drawBitmap(side, 784, 0, p);
        	canvas.drawBitmap(bottom, -45, 384, p);
            canvas.drawBitmap(leftArrow, 0, sh.getScreenHeight()/2-108/2, p);
        	canvas.drawBitmap(upArrow, sh.getScreenWidth()-48-sh.getSideSize(), sh.getScreenHeight()/2-59, p);
        	canvas.drawBitmap(downArrow, sh.getScreenWidth()-48-sh.getSideSize(), sh.getScreenHeight()/2, p);
        } 
        
        public void setupTextureColorRects() {
			List<Coordinate> vC = ch.getVisibleCoords();
			synchronized(vC) {
				for(Coordinate c : vC) {
					ShapeDrawable sD = new ShapeDrawable();
					int xS = c.getxS();
					int yS = c.getyS();
					Rect r = new Rect(xS, yS, xS+sh.getTSize(), yS+sh.getTSize());
					sD.setBounds(r);
					textureColorRects.add(sD);
				}
				textureRectsSetup = true;
			}
        }
        
        private void preProcessRects() {
			List<Coordinate> vC = ch.getVisibleCoords();
      	  		for(Coordinate c : vC) {
      	  			if(c.isDiscovered())
      	  				c.getbRect().setColor(c.getLandscape().getType().getColor());
      	  			else
      	  				c.getbRect().setColor(c.getLandscape().getType().getFoggedColor());
/*      	  	List<Being> beings = bh.getAllBeings();
      	  		for(Being b : beings) {
      	  			Dwarf d = b.getDwarf();
      	  			Coordinate c = ch.getCoord(d.getX(), d.getY(), d.getZ());
        			c.getbRect().setColor(d.getColor());  		
      	  		} */
			}
        }
        
 	private void storeStats() {
 		frameCountPerStatCycle++;
 		totalFrameCount++;

 		// check the actual time
 		statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

 		if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
 			// calculate the actual frames pers status check interval
 			double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));

 			//stores the latest fps in the array
 			fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;

 			// increase the number of times statistics was calculated
 			statsCount++;

 			double totalFps = 0.0;
 			// sum up the stored fps values
 			for (int i = 0; i < FPS_HISTORY_NR; i++) {
 				totalFps += fpsStore[i];
 			}

 			// obtain the average
 			if (statsCount < FPS_HISTORY_NR) {
 				// in case of the first 10 triggers
 				averageFps = totalFps / statsCount;
 			} else {
 				averageFps = totalFps / FPS_HISTORY_NR;
 			}
 			// saving the number of total frames skipped
 			totalFramesSkipped += framesSkippedPerStatCycle;
 			// resetting the counters after a status record (1 sec)
 			framesSkippedPerStatCycle = 0;
 			statusIntervalTimer = 0;
 			frameCountPerStatCycle = 0;

 			statusIntervalTimer = System.currentTimeMillis();
 			lastStatusStore = statusIntervalTimer;
 		}
 	}
	private void initTimingElements() {
		// initialise timing elements
		fpsStore = new double[FPS_HISTORY_NR];
		for (int i = 0; i < FPS_HISTORY_NR; i++) {
			fpsStore[i] = 0.0;
		}
	}
}
