package me.G4meM0ment.DwarvenSkill.Handler;

import me.G4meM0ment.DwarvenSkill.Map.Map;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ScreenHandler {
	
	private Context context;
	private static Map map;
	private static int sideSize = 16;
	private static int tSize = 48;
	private static int bottomGUISize = 48;
	private static int screenRects;
	private static int drawedHeight, drawedWidth, screenWidth, screenHeight;
	private static final String tag = "ScreenHandler";
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public ScreenHandler(Context context) {
		this.context = context;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		map = new Map();
		drawedHeight = screenHeight = display.getHeight();
		drawedWidth = screenWidth = display.getWidth();
		
        if(map.getMap2DSize() < (((screenWidth-sideSize*2)/tSize))*(screenHeight/tSize)) {
        	screenRects = map.getMap2DSize();
        	drawedWidth = map.getMapX()*tSize;
        	drawedHeight = map.getMapY()*tSize;
        }
        else
        	screenRects = ((screenHeight)/tSize)*((screenWidth-sideSize*2)/tSize);
	}
	public ScreenHandler() {
		
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	
	@SuppressWarnings("static-access")
	public int getDrawedWidth() {
        if(map.getMapX()*tSize < (screenWidth-sideSize*2))
        	drawedWidth = (int) ((screenWidth/2)+((map.getMapX()*tSize)/2));
        return drawedWidth;
	}
	@SuppressWarnings("static-access")
	public int getDrawedHeight() {
        if(map.getMapY()*tSize < screenHeight)
        	drawedHeight = (int) ((screenHeight/2)+((map.getMapY()*tSize)/2));
        return drawedHeight;
	}
	
	public int getSideSize() {
		return sideSize;
	}
	public int getTSize() {
		return tSize;
	}
	public int getBottomGUISize() {
		return bottomGUISize;
	}
	public int getScreenRects() {
		return screenRects;
	}

}
