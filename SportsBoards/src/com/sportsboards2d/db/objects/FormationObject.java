package com.sportsboards2d.db.objects;

import java.util.List;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class FormationObject {
	//record of one Formation object. Stores the name, the coordinates of the ball, and
	//an array of all the players.
	private String fName;
	private Coordinates mBall;
	private List<PlayerObject> players;
	
	public FormationObject(String fName, Coordinates mBall, List<PlayerObject>players){
		this.fName = fName;
		this.mBall = mBall;
		this.players = players;
	}

	public void setBall(Coordinates mBall) {
		this.mBall = mBall;
	}

	public Coordinates getBall() {
		return mBall;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfName() {
		return fName;
	}

	public void setPlayers(List<PlayerObject> players) {
		this.players = players;
	}

	public List<PlayerObject> getPlayers() {
		return players;
	}
}
