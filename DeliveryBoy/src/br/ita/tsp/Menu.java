package br.ita.tsp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Menu extends Activity implements OnClickListener {

	public static final String PREFS_NAME = "DeliveryBoySettings";
	public static Typeface tf;
	private TextView tv, tv1, tv2, tv3, tv4, tv5;
	MediaPlayer mMediaPlayer = null;
	
	@Override
	public void onRestart(){
		super.onRestart();
		if (mMediaPlayer==null)
			mMediaPlayer = MediaPlayer.create(Menu.this, R.raw.bosco);
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		boolean sound = settings.getBoolean("sound", true);
		if (sound==true)
			playerMedia();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tf = Typeface.createFromAsset(getAssets(), "data/fonts/Plok.ttf");

		tv = (TextView) findViewById(R.id.start);
		tv1 = (TextView) findViewById(R.id.sound);
		tv2 = (TextView) findViewById(R.id.settings);
		tv3 = (TextView) findViewById(R.id.score);
		tv4 = (TextView) findViewById(R.id.about);
		tv5 = (TextView) findViewById(R.id.exit);

		tv.setTypeface(tf);
		tv1.setTypeface(tf);
		tv2.setTypeface(tf);
		tv3.setTypeface(tf);
		tv4.setTypeface(tf);
		tv5.setTypeface(tf);

		tv.setOnTouchListener(new CustomTouchListener());
		tv1.setOnTouchListener(new CustomTouchListener());
		tv2.setOnTouchListener(new CustomTouchListener());
		tv3.setOnTouchListener(new CustomTouchListener());
		tv4.setOnTouchListener(new CustomTouchListener());
		tv5.setOnTouchListener(new CustomTouchListener());

		tv.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);

//		getSettings();

		setText();
		mMediaPlayer = MediaPlayer.create(Menu.this, R.raw.bosco);
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		boolean sound = settings.getBoolean("sound", true);
		if (sound==true)
			playerMedia();

	}

	
	private void setText() {
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		boolean sound = settings.getBoolean("sound", true);
		if(sound==true)
			tv1.setText("Sound: ON ");
		else
			tv1.setText("Sound: OFF "); 
	}

	private boolean[] settingsHolder = new boolean[2];

	private void getSettings() {
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		settingsHolder[0] = settings.getBoolean("sound", true);
	}

	@Override
	public void onClick(View v) {
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		switch (v.getId()) {
		case R.id.start:
			Intent i = new Intent(this, Game.class);
			stopMusic();
			startActivity(i);
			break;
		
		case R.id.sound:
			SharedPreferences sett= getSharedPreferences(PREFS_NAME, 0);
			boolean sound = sett.getBoolean("sound", true);
			SharedPreferences.Editor edit = sett.edit();
			
			if (sound==true) {
				stopMusic();
				edit.putBoolean("sound", false);
			} else {
				playerMedia();
				edit.putBoolean("sound", true);
			}
			setText();
			edit.commit();
			break;
			
		case R.id.settings:
			Intent j = new Intent(this, SettingsActivity.class);
			stopMusic();
			startActivity(j);
			break;
		case R.id.score:
			Intent k = new Intent(this, Score.class);
			stopMusic();
			startActivity(k);
			break;
		case R.id.about:
			Intent l = new Intent(this, About.class);
			stopMusic();
			startActivity(l);
			break;
		case R.id.exit:
			stopMusic();
			finish();
			break;
		}

		editor.commit();

		getSettings();
		setText();

	}

	private void playerMedia() {
		if (mMediaPlayer.isPlaying()==false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					Handler handler = new Handler();
					mMediaPlayer.setLooping(true);
					mMediaPlayer.start();
					
					Message message = handler.obtainMessage();
					handler.dispatchMessage(message);
					Looper.loop();
				}
			}).start();

		}
	}

	private void stopMusic() {
		if (mMediaPlayer.isPlaying()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					Handler handler = new Handler();

					mMediaPlayer.pause();
					Message message = handler.obtainMessage();
					handler.dispatchMessage(message);
					Looper.loop();
				}
			}).start();
		}
	}

}