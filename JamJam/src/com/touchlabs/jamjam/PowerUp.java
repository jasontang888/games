package com.touchlabs.jamjam;

import java.util.Random;

public class PowerUp {

	private int xPos;
	private int yPos;
	private int speed;
	private float startTime;
	private Random generator = new Random();
	private int type;
	private int dif = 0;

	private int y3 = 240;
	private int y2 = 140;
	private int y1 = 40;
	
	public PowerUp(float scale_x, float scale_y){
		speed = (int) (100 * scale_x);
		dif = (int) (600 * scale_x);
		y3 *= scale_y;
		y2 *= scale_y;
		y1 *= scale_y;
		newPos();
		
	}

	public void setSpeed(double newspeed){
		speed = Double.valueOf(newspeed).intValue();// newspeed;
	}
		
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public void updatePowerUp(float timeDelta){
		startTime -= timeDelta;
		if(startTime < 0) {	
			xPos -= timeDelta * speed;
			if(xPos < -dif/6) {
				newPos();
			}
		}
	}
	
	private void newPos() {
		xPos = dif;

		int type2 = generator.nextInt(4);
		if (type2 == 3)
			type = 1;
		else if (type2 == 2)
			type = 2;
		else type = 0;
			
		int floor = generator.nextInt(3);
		if(floor == 0)
			yPos = y1;
		else if (floor == 1)
			yPos = y2;
		else 
			yPos = y3;
		startTime = 5 + (generator.nextInt(20) + 1);
	}
	
	public int getType() {
		return type;		
	}

}
