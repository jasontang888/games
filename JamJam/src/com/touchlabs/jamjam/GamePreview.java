package com.touchlabs.jamjam;


import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePreview extends SurfaceView implements SurfaceHolder.Callback, java.io.Serializable{
	
	public static final long serialVersionUID = 1L;
	private static final Paint sPaintTextWhite = new Paint();
	private static final Paint sPaintTextBlack = new Paint();
	private static final Paint sPaintTextWhite2 = new Paint();
	private static final Paint sPaintTextBlack2 = new Paint();
	private GamePreviewThread thread;
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
	private Activity mActivity;
	private GameModel mGameModel;
	private SoundManager mSoundManager;	
	public Context context;
	
	float hero_width = 320;
	float hero_height = 480;
	int width = 0;
	int height = 0;
	float scale_x = 0;
	float scale_y = 0;
	float aspectratio = 0;
	
	private int textposx = 0; 
	private int textposy = 0;
	private int textposx2 = 0;
	private int textposy2 = 0;
	private int handposx = 0;
	
	private int touchx = 0;
	private int touchy = 0;
	
	public GamePreview(Context context, SoundManager sm, int width, int height) {
		super(context);
		
		this.width = width;
		this.height = height;

		scale_y = this.height / hero_width;
		scale_x = this.width / hero_height;
		
		float hero = hero_width / hero_height;
		float you = (float) (this.height) / (float) (this.width);

		aspectratio = you/hero;
		
		textposx = (int) (240 * scale_x); 
		textposy = (int) (20 * scale_y);
		textposx2 = (int) (240 * scale_x);
		textposy2 = (int) (180 * scale_y);
		handposx = (int) (-12 * scale_y);
		touchx = (int) (480 * scale_y);
		touchy = (int) (160 * scale_y);
		
		this.context = context;
		mSoundManager = sm;			
		mGameModel = new GameModel(mSoundManager, scale_x, scale_y);
		mGameModel.initialize(context);
		fillBitmapCache();	
		mActivity = (Activity) context;		
		getHolder().addCallback(this);
		thread = new GamePreviewThread(this,mGameModel);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();

		// The gesture threshold expressed in dip
		float GESTURE_THRESHOLD_DIP = 16.0f;
		// Convert the dips to pixels
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int mGestureThreshold = (int) (GESTURE_THRESHOLD_DIP * scale + 0.5f);		
		
		sPaintTextWhite.setTextSize(mGestureThreshold);
		sPaintTextWhite.setARGB(255, 255, 255, 255);
		Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextWhite.setTypeface(font2);
		sPaintTextWhite.setTextAlign(Paint.Align.CENTER);
		sPaintTextWhite.setAntiAlias(true);
		
		sPaintTextBlack.setTextSize(mGestureThreshold);
		sPaintTextBlack.setARGB(255, 0, 0, 0);
		Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextBlack.setTypeface(font3);
		sPaintTextBlack.setTextAlign(Paint.Align.CENTER);
		sPaintTextBlack.setAntiAlias(true);
		
		// The gesture threshold expressed in dip
		GESTURE_THRESHOLD_DIP = 48.0f;
		// Convert the dips to pixels
		final float scale2 = getContext().getResources().getDisplayMetrics().density;
		mGestureThreshold = (int) (GESTURE_THRESHOLD_DIP * scale2 + 0.5f);		
		
		sPaintTextWhite2.setTextSize(mGestureThreshold);
		sPaintTextWhite2.setARGB(255, 255, 255, 255);
		Typeface font4 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextWhite2.setTypeface(font4);
		sPaintTextWhite2.setTextAlign(Paint.Align.CENTER);
		sPaintTextWhite2.setAntiAlias(true);
		
		sPaintTextBlack2.setTextSize(mGestureThreshold);
		sPaintTextBlack2.setARGB(255, 0, 0, 0);
		Typeface font5 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextBlack2.setTypeface(font5);
		sPaintTextBlack2.setTextAlign(Paint.Align.CENTER);
		sPaintTextBlack2.setAntiAlias(true);

	}
	

	
	
	private void fillBitmapCache() {
		mBitMapCache = new HashMap<Integer, Bitmap>();
		mBitMapCache.put(R.drawable.background, BitmapFactory.decodeResource(getResources(), R.drawable.background));
		mBitMapCache.put(R.drawable.gamebg, BitmapFactory.decodeResource(getResources(), R.drawable.gamebg));
		mBitMapCache.put(R.drawable.dront, BitmapFactory.decodeResource(getResources(), R.drawable.dront));
		mBitMapCache.put(R.drawable.egg, BitmapFactory.decodeResource(getResources(), R.drawable.egg));
		mBitMapCache.put(R.drawable.watch, BitmapFactory.decodeResource(getResources(), R.drawable.watch));
		mBitMapCache.put(R.drawable.stop, BitmapFactory.decodeResource(getResources(), R.drawable.stop));
		mBitMapCache.put(R.drawable.ground, BitmapFactory.decodeResource(getResources(), R.drawable.ground));
		mBitMapCache.put(R.drawable.wall, BitmapFactory.decodeResource(getResources(), R.drawable.wall));
		mBitMapCache.put(R.drawable.gameover, BitmapFactory.decodeResource(getResources(), R.drawable.gameover));
		mBitMapCache.put(R.drawable.d1, BitmapFactory.decodeResource(getResources(), R.drawable.d1));
		mBitMapCache.put(R.drawable.d2, BitmapFactory.decodeResource(getResources(), R.drawable.d2));
		mBitMapCache.put(R.drawable.d3, BitmapFactory.decodeResource(getResources(), R.drawable.d3));
		mBitMapCache.put(R.drawable.d4, BitmapFactory.decodeResource(getResources(), R.drawable.d4));
		mBitMapCache.put(R.drawable.d5, BitmapFactory.decodeResource(getResources(), R.drawable.d5));
		mBitMapCache.put(R.drawable.d6, BitmapFactory.decodeResource(getResources(), R.drawable.d6));
		mBitMapCache.put(R.drawable.d7, BitmapFactory.decodeResource(getResources(), R.drawable.d7));
		mBitMapCache.put(R.drawable.d8, BitmapFactory.decodeResource(getResources(), R.drawable.d8));
		mBitMapCache.put(R.drawable.d9, BitmapFactory.decodeResource(getResources(), R.drawable.d9));
		mBitMapCache.put(R.drawable.bg, BitmapFactory.decodeResource(getResources(), R.drawable.bg));
		mBitMapCache.put(R.drawable.groundbottom, BitmapFactory.decodeResource(getResources(), R.drawable.groundbottom));
		mBitMapCache.put(R.drawable.girl, BitmapFactory.decodeResource(getResources(), R.drawable.girl));
		mBitMapCache.put(R.drawable.mobb1, BitmapFactory.decodeResource(getResources(), R.drawable.mobb1));
		mBitMapCache.put(R.drawable.mobb2, BitmapFactory.decodeResource(getResources(), R.drawable.mobb2));
		mBitMapCache.put(R.drawable.mobb3, BitmapFactory.decodeResource(getResources(), R.drawable.mobb3));
		mBitMapCache.put(R.drawable.mobb4, BitmapFactory.decodeResource(getResources(), R.drawable.mobb4));

	}
	

	public void onDraw(Canvas canvas) {
				
		// Draw background		
		//canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), mGameModel.getBackgroun().getX(),0,null);
		//canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), mGameModel.getBackgroun().getX2(),0,null);
		
	    //Background
		int new_width = (int) (mBitMapCache.get(mGameModel.getBackgroun().getImage()).getWidth());

		Rect dst = new Rect(mGameModel.getBackgroun().getX1(new_width), 0, mGameModel.getBackgroun().getX1(new_width)+ new_width, height);

		if (mGameModel.getBackgroun().getFirst()) {
		    canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), null, dst, null);
		    dst = new Rect(mGameModel.getBackgroun().getX1(new_width) + new_width, 0,mGameModel.getBackgroun().getX1(new_width) + new_width + new_width, height);
		    canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), null, dst, null);
	    }
	    else {
			dst = new Rect(mGameModel.getBackgroun().getX1(new_width) + new_width, 0, mGameModel.getBackgroun().getX1(new_width)+ new_width + new_width, height);
		    canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), null, dst, null);
		    dst = new Rect(mGameModel.getBackgroun().getX1(new_width), 0,mGameModel.getBackgroun().getX1(new_width) + new_width, height);
		    canvas.drawBitmap(mBitMapCache.get(mGameModel.getBackgroun().getImage()), null, dst, null);
	    }

		// Draw top ground
		new_width = (int) (mBitMapCache.get(R.drawable.ground).getWidth());
		int new_height = (int) (mBitMapCache.get(R.drawable.ground).getHeight());
		dst = new Rect(mGameModel.getGroundTop().getX1(), mGameModel.getGroundTop().getY(), mGameModel.getGroundTop().getX1() + new_width, mGameModel.getGroundTop().getY() + new_height);
		Rect dst2 = new Rect(mGameModel.getGroundTop().getX2(new_width), mGameModel.getGroundTop().getY(), mGameModel.getGroundTop().getX2(new_width) + new_width, mGameModel.getGroundTop().getY() + new_height);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), null, dst, null);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), null, dst2, null);
		
		//canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), mGameModel.getGroundTop().getX1(), mGameModel.getGroundTop().getY(),null);
		//canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), mGameModel.getGroundTop().getX2(), mGameModel.getGroundTop().getY(),null);
		
		// Draw middle ground
		dst = new Rect(mGameModel.getGroundMid().getX1(), mGameModel.getGroundMid().getY(), mGameModel.getGroundMid().getX1() + new_width, mGameModel.getGroundMid().getY() + new_height);
		dst2 = new Rect(mGameModel.getGroundMid().getX2(new_width), mGameModel.getGroundMid().getY(), mGameModel.getGroundMid().getX2(new_width) + new_width, mGameModel.getGroundMid().getY() + new_height);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), null, dst, null);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), null, dst2, null);
	    
	    //canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), mGameModel.getGroundMid().getX1(), mGameModel.getGroundMid().getY(),null);
		//canvas.drawBitmap(mBitMapCache.get(R.drawable.ground), mGameModel.getGroundMid().getX2(), mGameModel.getGroundMid().getY(),null);
		
		//Draw bottom ground
		new_width = (int) (mBitMapCache.get(R.drawable.groundbottom).getWidth());
		new_height = (int) (mBitMapCache.get(R.drawable.groundbottom).getHeight());
		dst = new Rect(mGameModel.getGroundBot().getX1(), mGameModel.getGroundBot().getY(), mGameModel.getGroundBot().getX1() + new_width, mGameModel.getGroundBot().getY() + new_height);
		dst2 = new Rect(mGameModel.getGroundBot().getX2(new_width), mGameModel.getGroundBot().getY(), mGameModel.getGroundBot().getX2(new_width) + new_width, mGameModel.getGroundBot().getY() + new_height);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.groundbottom), null, dst, null);
	    canvas.drawBitmap(mBitMapCache.get(R.drawable.groundbottom), null, dst2, null);

		//canvas.drawBitmap(mBitMapCache.get(R.drawable.groundbottom), mGameModel.getGroundBot().getX1(), mGameModel.getGroundBot().getY(),null);
		//canvas.drawBitmap(mBitMapCache.get(R.drawable.groundbottom), mGameModel.getGroundBot().getX2(), mGameModel.getGroundBot().getY(),null);

		//Draw wall in the middle
		int count = mGameModel.getListEnemyWall().size();
		new_width = (int) (mBitMapCache.get(R.drawable.wall).getWidth());
		new_height = (int) (mBitMapCache.get(R.drawable.wall).getHeight());

		for(int i = 0; i < count; i++){
			dst = new Rect(mGameModel.getListEnemyWall().get(i).getX1(), mGameModel.getListEnemyWall().get(i).getY(), mGameModel.getListEnemyWall().get(i).getX1() + new_width, mGameModel.getListEnemyWall().get(i).getY() + new_height);
		    canvas.drawBitmap(mBitMapCache.get(R.drawable.wall), null, dst, null);			
			//canvas.drawBitmap(mBitMapCache.get(R.drawable.wall), mGameModel.getListEnemyWall().get(i).getX1(), mGameModel.getListEnemyWall().get(i).getY(),null);
		}
		
		new_width = (int) (mBitMapCache.get(mGameModel.getDront().getDrontImage()).getWidth());
		new_height = (int) (mBitMapCache.get(mGameModel.getDront().getDrontImage()).getHeight());
		dst = new Rect(mGameModel.getDront().getX(), mGameModel.getDront().getY(), mGameModel.getDront().getX() + new_width, mGameModel.getDront().getY() + new_height);
	    canvas.drawBitmap(mBitMapCache.get(mGameModel.getDront().getDrontImage()), null, dst, null);			
		//canvas.drawBitmap(mBitMapCache.get(mGameModel.getDront().getDrontImage()),mGameModel.getDront().getX(),mGameModel.getDront().getY(),null);
	
		//PowerUp
		new_width = (int) (mBitMapCache.get(R.drawable.egg).getWidth());
		new_height = (int) (mBitMapCache.get(R.drawable.egg).getHeight());
		dst = new Rect(mGameModel.getPowerUp().getX(),mGameModel.getPowerUp().getY(), mGameModel.getPowerUp().getX() + new_width, mGameModel.getPowerUp().getY() + new_height);

		if (mGameModel.getPowerUp().getType() == 0) {
		    canvas.drawBitmap(mBitMapCache.get(R.drawable.egg), null, dst, null);	
			//canvas.drawBitmap(mBitMapCache.get(R.drawable.egg),mGameModel.getPowerUp().getX(),mGameModel.getPowerUp().getY(),null);
		}
		else if (mGameModel.getPowerUp().getType() == 1) {
		    canvas.drawBitmap(mBitMapCache.get(R.drawable.watch), null, dst, null);	
			//canvas.drawBitmap(mBitMapCache.get(R.drawable.watch),mGameModel.getPowerUp().getX(),mGameModel.getPowerUp().getY(),null);
		}
		else if (mGameModel.getPowerUp().getType() == 2) {
		    canvas.drawBitmap(mBitMapCache.get(R.drawable.stop), null, dst, null);	
			//canvas.drawBitmap(mBitMapCache.get(R.drawable.stop),mGameModel.getPowerUp().getX(),mGameModel.getPowerUp().getY(),null);
		}

		
		if(mGameModel.getShowGirl()){
			new_width = (int) (mBitMapCache.get(R.drawable.girl).getWidth());
			new_height = (int) (mBitMapCache.get(R.drawable.girl).getHeight());
			dst = new Rect((int) (330 * scale_x), (int) (40 * scale_y), (int) (330 * scale_x) + new_width, (int) (40 * scale_y) + new_height);
		    canvas.drawBitmap(mBitMapCache.get(R.drawable.girl), null, dst, null);	
			//canvas.drawBitmap(mBitMapCache.get(R.drawable.girl), 330 * scale_x,40 * scale_y,null);
		}
		
		canvas.drawText("Distance: " + mGameModel.getDistance(), textposx,textposy+2,sPaintTextBlack);
		canvas.drawText("Distance: " + mGameModel.getDistance(), textposx-1,textposy,sPaintTextWhite);
		
		if(mGameModel.getDront().getFreeze()){
			canvas.drawText("" + Double.valueOf(mGameModel.getDront().getFreezeTime()).intValue(), textposx2,textposy2+2,sPaintTextBlack2);
			canvas.drawText("" + Double.valueOf(mGameModel.getDront().getFreezeTime()).intValue(), textposx2,textposy2,sPaintTextWhite2);
		}
		
		//Draw hands on left side
		canvas.drawBitmap(mBitMapCache.get(mGameModel.getHandImage()), handposx,0,null);
		
		//Lost?
		if(mGameModel.getLost()){
			canvas.drawBitmap(mBitMapCache.get(R.drawable.gameover),110 * scale_x,60 * scale_y,null);
			canvas.drawText("Your distance: " + mGameModel.getDistance(), 240 * scale_x,167 * scale_y,sPaintTextBlack);
			
		    // Restore preferences
			SharedPreferences settings = mActivity.getSharedPreferences("Score_file", 0);
			canvas.drawText("Highscore: " + settings.getInt("distance", 0), 240 * scale_x,183 * scale_y,sPaintTextBlack);
			
			if(mGameModel.getDistance() > settings.getInt("distance", 0)){
			// Save you score
				SharedPreferences settings2 = context.getSharedPreferences("Score_file", 0);
				SharedPreferences.Editor editor = settings2.edit();
				editor.putInt("distance", mGameModel.getDistance());
				editor.commit();
			}
		}

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		
		synchronized (getHolder()) {			
			
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(!mGameModel.getLost()){
					//if (event.getX() > 0 && event.getX() < touchx) {
						if (event.getY() > 0 && event.getY() < touchy) {
							if (mGameModel.getDront().moveDrontDOWN())
								mSoundManager.playSound(0);									
						}
						else if (event.getY() > touchy) {
							if (mGameModel.getDront().moveDrontUP())
								mSoundManager.playSound(0);							
						}
					//}
				} else {
					thread.setRunning(false);
					mBitMapCache = null;
					getHolder().removeCallback(this);
					Activity parent = (Activity) getContext();
					parent.finish();
				}
		
				break;
			case MotionEvent.ACTION_MOVE:
				

				break; 
			case MotionEvent.ACTION_UP:
					
		

				break;
			}
			
		}
		
		return true;
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		
		if (!thread.isAlive()) {
			thread = new GamePreviewThread(this,mGameModel);			
		}
		
		thread.setRunning(true);
		thread.start();
		
		
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		//Log.v("Progression","surfaceDestroyed");
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		
		// To prevent memory filled exception
		mBitMapCache = new HashMap<Integer, Bitmap>();
	}
	

}
