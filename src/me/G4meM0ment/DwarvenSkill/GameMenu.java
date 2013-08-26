package me.G4meM0ment.DwarvenSkill;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameMenu extends Activity {

	private static LinearLayout ll;
	private static LinearLayout.LayoutParams llp;
	private static String header = "Menu";
	private static String back = "Back";
	private static String mainMenu = "Quit To Main Menu";
	private static float buttonTextSize = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupLinearLayout();
		setupMainMenu();
		setContentView(ll, llp);
	}
	
	@SuppressWarnings("deprecation")
	private void setupLinearLayout() {
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(0xff000000);
        llp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.FILL_PARENT);
	}
	private void setupMainMenu() {
		setupHeader();
		setupMainMenuButton();
		setupBackButton();
	}
	private void setupHeader() {
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
	private void setupMainMenuButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(mainMenu);
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
        		Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	private void setupBackButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(back);
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
        		Intent intent = new Intent(getApplicationContext(), Ingame.class);
        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
}
