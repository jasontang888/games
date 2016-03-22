package com.sportsboards2d.db.objects;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public abstract class PlayerObject {
	
	private int pID;
	
	public PlayerObject(int pID){
		this.pID = pID;
	}

	public void setpID(int pID) {
		this.pID = pID;
	}

	public int getpID() {
		return pID;
	}

}
