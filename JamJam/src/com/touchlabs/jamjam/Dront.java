package com.touchlabs.jamjam;

public class Dront {
	private int xPos = 150;
	private int yPos = 220;
	private int gridX = 3;
	private int gridY = 0;
	private int speedX = 600;
	private int speedY = 300;
	private boolean freeze = false;
	private double freezeTime = 2;
	private float mAnimation;
	private float mAnimationWalk = 1.8f;
	private SoundManager mSoundManager;
	private int tempImage = R.drawable.d1;

	private int y3 = 220;
	private int y2 = 120;
	private int y1 = 20;
	
	private int x4 = 200;
	private int x3 = 150;
	private int x2 = 100;
	private int x1 = 50;
	
	//Constructor
	public Dront(SoundManager ms, float scale_x, float scale_y) {
		mSoundManager = ms;
		xPos = (int) (150 * scale_x);
		
		x4 *= scale_x;
		x3 *= scale_x;
		x2 *= scale_x;
		x1 *= scale_x;
		
		y3 *= scale_y;
		y2 *= scale_y;
		y1 *= scale_y;
		
		yPos = (int) (220 * scale_y);
		speedX = (int) (speedX * scale_x);
		speedY = (int) (speedY * scale_y);
	}
	
	public void setSpeed(double newspeed){
		speedX = Double.valueOf(newspeed).intValue() * 6;
		speedY = Double.valueOf(newspeed).intValue() * 3;
	}
	
	//Return actual pos X
	public int getX() {
		return xPos;		
	}

	//Return actual pos Y
	public int getY() {
		return yPos;		
	}

	//Set the actual pos X
	public void setX(int x) {
		xPos = x;		
	}

	//Set the actual pos Y
	public void setY(int y) {
		yPos = y;		
	}

	
	//If the dront gets hit by obstacle
	public void hitDront() 
	{
		if (gridX > 0) 
			gridX--;
	}
	
	//If the dront gets hit by obstacle
	public void drontPowerUp() 
	{
		if (gridX < 4) 
			gridX++;
	}

	public boolean moveDrontUP() 
	{
		boolean ret = false;
		if (!freeze) {
			if (gridY > 0) {
				gridY--;
				ret = true;
			}
		}
		return ret;
	}

	public boolean moveDrontDOWN() 
	{
		boolean ret = false;
		if (!freeze) {
			if (gridY < 2) {
				gridY++;
				ret = true;
			}
		}
		return ret;
	}
	public void frontFreeze() {
		freeze = true;
		freezeTime = 2;
	}
	
	public void setAnimationWalk(float timer){
		if (timer == 99f)
			mAnimationWalk = 1.8f;
		else mAnimationWalk *= timer;
	}
	
	public int getDrontImage(){
				if(!freeze){
					if (mAnimation >= 8*mAnimationWalk/8) {
						tempImage = R.drawable.d1;
						return R.drawable.d1;
					} else if (mAnimation >= 7*mAnimationWalk/8) {
						tempImage = R.drawable.d2;
						return R.drawable.d2;
					} else if (mAnimation >= 6*mAnimationWalk/8) {
						tempImage = R.drawable.d3;
						return R.drawable.d3;
					} else if (mAnimation >= 5*mAnimationWalk/8) {
						tempImage = R.drawable.d4;
						return R.drawable.d4;
					} else if (mAnimation >= 4*mAnimationWalk/8) {
						tempImage = R.drawable.d5;
						return R.drawable.d5;
					} else if (mAnimation >= 3*mAnimationWalk/8) {
						tempImage = R.drawable.d6;
						return R.drawable.d6;
					} else if (mAnimation >= 2*mAnimationWalk/8) {
						tempImage = R.drawable.d7;
						return R.drawable.d7;
					} else if (mAnimation >= 1*mAnimationWalk/8) {
						tempImage = R.drawable.d8;
						return R.drawable.d8;
					} else 
						tempImage = R.drawable.d1;
						return R.drawable.d1;
				} else {
					return tempImage;
				}
	}
	
	public double getFreezeTime(){
		return freezeTime;
	}
	
	public boolean getFreeze(){
		return freeze;
	}
	public void updateDront(float timeDelta) {
		
		
		if (freeze) {
			freezeTime -=timeDelta;
			if (freezeTime <= 1 && freezeTime+timeDelta >= 1)
				mSoundManager.playSound(3);
			if (freezeTime <= 0 && freezeTime+timeDelta >= 0)
				mSoundManager.playSound(3);
			if (freezeTime < 0)
				freeze = false;
		}
		
		mAnimation -= timeDelta;
		if(mAnimation < 0){
			mAnimation += mAnimationWalk;
		}
		
		//Dront moves?
		if (gridY == 0) {
			if (yPos < y3) {
				yPos += speedY * timeDelta; 
				if (yPos > y3)
					yPos = y3;			
			}
			else yPos = y3;			
		}
		else if (gridY == 1) {
			if (yPos > y2) {
				yPos -= speedY * timeDelta;
				if (yPos < y2)
					yPos = y2;
			}
			else if (yPos < y2) {
				yPos += speedY * timeDelta; 
				if (yPos > y2)
					yPos = y2;
			}
			else yPos = y2;
		}
		else if (gridY == 2) {
			if (yPos > y1) {
				yPos -= speedY * timeDelta; 
				if (yPos < y1)
					yPos = y1;			
			}
			else yPos = y1;	
		}
		
		//Dront gets hit or power up
		if (gridX == 4) {
			if (xPos < x4) {
				xPos += speedX * timeDelta; 
				if (xPos > x4)
					xPos = x4;
			} else xPos = x4;			
		}
		else if (gridX == 3) {
			if (xPos > x3) {
				xPos -= speedX * timeDelta; 
				if (xPos < x3)
					xPos = x3; 				
			}
			else if (xPos < x3) {
				xPos += speedX * timeDelta; 
				if (xPos > x3)
					xPos = x3;
			} 			
			else xPos = x3;			
		}
		else if (gridX == 2) {
			if (xPos > x2) {
				xPos -= speedX * timeDelta; 
				if (xPos < x2)
					xPos = x2; 				
			}
			else if (xPos < x2) {
				xPos += speedX * timeDelta; 
				if (xPos > x2)
					xPos = x2;
			} 			
			else xPos = x2;			
		}
		else if (gridX == 1) {
			if (xPos > x1) {
				xPos -= speedX * timeDelta; 
				if (xPos < x1)
					xPos = x1; 				
			}
			else if (xPos < x1) {
				xPos += speedX * timeDelta; 
				if (xPos > x1)
					xPos = x1;
			} 			
			else xPos = x1;			
		}
		else if (gridX == 0) {
			if (xPos > 0) {
				xPos -= speedX * timeDelta; 
				if (xPos < 0)
					xPos = 0; 				
			}
			else xPos = 0;			
		}
		
	}
	
}
