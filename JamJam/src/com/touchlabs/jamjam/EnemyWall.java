package com.touchlabs.jamjam;

import java.util.Random;

public class EnemyWall {

	private int xPos;
	private int yPos;
	private int speed;
	private float startTime;
	private boolean doWall = false;
	int dif = 0;
	int baseSpeed = 0;
	
	Random generator = new Random();
	
	public EnemyWall(int randomTime, int floor, float scale_x, float scale_y){
		dif = (int) (600 * scale_x);
		xPos = dif;
		if(floor == 1)
			yPos = (int) (20 * scale_y);
		else if(floor == 2)
			yPos = (int) (120 * scale_y);
		else if (floor == 3)
			yPos = (int) (220 * scale_y);
		
		speed = (int) (100 * scale_x);
		baseSpeed = speed;
		startTime = randomTime;
	}
	
	public void setSpeed(double newspeed){
		speed = Double.valueOf(newspeed).intValue();// newspeed;
	}
	
	public int getX1(){
		return xPos;
	}
	
	public void setX(int pos){
		xPos = pos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public boolean updateWall(float timeDelta, boolean spawnWall){
		boolean spawnedNewWall = false;
		
		startTime -= timeDelta;

		if (!doWall && spawnWall && startTime < 0 ) {
			doWall = true;
			spawnedNewWall = true;
		}

		if(startTime < 0 && doWall){	
			xPos -= timeDelta * speed;
			if(xPos < -dif/6){
				startTime = (generator.nextInt(15) + 1);
				setNewRandomTime();				
			}
		}
		return spawnedNewWall;
	}
	
	public void setNewRandomTime(){
		xPos = dif;
		double tmp = baseSpeed;
		double newtmp = tmp / speed;
		int rand = Double.valueOf(15 * newtmp).intValue();
		startTime = (generator.nextInt(rand));
		doWall = false;
	}
	
}
