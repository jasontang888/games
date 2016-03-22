package com.sportsboards2d.util;

import org.anddev.andengine.util.MathUtils;
/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class Math {

	
	public static float calculateDifference(float pX1, float pY1, float pX2, float pY2){
		
		return MathUtils.distance(pX1, pY1, pX2, pY2);
		
	}
	
	public static float calculateDifference(float x1, float x2){
		
		return x2-x1;
		
	}
	
	public static float calculateRotation(float x1, float y1, float x2, float y2){
		
		float xResult = java.lang.Math.abs(x2 - x1);
		float yResult = java.lang.Math.abs(y2 - y1);
		
		//float result = 
		
		return MathUtils.radToDeg((float)java.lang.Math.atan2(-yResult, xResult));
		
	}
	
}
