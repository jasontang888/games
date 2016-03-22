package com.sportsboards2d.sprites;

import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.sportsboards2d.db.objects.Player;
import com.sportsboards2d.db.objects.PlayerInfo;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class PlayerSprite extends BaseSprite{
	
	private final int NAME_DISPLAY = 0;
	private final int POSITION_DISPLAY = 1;
	private final int NUMBER_DISPLAY = 2;
	
	public static int displayMode;
	private final int NIP = 0;
	private final int NI = 1;
	private final int NP = 2;
	private final int N = 3;
	private final int IP = 4;
	private final int P = 5;
	private final int I = 6;
	
	/*
	 * Variables + Setters
	 */
	
	private Player player;
	public Player getPlayer(){ return player;}
	
	/*
	 * Constructors
	 */
	
	public PlayerSprite(Player player, float x, float y, TiledTextureRegion tex){
		super(x, y, tex);
		this.player = player;
	}

	public ChangeableText getNameDisplay() {
		return this.displayInfo.get(NAME_DISPLAY);
	}
	public ChangeableText getPositionDisplay(){
		return this.displayInfo.get(POSITION_DISPLAY);
	}
	public ChangeableText getNumberDisplay(){
		return this.displayInfo.get(NUMBER_DISPLAY);
	}
	
	public void swap(PlayerInfo swap){
		
		player.setpInfo(swap);
		player.setpID(swap.getpID());
		
		System.out.println(swap.getpID());
		System.out.println(player.getpID());
		
		getNameDisplay().setText(player.getpInfo().getInitials());
		getPositionDisplay().setText(player.getpInfo().getType());
		getNumberDisplay().setText(String.valueOf(player.getpInfo().getjNum()));

	}
	
	public void displayInfo(boolean bool){
		
		this.detachChildren();
		
		if(bool){
			
			//+10, -20, TOP
			//+50, +10, RIGHT
			//+10, +50, BOTTOM
			
			
			
			switch(displayMode){
			
				case NIP:
					this.attachChild(getNameDisplay());
					this.attachChild(getPositionDisplay());
					this.attachChild(getNumberDisplay());
					break;
				case NI:
					this.attachChild(getNameDisplay());
					this.attachChild(getNumberDisplay());
					break;
				case NP:
					this.attachChild(getPositionDisplay());
					this.attachChild(getNumberDisplay());
					break;
				case N:
					this.attachChild(getNumberDisplay());
					break;
				case IP:
					this.attachChild(getNameDisplay());
					this.attachChild(getPositionDisplay());
					break;
				case I:
					this.attachChild(getPositionDisplay());
					break;
				case P:
					//this.setPosition(+10, +15);
					this.attachChild(getNameDisplay());
					break;
			}
			
		}
	}
}