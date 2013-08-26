package me.G4meM0ment.DwarvenSkill.Draw.Menu;

import me.G4meM0ment.DwarvenSkill.Ingame;
import me.G4meM0ment.DwarvenSkill.Control.ControlHandler;
import me.G4meM0ment.DwarvenSkill.Control.ControlMode;
import me.G4meM0ment.DwarvenSkill.Control.ControlModeViews;
import me.G4meM0ment.DwarvenSkill.Draw.AnimateButton;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeftMenu {
	
	private Context context;
	private Ingame ingame;
	private AnimateButton animateButton;
	private ControlHandler ctrlH;
	private ControlModeViews cMV;
	
	private LinearLayout ll;
	private LinearLayout.LayoutParams llp;
	private static LeftMenuPage page;
	private static int buttonTextSize = 15;
	
	public LeftMenu(Context context) {
		this.context = context;
		ingame = new Ingame();	
		animateButton = new AnimateButton();
		ctrlH = new ControlHandler();
		cMV = new ControlModeViews(this);
	}
	public LeftMenu() {
	}
	
	public void setupLeftMenu() {
		page = LeftMenuPage.OVERVIEW;
		setupLinearLayout();
		setupButtons();
	}
	public void processMenu() {
		setupButtons();
	}
	
	@SuppressWarnings("deprecation")
	private void setupLinearLayout() {
        ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(0xff000000);
        llp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.FILL_PARENT);
        ll.setLayoutParams(llp);
	}
	public LinearLayout getLayout() {
		return ll;
	}
	
	public LeftMenuPage getLeftMenuPage() {
		return page;
	}
	public void setLeftMenuPage(LeftMenuPage page) {
		LeftMenu.page = page;
	}	
	
	private void setupButtons() {
		switch(page) {
    		case OVERVIEW:		
    			ll = cMV.getOverviewLayout(ll, context);
    			break;
    		case CREATE:
    			ll = cMV.getCreateLayout(ll, context);
    			break;
    		case DESIGNATIONS:
    			ll = cMV.getDesignateLayout(ll, context);
    			break;
		}
	}
	
	private void setupButtonsOLLDDD() {
		final TextView t1 = new TextView(context);
        t1.setBackgroundColor(0xff000000);
        t1.setTextColor(0xffffffff);
        t1.setTypeface(null, Typeface.NORMAL);
        t1.setTextSize(buttonTextSize);
//        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        t1.setLayoutParams(lp);
        
        String content = null;
		if(page == LeftMenuPage.OVERVIEW) {
			content = "Creation Mode";
		}
		else {
			content = "Back";
		}
		t1.setText(content);
		ll.addView(t1);
		
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setTextColor(0xffcccccc);
                switch(page) {
                	case OVERVIEW:		
                		page = LeftMenuPage.CREATE;
                		t1.setText("Create Dwarves");        		
                		break;
                	case CREATE:
//                		ingame.toggleLeftSM();
                		break;
                }
                t1.setTextColor(0xffffffff);
            }
        });
	}
	
	public void openLeftMenu() {
		page = LeftMenuPage.OVERVIEW;
	}
	
	public void waitForAnimation() {
        Thread splash = new Thread(new Runnable() {

			@Override
            public void run() {
            	try {
					Thread.currentThread();
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		animateButton.setLeftState(false);
            }
        });
        splash.start();
	}

}
