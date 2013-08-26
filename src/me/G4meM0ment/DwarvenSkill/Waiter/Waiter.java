package me.G4meM0ment.DwarvenSkill.Waiter;

import me.G4meM0ment.DwarvenSkill.Ingame;
import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Landscape.LandscapeHandler;
import me.G4meM0ment.DwarvenSkill.Waiter.Waiter;
import me.G4meM0ment.DwarvenSkill.WorldGenerator.WorldGenerator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Waiter extends Activity{
	
	private FilesHandler fh;
	private WaiterModeHolder wMHolder;
	private CoordsHandler ch;
	private LandscapeHandler lh;
	private WorldGenerator wg;
	
	private LinearLayout ll;
	private LinearLayout.LayoutParams llp;
	private int buttonTextSize = 15;
	private String mainContent = "Loading"; //DEAFULT WAITING, ONLY WORKAROUND
	private String defaultMainContent = "Waiting";
	private String animator = ".";
	private static boolean waiting = false;
	private static boolean loading = false;
	private static final String tag = "Waiter";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fh = new FilesHandler(this);
		wMHolder = new WaiterModeHolder();
		ch = new CoordsHandler();
		lh = new LandscapeHandler();
		wg = new WorldGenerator();
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupLinearLayout();
		setupWaiterContent();
		setContentView(ll, llp);
		
		initWaiter();
		
/*		Handler h = new Handler();
        Thread splash = new Thread(new Runnable() {
			@Override
            public void run() {
				initWaiter();
            }
        });
        splash.start();
        h.postDelayed(splash, 3000); */

	}
	@Override
	public void onBackPressed(){
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void initWaiter() {
		WaiterMode mode = wMHolder.getMode();
		if(mode == WaiterMode.LOAD) {
			mainContent = defaultMainContent = "Loading";
			waiting = true;
			loading = true;
			waitForLoad();
			while(loading) {Log.v(tag, "Loading");}
			waiting = false;
    		Intent intent = new Intent(getApplicationContext(), Ingame.class);
    		startActivity(intent);
		}
		if(mode == WaiterMode.SAVE) {
			mainContent = defaultMainContent = "Saving";
			waiting = true;
		}
	}
	
	private void setupWaiterContent() {
		setupMainContent();
		animateMainContent();
	}
	@SuppressWarnings("deprecation")
	public void setupLinearLayout() {
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(0xff000000);
        llp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.FILL_PARENT);
	}
	@SuppressWarnings("deprecation")
	public void setupMainContent() {
	    TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(buttonTextSize);
        tv.setText(mainContent);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
	}
	
	public void animateMainContent() {
        Thread splash = new Thread(new Runnable() {

            @SuppressWarnings("static-access")
			@Override
            public void run() {
            	int i = 0;
            	while(waiting) {
            		try {
            			Thread.currentThread().sleep(500);
            			if(i >= 3) {
            				mainContent = defaultMainContent;
            				i = 0;
            				continue;
            			}
            			mainContent = mainContent+animator;
            			i++;
            		} catch (InterruptedException e) {
            			e.printStackTrace();
            		}
            	}

            }
        });
        splash.start();
	}
	
	public void waitForLoad() {
		
		loading = true;
		
		ch.setupCoordinates();
		while(!ch.isReady()) {Log.v(tag, "Wating for Coords");}
		Log.v(tag, "Coords Finished!");

		lh.setupLandscape();
		
		while(!lh.isLandscapeReady()) {Log.v(tag, "Wating for Landscape");}
		Log.v(tag, "Landscape Finished!");
		
		wg.convert();
		
		while(!wg.isConverted()) {Log.v(tag, "Wating for Converter");}
		Log.v(tag, "Converter Finished!");
		
		wg.regenerateLandscape();
		
		while(!wg.isGenerated()) {Log.v(tag, "Wating for Generator");}
		Log.v(tag, "Generator Finished!");
		
		new ScreenHandler(this);
		
		ch.setupMiddleScreenCoord();
		
		ch.setupScreenCoords();
		
		while(!ch.isVisReady())  {Log.v(tag, "Wating for Visisble Coords");}
		Log.v(tag, "Visible Coords Finished!");
		
    	loading = false;
		
		
		
		
/*        Thread splash = new Thread(new Runnable() {

			@SuppressWarnings("static-access")
			@Override
            public void run() {
				loading = true;
            	
				ch.setupCoordinates();
				
				while(!ch.isReady()) {
					try {
						Thread.currentThread().sleep(100);
					} catch(InterruptedException e) {
					e.printStackTrace();
					}
				}

				lh.setupLandscape();
				
				ch.setupMiddleScreenCoord();
				
				ch.setupScreenCoords();
				
				while(!ch.isVisReady()) {
					try {
						Thread.currentThread().sleep(100);
					} catch(InterruptedException e) {
					e.printStackTrace();
					}
				}
				
            	loading = false;
            }
        });
        splash.start();   */ 
	}	
}
