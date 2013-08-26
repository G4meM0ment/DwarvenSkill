package me.G4meM0ment.DwarvenSkill;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DevInfo extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//Create layout, views etc
		LayoutParams sizeP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LayoutParams centerP = new LayoutParams(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.CENTER_VERTICAL);
		
        RelativeLayout rr = new RelativeLayout(this);
        rr.setBackgroundColor(Color.BLACK);
        rr.setLayoutParams(sizeP);
        rr.setLayoutParams(centerP);
        
        TextView c = new TextView(this);
        c.setTextColor(Color.WHITE);
        c.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        c.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        c.setGravity(Gravity.CENTER);
        c.setText(Html.fromHtml(getString(R.string.devInfo_content)));
        rr.addView(c);

        setContentView(rr);		
        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        		Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        		startActivity(intent);
            }
        });
	}
}
