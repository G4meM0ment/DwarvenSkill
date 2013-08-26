package me.G4meM0ment.DwarvenSkill;

import me.G4meM0ment.DwarvenSkill.Map.Map;
import me.G4meM0ment.DwarvenSkill.Waiter.Waiter;
import me.G4meM0ment.DwarvenSkill.Waiter.WaiterMode;
import me.G4meM0ment.DwarvenSkill.Waiter.WaiterModeHolder;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewGame extends Activity{
	
	private Map map;
	private WaiterModeHolder wMHolder;
	
	private LinearLayout ll;
	private LinearLayout.LayoutParams llp;
	private int buttonTextSize = 15;
	private String header = "Create New World";
	private String sizeHeader = "Choose parameters of your world:";
	private String smallWorld = "Small";
	private String mediumWorld = "Medium";
	private String hugeWorld = "Huge";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		map = new Map();
		wMHolder = new WaiterModeHolder();
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupParameterChooser();
	}
	
	public void setupParameterChooser() {
		setupLinearLayout();
		setupHeader();
		setupSizeHeader();
		setupSmallButton();
		setupMediumButton();
		setupHugeButton();
		setContentView(ll, llp);
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
	public void setupHeader() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(buttonTextSize);
        tv.setText(header);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
	}
	public void setupSizeHeader() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTextSize(buttonTextSize);
        tv.setText(sizeHeader);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
	}
	public void setupSmallButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(smallWorld);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
           
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                tv.setTextColor(0xffcccccc);
                Map.setMapX(50);
                Map.setMapY(50);
                Map.setMapZ(70);
                Map.setZ(Map.getMapZ()/4);
                Map.setMapSizes();
        		Intent intent = new Intent(getApplicationContext(), Waiter.class);
        		startActivity(intent);
        		wMHolder.setMode(WaiterMode.LOAD);
                tv.setTextColor(0xffffffff);
            }
        });		
	}
	public void setupMediumButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(mediumWorld);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
           
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                tv.setTextColor(0xffcccccc);
                Map.setMapX(100);
                Map.setMapY(100);
                Map.setMapZ(40);
                Map.setZ(Map.getMapZ()/4);
                Map.setMapSizes();
        		Intent intent = new Intent(getApplicationContext(), Waiter.class);
        		startActivity(intent);
        		wMHolder.setMode(WaiterMode.LOAD);
                tv.setTextColor(0xffffffff);
            }
        });		
	}
	public void setupHugeButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(hugeWorld);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        lp.gravity=1;
        tv.setLayoutParams(lp);
        ll.addView(tv);
           
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                tv.setTextColor(0xffcccccc);
                Map.setMapX(150);
                Map.setMapY(150);
                Map.setMapZ(40);
                Map.setZ(Map.getMapZ()/4);
                Map.setMapSizes();
        		Intent intent = new Intent(getApplicationContext(), Waiter.class);
        		startActivity(intent);
        		wMHolder.setMode(WaiterMode.LOAD);
                tv.setTextColor(0xffffffff);
            }
        });		
	}

}