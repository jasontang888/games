package br.ita.tsp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	
	private TextView tv, tv1, tv2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);        
        
        tv = (TextView) findViewById(R.id.mode);
        tv1 = (TextView) findViewById(R.id.difficult);
        tv2 = (TextView) findViewById(R.id.back);
        
        tv.setTypeface(Menu.tf);
        tv1.setTypeface(Menu.tf);
        tv2.setTypeface(Menu.tf);
        
        tv.setOnTouchListener(new CustomTouchListener());
        tv1.setOnTouchListener(new CustomTouchListener());
        tv2.setOnTouchListener(new CustomTouchListener());
        
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        
        getSettings();
        
        setText();        
    }
        
    
	private void setText() {
        tv.setText(settingsHolder[0] ? "THEME: URBAN  " : "THEME: FIELD  ");
		tv1.setText(settingsHolder[1] ? "DIFFICULT: NORMAL  "	: "DIFFICULT: HARD  ");
	}

	private boolean[] settingsHolder = new boolean[4];
	
	private void getSettings() {
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		
		settingsHolder[0] = settings.getBoolean("mode", true);
		settingsHolder[1] = settings.getBoolean("difficult", true);
	}

	@Override
	public void onClick(View v) {
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	      
		switch(v.getId()){
			case R.id.mode:
				editor.putBoolean("mode", !settingsHolder[0]);
				break;
			case R.id.difficult:
				editor.putBoolean("difficult", !settingsHolder[1]);
				break;
			case R.id.back:
				finish();
				break;
		}
		
		editor.commit();
		
		getSettings();
		setText();
	}
}