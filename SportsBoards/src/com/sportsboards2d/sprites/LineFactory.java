package com.sportsboards2d.sprites;

import org.anddev.andengine.entity.primitive.Line;

import com.sportsboards2d.util.Colors;
	
/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class LineFactory {
	
	private static final float LINEWIDTH_DEFAULT = 5.0f;

	private static float pRed = 100.0f;
	private static float pGreen = 100.0f;
	private static float pBlue = 100.0f;
	
	public static Line createLine(float pStartX, float pStartY, float pEndX, float pEndY){
		Line line = new Line(pStartX, pStartY, pEndX, pEndY, LINEWIDTH_DEFAULT);
		line.setColor(pRed, pGreen, pBlue);
		return line;
	}
	
	public static Line createLine(float pStartX, float pStartY, float pEndX, float pEndY, float line_width){
		Line line = new Line(pStartX, pStartY, pEndX, pEndY, line_width);
		line.setColor(pRed, pGreen, pBlue);
		return line;
	}
	
	public static void setColor(int id){
		switch(id){
		
			case Colors.WHITE:
				pRed = 1.0f;
				pGreen = 1.0f;
				pBlue = 1.0f;
				break;
			case Colors.BLACK:
				pRed = 0.0f;
				pGreen = 0.0f;
				pBlue = 0.0f;
				break;	
			case Colors.RED:
				pRed = 1.0f;
				pGreen = 0.0f;
				pBlue = 0.0f;
				break;
			case Colors.GREEN:
				pRed = 0.0f;
				pGreen = 1.0f;
				pBlue = 0.0f;
				break;
			case Colors.BLUE:
				pRed = 0.0f;
				pGreen = 0.0f;
				pBlue = 1.0f;
				break;
			case Colors.ORANGE:
				pRed = 1.0f;
				pGreen = 0.38f;
				pBlue = 0.0f;
				break;
			case Colors.GREY:
				pRed = 0.6f;
				pGreen = 0.6f;
				pBlue = 0.6f;
				break;
			case Colors.YELLOW:
				pRed = 1.0f;
				pGreen = 1.0f;
				pBlue = 0.0f;
				break;
		}	
	}
}
