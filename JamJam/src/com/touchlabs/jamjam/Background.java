package com.touchlabs.jamjam;

public class Background {
	private int xPos = 0;
	private int dif = 0;
	private int speed;
	private int temp;
	
	private boolean x1first = true;
	
	public Background(float scale_x){
		speed = (int) (23 * scale_x);
		//dif =  (int) (dif * scale_x);
	}
	
	public int getImage(){
		return R.drawable.bg;
	}
	
	
	public int getX1(int width){		
		dif = width;
		return xPos;
	}
		
	public boolean getFirst(){
		return x1first;
	}
	
	public void setXPos(float timeDelta){
		temp = Double.valueOf(timeDelta * speed).intValue();
		xPos -= temp;//timeDelta * speed;
		
		if(xPos <= -dif) {
			xPos = 0;
			if (x1first)
				x1first = false;
			else x1first = true;
		}
	}
}
