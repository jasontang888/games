package com.sportsboards2d.db.objects;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

/*
 * Each PlayerSprite has a Player. Each Player has a PlayerInfo
 */
public class Player extends PlayerEntry{

	private PlayerInfo pInfo;
	
	public Player(int pID, String pTeam, Coordinates xy, PlayerInfo pInfo) {
		super(pID, pTeam, xy);
		this.pInfo = pInfo;
	}
	public void setpInfo(PlayerInfo pInfo) {
		this.pInfo = pInfo;
	}
	public PlayerInfo getpInfo() {
		return pInfo;
	}
}
