package com.touchlabs.jamjam;

public class GroundBot {
	private int xPos;
	private int yPos;
	private int speed;
	private int dif = 600;
	
	public GroundBot(float scale_x, float scale_y){
		xPos = 0;
		yPos = (int) (300 * scale_y);
		speed = (int) (100 * scale_x);
		dif = (int) (600 * scale_x);
	}
	
	public void setSpeed(double newspeed){
		speed = Double.valueOf(newspeed).intValue();// newspeed;
	}
	
	public int getX1(){
		return xPos;
	}
	
	public int getX2(int new_width){
		return xPos + new_width;
	}
	
	public int getY(){
		return yPos;
	}
	
	public void setXPos(float timeDelta){
		xPos -= timeDelta * speed;
		if(xPos < -dif)
			xPos = 0;
	}
	


}
