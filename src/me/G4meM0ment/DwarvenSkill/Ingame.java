package me.G4meM0ment.DwarvenSkill;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import me.G4meM0ment.DwarvenSkill.Control.ControlHandler;
import me.G4meM0ment.DwarvenSkill.Control.ControlMode;
import me.G4meM0ment.DwarvenSkill.Control.CreationMode;
import me.G4meM0ment.DwarvenSkill.Draw.AnimateButton;
import me.G4meM0ment.DwarvenSkill.Draw.AnimateMap;
import me.G4meM0ment.DwarvenSkill.Draw.GameBoard;
import me.G4meM0ment.DwarvenSkill.Draw.IngameButton;
import me.G4meM0ment.DwarvenSkill.Draw.Menu.LeftMenu;
import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Being;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.BeingType;
import me.G4meM0ment.DwarvenSkill.IngameObject.Being.Dwarf.Dwarf;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;
import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Schedule.Schedule;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Ingame extends /*Sliding*/Activity implements OnTouchListener{
	
    private FilesHandler fh;
    private AnimateMap animateMap;
    private CoordsHandler ch;
    private ScreenHandler sh;
	private GameBoard gb;
	private Schedule sched;
	private AnimateButton animateButton;
	private LeftMenu lm;
	private ControlMode ctrlMode;
	private ControlHandler ctrlH;
	private CreationMode cMode;
	
	private float startX = 0, startY = 0, finX = 0, finY = 0;
	private boolean noMove = true;
//	private static SlidingMenu lsm;
//	private static SlidingMenu rsm;
	private MediaPlayer player;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sh = new ScreenHandler();
		fh = new FilesHandler(this);
		animateMap = new AnimateMap(this);
		ch = new CoordsHandler();
		animateButton = new AnimateButton();
		sched = new Schedule();
		lm = new LeftMenu(this);
		ctrlH = new ControlHandler();
		cMode = new CreationMode();
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupViews();

		synchronized(gb) {
			gb.setupTextureColorRects();
		}

		
//		sched.startSchedule();
		Schedule sched = new Schedule(); 
		Thread time = new Thread(sched);
		time.start();
				
        lm.setupLeftMenu();
//        setBehindContentView(lm.getLayout());
//        setupLeftSlidingMenu();
//        setupRightSlidingMenu();

        
        MainMenu.getMediaPlayer().stop();

        player = MediaPlayer.create(this, R.raw.song_game);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        player.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		gb.pause();
		sched.pause();
		animateButton.setPPState(true);
	}
	@Override
	protected void onResume() {
		super.onResume();
		gb.resume();
		sched.resume();
		animateButton.setPPState(true);
	}
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), GameMenu.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(getApplicationContext(), GameMenu.class);
		startActivity(intent);
		return true;
	}
	
	public void setBehindContentView() {
//		setBehindContentView(lm.getLayout());
	}
  
	@SuppressWarnings("deprecation")
	public void setupViews() {
		FrameLayout fl = new FrameLayout(getBaseContext());
		LayoutParams sizeP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		fl.setLayoutParams(sizeP);
		fl.setBackgroundColor(Color.rgb(0, 153, 204));
		
		gb = new GameBoard(getBaseContext());
		LayoutParams fillP = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		gb.setLayoutParams(fillP);      
        
        gb.setOnTouchListener(Ingame.this); 
        fl.addView(gb);
        setContentView(fl);	
	}
	
/*	public void setupLeftSlidingMenu() {
		lsm = getSlidingMenu();
        lsm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        lsm.setMode(SlidingMenu.LEFT);
        lsm.setMenu(lm.getLayout());
	}
	public void setupRightSlidingMenu() {
		rsm = getSlidingMenu();
        rsm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        rsm.setSecondaryMenu(lm.getLayout());
        rsm.setMode(SlidingMenu.RIGHT);
	} */

	@SuppressWarnings("static-access")
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		float distX, distY;
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = e.getX();
				startY = e.getY();
				gb.movedDist.put("startX", startX);
				gb.movedDist.put("startY", startY);
				finX = finY = -1;
				gb.movedDist.put("finX", finX);
				gb.movedDist.put("finY", finY);
				break;
			case MotionEvent.ACTION_MOVE:
				finX = e.getX();
				finY = e.getY();
				distX = startX - finX;
				distY = startY - finY;
				if(!checkUnvalidValues() && (Math.abs(distX) > sh.getTSize() || Math.abs(distY) > sh.getTSize())) {
	    			int rectDistX = (int) (distX/sh.getTSize());
	    			int rectDistY = (int) (distY/sh.getTSize());
    				noMove = false;
	    			if(!checkEdges(rectDistX, rectDistY)) {
	    				animateMap.animateXYDistance(rectDistX, rectDistY);
	    				startX = e.getX();
	    				startY = e.getY();
	    			}
				}
				break;
			case MotionEvent.ACTION_UP:
				finX = e.getX();
				finY = e.getY();
				if(checkButton() == IngameButton.PP && ctrlH.getCtrlMode() == ControlMode.MOVE) {
					animateButton.changePPState();		
					break;
				}
				if(checkButton() == IngameButton.LEFT) {
					animateButton.setLeftState(true);
					animateButton.setPPState(true);
					lm.openLeftMenu();
					lm.processMenu();
//					toggleLeftSM();
					break;
				}
				if(checkButton() == IngameButton.UP) {
					animateMap.animateZ(1);
					break;
				}
				if(checkButton() == IngameButton.DOWN) {
					animateMap.animateZ(-1);
					break;
				}
				
				if(noMove && !checkUnvalidValues()) {
					switch(ctrlH.getCtrlMode()) {
						case MOVE:
							animateMap.tellCoordinate(finX, finY);
							break;
						case CREATE:
							Being b = new Being(BeingType.DWARF);
							Dwarf d = b.getDwarf();
							cMode.setDwarfToPos(finX, finY, d);
							break;
						case DESIGNATE:
							ctrlH.processDesignatedCoord(finX, finY);
					}
				}
				noMove = true;
				break;
		}
		return true;
	}
	
	private boolean checkUnvalidValues() {
		if(startX <= sh.getSideSize()-1 || startX >= (sh.getScreenWidth()-sh.getSideSize()))
			return true;
		if(startY <= -1 || startY >= (sh.getScreenHeight()-sh.getBottomGUISize()))
			return true;
		if(finX <= sh.getSideSize()-1 || finX >= (sh.getScreenWidth()-sh.getSideSize()))
			return true;
		if(finY <= -1 || finY >= (sh.getScreenHeight()-sh.getBottomGUISize()))
			return true;
		return false;
	}
	
	private IngameButton checkButton() {
		if(startX >= ((sh.getScreenWidth()/2)-sh.getTSize()) && startX <= ((sh.getScreenWidth()/2)+sh.getTSize()) && startY >= (sh.getScreenHeight()-sh.getBottomGUISize()*2) && (startY <= (sh.getScreenHeight())) && finX >= ((sh.getScreenWidth()/2)-sh.getTSize()) && finX <= ((sh.getScreenWidth()/2)+sh.getTSize()) && finY >= (sh.getScreenHeight()-sh.getBottomGUISize()*2) && (finY <= (sh.getScreenHeight())))
			return IngameButton.PP;
		if(startX >= (0) && startX <= (sh.getTSize()*2+sh.getTSize()/2) && startY >= (sh.getScreenHeight()/2-sh.getTSize()-sh.getTSize()/2) && (startY <= (sh.getScreenHeight()/2+sh.getTSize()+sh.getTSize()/2)) && finX >= (0) && finX <= (sh.getTSize()*2+sh.getTSize()/2) && finY >= (sh.getScreenHeight()/2-sh.getTSize()-sh.getTSize()/2) && (finY <= (sh.getScreenHeight()/2+sh.getTSize()+sh.getTSize()/2)))
			return IngameButton.LEFT;
		if(startX >= (sh.getScreenWidth()-48-sh.getSideSize()) && startX <= (sh.getScreenWidth()-sh.getSideSize()) && finX >= (sh.getScreenWidth()-48-sh.getSideSize()) && finX <= (sh.getScreenWidth()-sh.getSideSize())) {
			if(startY >= sh.getScreenHeight()/2-59 && finY >= sh.getScreenHeight()/2-59 && startY <= sh.getScreenHeight()/2 && finY <= sh.getScreenHeight()/2)
				return IngameButton.UP;
			if(startY >= sh.getScreenHeight()/2 && finY >= sh.getScreenHeight()/2 && startY <= sh.getScreenHeight()/2+59 && finY <= sh.getScreenHeight()/2+59)
				return IngameButton.DOWN;
		}
		return IngameButton.NONE;
	}
	
	private boolean checkEdges(int distX, int distY) {
		if(distX > 0 && animateMap.checkRightEdgeVisible())
			return true;
		if(distX < 0 && animateMap.checkLeftEdgeVisible())
			return true;
		if(distY > 0 && animateMap.checkBottomEdgeVisible())
			return true;
		if(distY < 0 && animateMap.checkTopEdgeVisible())
			return true;
		return false;
	}
	private boolean checkPreRenderedEdges(int distX, int distY) {
		if(distX > 0 && animateMap.checkRightMovement(distX))
			return true;
		if(distX < 0 && animateMap.checkLeftMovement(distX))
			return true;
		if(distY > 0 && animateMap.checkBottomMovement(distY))
			return true;
		if(distY < 0 && animateMap.checkTopMovement(distY))
			return true;
		return false;
	}
	
/*	public SlidingMenu getLeftSM() {
		return lsm;
	}
	
	public void toggleLeftSM() {
        lsm.toggle(true);
        
        
	} */
}
