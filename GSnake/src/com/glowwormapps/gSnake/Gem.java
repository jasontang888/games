package com.glowwormapps.gSnake;

public class Gem extends Fruit{
	
	private int mLife; 
	Gem(int x, int y, int life) {
		super(x, y);
		NUM_FRUIT = 3;	
		mLife = life; 
	}
	public  int getLife(){
		return mLife;
	}
	public void progress(){
		mLife--;
	}
}
