package com.touchlabs.jamjam;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;


public class MenuGame extends Activity {
    
	public GamePreview gamePreview;

	
    /**
     * Method called on application start.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MenuGame","onCreate()");
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SoundManager mSoundManager = new SoundManager(getBaseContext());	

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        DisplayMetrics metrics = new DisplayMetrics();
        	getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
    	//Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
    	int width = metrics.widthPixels;
    	int height = metrics.heightPixels;
    	
        	
         gamePreview = new GamePreview(this,mSoundManager,width,height); 
         setContentView(gamePreview);        
    }

    
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
  
    }
    
    public void startGamePanel() {
    	
    }
    
    /**
     * Method called upon application closure.
     */
    @Override
    public void onStop() {
    	super.onStop();
    	finish();
    }
    
    @Override
    public void onRestart() {
    	//Log.i("MenuGame","onRestart()");
    	super.onRestart();
    	finish();
    }
    
    @Override
    public void onStart() {
    	//Log.i("MenuGame","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	//Log.i("MenuGame","onResume()");
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	//Log.i("MenuGame","onPause()");
    	super.onPause();
    	finish();    
    }
    
    @Override
    public void onDestroy() {
       	//Log.i("MenuGame","onDestroy()");
    	super.onDestroy();
    	finish(); 
    }
}