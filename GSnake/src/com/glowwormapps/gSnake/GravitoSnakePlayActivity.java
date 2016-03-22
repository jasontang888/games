/*
 *  G - Snake
 *
 *  Copyright (C) 2012 Glow Worm Applications
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Street #330, Boston, MA 02111-1307, USA.
 */


package com.glowwormapps.gSnake;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class GravitoSnakePlayActivity extends Activity {

    private SnakeView mSnakeView;
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
    
    private SharedPreferences mPrefs; 
    
    private Timer autoUpdate;
    private SnakeTimer aUpdateEvent = new SnakeTimer();
    
    private int mSpeed;
    private int mWidth;
    
    private Boolean mGemsEnabled = true;
    private int mGemProbability = 50;
    private int mGemLife = 20;
    
    private Boolean mDrawScore = true;
    private Boolean mDrawGemTimers = true;
    
    private String mMazeType;
    
    
    private static final int MAX_WAIT = 660;
    
   public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu, menu);
        return true;

    }

    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.play:
            	mSnakeView.Start();
                return true;
            case R.id.pause:
            	mSnakeView.Pause();
                return true;
            case R.id.resume:
            	mSnakeView.Resume();
                return true;
                
            case R.id.preferences:
            	mSnakeView.Pause();
            	startActivity(new Intent(this, MasterPreferenceActivity.class));
            	
            	return true;
        	}
        return false;
        }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        
        //get the shared preference manager
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());

        //load Preferences
        mSpeed = MAX_WAIT - mPrefs.getInt("velocity", 220);
        mWidth=  mPrefs.getInt("screen_width", 12);   
        mMazeType = mPrefs.getString("maze_type","No Maze");
        
        mGemsEnabled = mPrefs.getBoolean("gems_enabled", true);
        mGemProbability = mPrefs.getInt("gem_probability", 50);
        mGemLife =  mPrefs.getInt("gem_life", 20);
        
        mDrawScore =  mPrefs.getBoolean("print_score", true);
        mDrawGemTimers =  mPrefs.getBoolean("print_gem_timers", true);
        // instantiate our Snake view and set it as the activity's content
         mSnakeView = new SnakeView(getApplicationContext());
        setContentView(mSnakeView);

        autoUpdate = new Timer();
        aUpdateEvent = new SnakeTimer();
        autoUpdate.schedule(aUpdateEvent, 0 ,mSpeed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSnakeView.startSnake();
        mWakeLock.acquire();
        
    	int vel = mPrefs.getInt("velocity", 220);
    	Boolean needReset = false;
    	if(mSpeed !=  MAX_WAIT - vel){
    		mSpeed =  MAX_WAIT - vel;
    		aUpdateEvent.cancel();
    		autoUpdate.purge();
    		aUpdateEvent= new SnakeTimer();
    		autoUpdate.schedule(aUpdateEvent, 0 ,mSpeed);
    	}
    	int newWidth =  mPrefs.getInt("screen_width", 12);
    	if(mWidth!=newWidth){
    		mWidth=newWidth;
    		needReset = true;
    	}
    	String newMazeType = mPrefs.getString("maze_type","No Maze");
    	if(!mMazeType.contentEquals(newMazeType))
    	{
    		mMazeType= newMazeType;
    		needReset = true;
    	}
    	//Gem data may need reloading but will not require a reset
    	 mGemsEnabled = mPrefs.getBoolean("gems_enabled", true);
         mGemProbability = mPrefs.getInt("gem_probability", 50);
         mGemLife =  mPrefs.getInt("gem_life", 20);
         
         //same for UI Data
         mDrawScore =  mPrefs.getBoolean("print_score", true);
         mDrawGemTimers =  mPrefs.getBoolean("print_gem_timers", true);
    	if(needReset){
    		mSnakeView.reset();
    	}
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	mSnakeView.stopSnake();
    	mWakeLock.release();
    }
    public enum Direction {
        UP,  DOWN, LEFT,
        RIGHT 
    }
    class SnakeTimer extends TimerTask {
        @Override
        public void run() {
         runOnUiThread(new Runnable() {
          public void run() {
           mSnakeView.postInvalidate();
          }
         });
        }
       }
    class SnakeView extends View implements SensorEventListener{

        private Sensor mAccelerometer;
        
        private static final int NUM_FRUIT = 6;
        private static final int NUM_GEMS = 3;
        
        private int mUnitWidth;
        private int mUnitHeight;
        private int mUnitSize;
        
        private int mPixWidth;
        private int mPixHeight;
        
        private Boolean mRuning = false;
        private Boolean mDisplayable = false;
        private Boolean mRequiresReset = false;
        private Bitmap mBmpBody;
        private Bitmap mBmpHead;
        private Bitmap mBmpBg;
        private Bitmap mBmpMaze;
        private Bitmap mBmpFruit[]= new Bitmap[NUM_FRUIT];
        private Bitmap mBmpGems[]= new Bitmap[NUM_GEMS];
        
        private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint mScorePaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        
        private String mText = getText(R.string.welcome).toString();
        
        private Direction direction;
        private Direction oldDirection;
        private final Snake mSnake = new Snake(mPrefs.getInt("start_length", 6));
        private final Fruit mFruit = new Fruit(0,0);
        private final Maze mMaze = new Maze(12, 16, "no maze");
        private final GemList mGems= new GemList();

        public void Start(){
        	mText = getText(R.string.welcome).toString();
        	mSnake.reset(mPrefs.getInt("start_length", 6));
        	reset();
        	mRuning = true;
        	mDisplayable = true;
        	mRequiresReset=false;
        }
        public void Pause(){
        	mText = getText(R.string.paused).toString();
        	mRuning = false;
        }
        public void Resume(){
        	mText = getText(R.string.welcome).toString();
        	if(mRequiresReset){
        		mSnake.reset(mPrefs.getInt("start_length", 6));
        		reset();
        	}
        	mDisplayable = true;
        	mRuning = true;
        	mRequiresReset=false;
        }
        public void Stop(){
        	mText = getText(R.string.welcome).toString();
        	mDisplayable = false;
           	mSnake.reset(mPrefs.getInt("start_length", 6));
        	mRuning = false;
        }
        
        public void startSnake() {
            /*
             * It is not necessary to get accelerometer events at a very high
             * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
             * automatic low-pass filter, which "extracts" the gravity component
             * of the acceleration. As an added benefit, we use less power and
             * CPU resources.
             */
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        public void stopSnake() {
            mSensorManager.unregisterListener(this);
        }
        public void populateFruit(Bitmap fruit[], int size) {
            Bitmap apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            fruit[0] = Bitmap.createScaledBitmap( apple , size , size , true);
            
            Bitmap apple2 = BitmapFactory.decodeResource(getResources(), R.drawable.apple2);
            fruit[1] = Bitmap.createScaledBitmap( apple2 , size , size , true);
            
            Bitmap orange = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
            fruit[2] = Bitmap.createScaledBitmap( orange , size , size , true);
            
            Bitmap banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
            fruit[3] = Bitmap.createScaledBitmap( banana , size , size , true);
        
            Bitmap pear = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
            fruit[4] = Bitmap.createScaledBitmap( pear , size , size , true);
        
            Bitmap cherry = BitmapFactory.decodeResource(getResources(), R.drawable.cherry);
            fruit[5] = Bitmap.createScaledBitmap( cherry , size , size , true);
        
        }
        public void populateGems(Bitmap gems[], int size) {
            Bitmap diamond = BitmapFactory.decodeResource(getResources(), R.drawable.diamond);
            gems[0] = Bitmap.createScaledBitmap( diamond , size , size , true);
        
            Bitmap emerald = BitmapFactory.decodeResource(getResources(), R.drawable.emerald);
            gems[1] = Bitmap.createScaledBitmap( emerald , size , size , true);
            
            Bitmap ruby = BitmapFactory.decodeResource(getResources(), R.drawable.ruby);
            gems[2] = Bitmap.createScaledBitmap( ruby , size , size , true);
        
        }
        


        public SnakeView(Context context) {
            super(context);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            
            mPixHeight = metrics.heightPixels;
            mPixWidth = metrics.widthPixels;
            
            direction=Direction.DOWN;
            oldDirection=Direction.DOWN;
            
            reset();

            Options opts = new Options();
            opts.inDither = true;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bg  = BitmapFactory.decodeResource(getResources(), R.drawable.background, opts);
            mBmpBg = Bitmap.createScaledBitmap( bg , mPixWidth, mPixHeight, true);
            
            mTextPaint.setTextAlign(Align.CENTER);
            mTextPaint.setARGB(205, 255, 255, 255);
            mTextPaint.setTextSize(36);
            
            mScorePaint.setTextAlign(Align.LEFT);
            mScorePaint.setARGB(180, 255, 255, 255);
            mScorePaint.setTextSize(18);
 }

        public void regenerateSprites()
        {
        	  mUnitWidth = mWidth;
              mUnitHeight = (mUnitWidth * mPixHeight) / mPixWidth;
              mUnitSize = mPixWidth/mUnitWidth;
              
              Bitmap body = BitmapFactory.decodeResource(getResources(), R.drawable.body);
              mBmpBody = Bitmap.createScaledBitmap( body , mUnitSize ,
              											mUnitSize, true);
              Bitmap head = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
              mBmpHead = Bitmap.createScaledBitmap( head , mUnitSize ,
            		  									mUnitSize, true);
              Bitmap maze = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
              mBmpMaze = Bitmap.createScaledBitmap( maze , mUnitSize ,
            		  									mUnitSize, true);
              
              populateFruit(mBmpFruit, mUnitSize );
              
              populateGems(mBmpGems,mUnitSize);
        	
        }
        public void reset(){
        	regenerateSprites();
        	resetSnakePosition();
        	resetMaze();
        	mGems.reset();
        	randomiseFruit();
        	

        }
        public void randomiseFruit(){
        	mFruit.randomise(mUnitWidth, mUnitHeight, mSnake,mMaze, null, mGems);
        }
        public void resetSnakePosition(){
        	 mSnake.SetPosition(mUnitWidth/2, mUnitHeight/2); 
        }
        public void resetMaze(){
       	 mMaze.reset(mUnitWidth, mUnitHeight,
       			 	 mMazeType); 
       }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            // Never run due to settings in the manifest
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
        	float xSens;
        	float ySens;
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */
            xSens = event.values[1];
            ySens = event.values[0];

            if( xSens > 0 &&
            		abs(xSens)>abs(ySens) &&
            		oldDirection!=Direction.UP){
            	direction = Direction.DOWN;
            } else if( ySens > 0 &&
            		abs(ySens)>=abs(xSens) &&
            		oldDirection!=Direction.RIGHT){
            	direction = Direction.LEFT;
            } else if(xSens <= 0 &&
            		abs(xSens)>abs(ySens) &&
            		oldDirection!=Direction.DOWN){
            	direction = Direction.UP;
            }else if(ySens <= 0 &&
            		abs(ySens)>=abs(xSens) &&
            		oldDirection!=Direction.LEFT){
            	direction = Direction.RIGHT;
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent x){
        	Resume();
        	return true;
        }
        
        private void drawString(Canvas canvas, Paint paint, String str, int x, int y) {
            String[] lines = str.split("\n");
            final Rect bounds = new Rect();
            int h = 0; 
            for (int i = 0; i < lines.length; ++i) {
                canvas.drawText(lines[i], x, y + i*h, paint);
                paint.getTextBounds(lines[i], 0, lines[i].length(), bounds);
                
                if(i == 0){
                	h = bounds.height()+8; //HACK
                }
            }
        }

        private float abs(float x) {
			if(x>=0)
				return x;
			return -x;
		}
        private void drawObject(Canvas c, Bitmap b,
        								  float xc,  float xs, 
        								  float yc,  float ys ,
        								  float xPos,float yPos )
        {
       		final float x = xc + xPos * xs;
    		final float y = yc - yPos * ys;
    		
    		c.drawBitmap(b, x, y, null);



    		//draw the snake off the edge of the screen
    		//HACK

    		if(xPos==0){
    			final float gx = xc + mUnitWidth * xs;
    			c.drawBitmap(b, gx, y, null);
    		}
    		if(xPos == mUnitWidth-1){
    			final float gx = xc - xs;
    			c.drawBitmap(b, gx, y, null);
    		}
    		if(yPos==0){
    			final float gy = yc - mUnitHeight * ys;
    			c.drawBitmap(b, x, gy, null);
    		}
    		if(yPos== mUnitHeight-1){
    			final float gy = yc + ys;
    			c.drawBitmap(b, x, gy, null);
    		}
    		
    		
    		//Rare Case When Snake may be in one of the corners
    		if(xPos==0 && yPos == 0 ){
    			final float gx = xc + mUnitWidth * xs;
    			final float gy = yc - mUnitHeight * ys;
    			c.drawBitmap(b, gx, gy, null);
    		}
    		if(xPos==0 && yPos == mUnitHeight-1){
    			final float gx = xc + mUnitWidth * xs;
    			final float gy = yc + ys;
    			c.drawBitmap(b, gx, gy, null);
    		}
    		if(xPos==mUnitWidth-1 && yPos == 0){
    			final float gx = xc -xs;
    			final float gy = yc - mUnitHeight * ys;
    			c.drawBitmap(b, gx, gy, null);
    		}
    		if(xPos==mUnitWidth-1 && yPos == mUnitHeight-1){
    			final float gx = xc - xs;
    			final float gy = yc + ys;
    			c.drawBitmap(b, gx, gy, null);
    		}

        }
		@Override
        protected void onDraw(Canvas canvas) {

			//draw the background

			canvas.drawBitmap(mBmpBg, 0, 0, null);

			/*
			 * compute the new position of our object, based on accelerometer
			 * data and present time.
			 */
			
			//take a safe guess to prevent the occasional null pointer
			//if the accelerometer input is too slow
			if(direction == null)
			{
				direction = Direction.DOWN;
			}
			if( mRuning ){
				mSnake.updatePositions(direction,mUnitWidth, mUnitHeight);
				
				
				
				if(mFruit.TestCollision(mSnake.getParticle(0))){
					mSnake.IncreaseLength(mPrefs.getInt("fruit_gain", 1));
					mFruit.randomise(mUnitWidth, mUnitHeight,mSnake,mMaze, null,mGems);
				}

				if(mSnake.TestCollision(mMaze)){
					// User Looses
					mText = getText(R.string.game_over).toString()
							.concat(" ".concat(
									String.valueOf(
											mSnake.getScore()
											)));
					mRequiresReset = true;
					mRuning = false;
				}
				mGems.progress(mGemsEnabled,mGemProbability, mGemLife,
   					 mUnitWidth, mUnitHeight, 
   					 mMaze , mSnake, mFruit);
				
				direction = mGems.doEvent(mUnitWidth, mUnitHeight, 
  					 mMaze , mSnake, mFruit,
  					 direction);
				
				oldDirection = direction;

			}
			if (mDisplayable){
				final float xs = mUnitSize;
				final float ys = mUnitSize;

				//HACK
				final int xGap = (mPixWidth-((int)xs)* mUnitWidth)/2;
				final int yGap = (mPixHeight-((int)ys)* mUnitHeight)/2; 

				final float xc = 0 + xGap;
				final float yc = canvas.getHeight()-ys - yGap;

				final Rect boundingRect = new Rect(xGap,yGap,mPixWidth-xGap,mPixHeight-yGap);
				mTextPaint.setStyle(Paint.Style.STROKE);
				canvas.drawRect(boundingRect, mTextPaint);
				mTextPaint.setStyle(Paint.Style.FILL);

				int count = mMaze.getParticleCount();
				//loop through the maze drawing it
				for (int i = 0; i <count; i++) {
					drawObject(canvas, mBmpMaze,
							xc,xs, yc,ys ,
							mMaze.getPosX(i),
							mMaze.getPosY(i) );
				}

				count = mSnake.getParticleCount();
				//loop backwards so that the eye is drawn ontop at the start
				for (int i = count - 1; i >=0; i--) {

					//Draw the main part of the snake

					if (i == 0){
						drawObject(canvas, mBmpHead,
								xc,xs, yc,ys ,
								mSnake.getPosX(i),
								mSnake.getPosY(i) );
					}else{
						drawObject(canvas, mBmpBody,
								xc,xs, yc,ys ,
								mSnake.getPosX(i),
								mSnake.getPosY(i) );
					}
				}
				count = mGems.getParticleCount();
				for (int i = 0; i <count; i++) {
					drawObject(canvas, mBmpGems[mGems.getType(i)],
							xc,xs, yc,ys ,
							mGems.getPosX(i),
							mGems.getPosY(i));
				}
				drawObject(canvas, mBmpFruit[mFruit.getType()],
						xc,xs, yc,ys ,
						mFruit.GetX(),
						mFruit.GetY());
				if(mDrawScore){
	                canvas.drawText("SCORE: "+mSnake.getScore(), xGap+3, yGap+18, mScorePaint);
				}
				if(mDrawGemTimers){
					
					//HACK
					//Move this into it's own function
					count = mGems.getParticleCount();
					int xOff=0;
					Rect bounds = new Rect();
					String txt;
					for (int i = 0; i <count; i++) {
						switch(mGems.getType(i)){
						case 0://Diamond
							  mScorePaint.setARGB(180, 150, 150, 255);
							break;
						case 1://Emerald
							mScorePaint.setARGB(180, 150, 255, 150);
							break;
						case 2://Ruby
							mScorePaint.setARGB(180, 255, 150, 150);
							break;
						}
						txt =""+ mGems.getLife(i)+" ";
						canvas.drawText(txt,
								 		xGap+3+xOff, yc +ys -3,
								 		mScorePaint);
						mScorePaint.getTextBounds(""+ mGems.getLife(i)+" ", 
												0, txt.length(), bounds);
						xOff+=bounds.width()+6;
						
					}

					mScorePaint.setARGB(180, 255, 255, 255);
				}
				
			}
			if(!mRuning)
			{
				drawString( canvas,mTextPaint, mText,  mPixWidth/2, mPixHeight/3);
			}
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
    }
}