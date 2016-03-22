package com.sportsboards2d.db.objects;

import com.sportsboards2d.util.Colors;

/**
 * Coded by Nathan King
 */
//what is this for
/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class MenuTextSettings {

	public static float onSelectR;
	public static float onSelectG;
	public static float onSelectB;
	public static float unSelectR;
	public static float unSelectG;
	public static float unSelectB;
	
	public static void setColor(int id){
		
		switch(id){
			case Colors.WHITE:
				onSelectR = 1.0f;
				onSelectG = 1.0f;
				onSelectB = 1.0f;
				unSelectR = 0.0f;
				unSelectG = 1.0f;
				unSelectB = 0.0f;
				break;
			case Colors.BLACK:
				onSelectR = 0.0f;
				onSelectG = 0.0f;
				onSelectB = 0.0f;
				unSelectR = 1.0f;
				unSelectG = 0.0f;
				unSelectB = 0.0f;
				break;	
			case Colors.RED:
				onSelectR = 1.0f;
				onSelectG = 0.0f;
				onSelectB = 0.0f;
				unSelectR = 0.0f;
				unSelectG = 0.0f;
				unSelectB = 1.0f;
				break;
			case Colors.GREEN:
				onSelectR = 0.0f;
				onSelectG = 1.0f;
				onSelectB = 0.0f;
				unSelectR = 1.0f;
				unSelectG = 1.0f;
				unSelectB = 1.0f;
				break;
			case Colors.BLUE:
				onSelectR = 0.0f;
				onSelectG = 0.0f;
				onSelectB = 1.0f;
				unSelectR = 1.0f;
				unSelectG = 0.0f;
				unSelectB = 0.0f;
				break;
			case Colors.ORANGE:
				onSelectR = 1.0f;
				onSelectG = 0.38f;
				onSelectB = 0.0f;
				unSelectR = 0.0f;
				unSelectG = 0.0f;
				unSelectB = 0.0f;
				break;
			case Colors.GREY:
				onSelectR = 0.6f;
				onSelectG = 0.6f;
				onSelectB = 0.6f;
				unSelectR = 1.0f;
				unSelectG = 0.0f;
				unSelectB = 0.0f;
				break;
			case Colors.YELLOW:
				onSelectR = 1.0f;
				onSelectG = 1.0f;
				onSelectB = 0.0f;
				unSelectR = 1.0f;
				unSelectG = 1.0f;
				unSelectB = 1.0f;
				break;
		}
	}
}
