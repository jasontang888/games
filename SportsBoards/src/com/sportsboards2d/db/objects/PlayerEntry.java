package com.sportsboards2d.db.objects;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

/*
 * This class is used when reading/writing to the database
 */
public class PlayerEntry extends PlayerObject{
	
	private String pTeam;
	private Coordinates coords;
	
	public PlayerEntry(int pID, String pTeam, Coordinates coords){
		super(pID);
		this.setpTeam(pTeam);
		this.setCoords(coords);
	}
	
	public void setpTeam(String pTeam) {
		this.pTeam = pTeam;
	}
	public String getpTeam() {
		return pTeam;
	}
	public void setCoords(Coordinates coords) {
		this.coords = coords;
	}
	public Coordinates getCoords() {
		return coords;
	}

	public void setX(float x) {
		this.coords.setX(x);
	}

	public float getX() {
		return this.coords.getX();
	}

	public void setY(float y) {
		this.coords.setY(y);
	}

	public float getY() {
		return this.coords.getY();
	}
}
