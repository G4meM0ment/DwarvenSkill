package me.G4meM0ment.DwarvenSkill;

import me.G4meM0ment.DwarvenSkill.Handler.FilesHandler;
import me.G4meM0ment.DwarvenSkill.Handler.ScreenHandler;
import me.G4meM0ment.DwarvenSkill.Map.CoordsHandler;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
	
	private static final String tag = "Main";
	private FilesHandler fh;
	private View v;
	private CoordsHandler ch;
	private ScreenHandler sh;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	//Constructors
		super.onCreate(savedInstanceState);
		this.fh = new FilesHandler(getBaseContext());
		this.ch = new CoordsHandler();
		this.sh = new ScreenHandler(this);
		
		//Screen
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setupStartPicture();	
		Log.v(tag,"Main menu");
	}
    
    @SuppressWarnings("deprecation")
	private void setupStartPicture() {
    	LinearLayout ll = new LinearLayout(this);
        
    	ll.setOrientation(LinearLayout.VERTICAL);
    	ll.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.FILL_PARENT);
        
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.n_start_pic);
        iv.setVisibility(View.VISIBLE);
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        float yPos = (float) (sh.getScreenHeight()/2-/*iv.getHeight()*/298/2); //TODO read size from image
        iv.setY(yPos);
        
        ll.addView(iv, lp);
        setContentView(ll, llp);
        
        Thread splash = new Thread(new Runnable() {

            @SuppressWarnings("static-access")
			@Override
            public void run() {
                try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                Intent intent = new Intent(getBaseContext(), MainMenu.class);
                startActivity(intent);
            }
        });
        splash.start();
    }
}
