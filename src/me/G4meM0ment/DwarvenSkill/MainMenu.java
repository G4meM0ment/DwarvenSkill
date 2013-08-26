package me.G4meM0ment.DwarvenSkill;

import android.media.MediaPlayer;
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

public class MainMenu extends Activity {
	
	private static LinearLayout ll;
	private static LinearLayout.LayoutParams llp;
	private static String header = "Dwarven Skill";
	private static String newGame = "Create New World";
	private static String loadGame = "Continue Playing";
	private static String options = "Options";
	private static String developer = "Developer";
	private static String wiki = "Wiki";
	private static String df = "Dwarf Fortress Website";
	private static float buttonTextSize = 20;
	private static MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupLinearLayout();
		setupMainMenu();		
		setContentView(ll, llp);
		
        player = MediaPlayer.create(this, R.raw.song_title);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        player.start();
	}
	@Override
	public void onBackPressed(){
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public static MediaPlayer getMediaPlayer() {
		return player;
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
		setupNewGameButton();
		setupLoadGameButton();
		setupOptionsButton();
		setupDevButton();
		setupWikiButton();
		setupDFLink();
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
	private void setupNewGameButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(newGame);
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
        		Intent intent = new Intent(getApplicationContext(), NewGame.class);
        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	private void setupLoadGameButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xff888888);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(loadGame);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
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
//        		Intent intent = new Intent(getApplicationContext(), Ingame.class); //TODO load map
//        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	private void setupOptionsButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xff888888);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(options);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);	
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
//        		Intent intent = new Intent(getApplicationContext(), Wiki.class); //TODO add options
//        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	private void setupDevButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(developer);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);	
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
        		Intent intent = new Intent(getApplicationContext(), DevInfo.class);
        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	private void setupWikiButton() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xffffffff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(wiki);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);	
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
        		Intent intent = new Intent(getApplicationContext(), Wiki.class);
        		startActivity(intent);
                tv.setTextColor(0xffffffff);
            }
        });
	}
	
	private void setupDFLink() {
        final TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff000000);
        tv.setTextColor(0xff0000ff);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(buttonTextSize);
        tv.setText(df);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);	
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
                String link = "http://www.bay12games.com/dwarves/";
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link));
                startActivity(intent);
                tv.setTextColor(0xff0000ff);
            }
        });
	}
	

	
}
