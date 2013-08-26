package me.G4meM0ment.DwarvenSkill.Control;

import me.G4meM0ment.DwarvenSkill.Ingame;
import me.G4meM0ment.DwarvenSkill.Draw.Menu.LeftMenu;
import me.G4meM0ment.DwarvenSkill.Draw.Menu.LeftMenuPage;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ControlModeViews {

	private LeftMenu lm;
	private ControlHandler ctrlH;
	private Ingame ingame;
	
	private int buttonTextSize = 18;
	
	public ControlModeViews(LeftMenu lm) {
		ingame = new Ingame();
		this.lm = lm;
		ctrlH = new ControlHandler();
	}
	
	public LinearLayout getOverviewLayout(LinearLayout ll, Context context) {
		ll.removeAllViews();
		String tt1 = "Move";
		String tt2 = "Designation";
		String tt3 = "Creation";		
		String ttb = "Back";
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
		
		final TextView t1 = new TextView(context);
        t1.setBackgroundColor(0xff000000);
        t1.setTextColor(0xffffffff);
        t1.setTypeface(null, Typeface.NORMAL);
        t1.setTextSize(buttonTextSize);
        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.setLayoutParams(lp);
        t1.setText(tt1);
        ll.addView(t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ctrlH.setCtrlMode(ControlMode.MOVE);
//            	ingame.toggleLeftSM();
            	lm.waitForAnimation();
            }
        });
        
		final TextView t2 = new TextView(context);
        t2.setBackgroundColor(0xff000000);
        t2.setTextColor(0xffffffff);
        t2.setTypeface(null, Typeface.NORMAL);
        t2.setTextSize(buttonTextSize);
        t2.setGravity(Gravity.CENTER_HORIZONTAL);
        t2.setLayoutParams(lp);
        t2.setText(tt2);
        ll.addView(t2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	lm.setLeftMenuPage(LeftMenuPage.DESIGNATIONS);
            	lm.processMenu();
            }
        });
        
		final TextView t3 = new TextView(context);
        t3.setBackgroundColor(0xff000000);
        t3.setTextColor(0xffffffff);
        t3.setTypeface(null, Typeface.NORMAL);
        t3.setTextSize(buttonTextSize);
        t3.setGravity(Gravity.CENTER_HORIZONTAL);
        t3.setLayoutParams(lp);
        t3.setText(tt3);
        ll.addView(t3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	lm.setLeftMenuPage(LeftMenuPage.CREATE);
            	lm.processMenu();
            }
        });
        
		final TextView tb = new TextView(context);
        tb.setBackgroundColor(0xff000000);
        tb.setTextColor(0xffffffff);
        tb.setTypeface(null, Typeface.NORMAL);
        tb.setTextSize(buttonTextSize);
        tb.setGravity(Gravity.CENTER_HORIZONTAL);
        tb.setLayoutParams(lp);
        tb.setText(ttb);
        ll.addView(tb);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            	ingame.toggleLeftSM();
            	lm.waitForAnimation();
            }
        });
		
		return ll;
	}
	
	
	public LinearLayout getCreateLayout(LinearLayout ll, Context context) {
		ll.removeAllViews();
		String tt1 = "Create Dwarf";		
		String ttb = "Back";
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
		
		final TextView t1 = new TextView(context);
        t1.setBackgroundColor(0xff000000);
        t1.setTextColor(0xffffffff);
        t1.setTypeface(null, Typeface.NORMAL);
        t1.setTextSize(buttonTextSize);
        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.setLayoutParams(lp);
        t1.setText(tt1);
        ll.addView(t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ctrlH.setCtrlMode(ControlMode.CREATE);
//            	ingame.toggleLeftSM();
            	lm.waitForAnimation();
            }
        });
        
		final TextView tb = new TextView(context);
        tb.setBackgroundColor(0xff000000);
        tb.setTextColor(0xffffffff);
        tb.setTypeface(null, Typeface.NORMAL);
        tb.setTextSize(buttonTextSize);
        tb.setGravity(Gravity.CENTER_HORIZONTAL);
        tb.setLayoutParams(lp);
        tb.setText(ttb);
        ll.addView(tb);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	lm.setLeftMenuPage(LeftMenuPage.OVERVIEW);
            	lm.processMenu();
            }
        });
		
		return ll;
	}
	
	public LinearLayout getDesignateLayout(LinearLayout ll, Context context) {
		ll.removeAllViews();
		String tt1 = "Designate";	
		String ttb = "Back";
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
		
		final TextView t1 = new TextView(context);
        t1.setBackgroundColor(0xff000000);
        t1.setTextColor(0xffffffff);
        t1.setTypeface(null, Typeface.NORMAL);
        t1.setTextSize(buttonTextSize);
        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.setLayoutParams(lp);
        t1.setText(tt1);
        ll.addView(t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ctrlH.setCtrlMode(ControlMode.DESIGNATE);
//            	ingame.toggleLeftSM();
            	lm.waitForAnimation();
            }
        });
        
		final TextView tb = new TextView(context);
        tb.setBackgroundColor(0xff000000);
        tb.setTextColor(0xffffffff);
        tb.setTypeface(null, Typeface.NORMAL);
        tb.setTextSize(buttonTextSize);
        tb.setGravity(Gravity.CENTER_HORIZONTAL);
        tb.setLayoutParams(lp);
        tb.setText(ttb);
        ll.addView(tb);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	lm.setLeftMenuPage(LeftMenuPage.OVERVIEW);
            	lm.processMenu();
            }
        });
		
		return ll;
	}
	
}
