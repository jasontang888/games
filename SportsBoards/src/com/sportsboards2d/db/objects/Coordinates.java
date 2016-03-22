package com.sportsboards2d.db.objects;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class Coordinates{

	/*
	 * Variables + Getters/Setters
	 */
	
	private float x;
	public float getX(){ return this.x;}
	public void setX(float x){ this.x = x;}
	
	public float y;
	public float getY(){ return this.y;}
	public void setY(float y){ this.y = y;}
	
	/*
	 * Constructors
	 */
	
	public Coordinates(float x, float y){
		this.x = x;
		this.y = y;
	}
}