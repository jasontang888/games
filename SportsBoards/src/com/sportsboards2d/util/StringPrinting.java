package com.sportsboards2d.util;

import java.util.List;

import com.sportsboards2d.db.objects.FormationObject;
import com.sportsboards2d.db.objects.Player;
import com.sportsboards2d.db.objects.PlayerInfo;
import com.sportsboards2d.db.objects.PlayerObject;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class StringPrinting {
	
	public static void printForm(FormationObject fn){
		
		System.out.println(fn.getfName());
		System.out.println("Xball: " + fn.getBall().getX());
		System.out.println("Yball: " + fn.getBall().getY());
		
		List<PlayerObject>players = fn.getPlayers();
		
		//Player p 
		
		for(PlayerObject p:players){
			System.out.println("ID: " + p.getpID());
			System.out.println("Team: " + ((Player)p).getpTeam());
			//System.out.println()
		}

	}
	
	public static void printAllFormation(List<FormationObject> forms){
		
		for(FormationObject fn:forms){
			System.out.println(fn.getfName());
			System.out.println(fn.getBall().getX() + " " + fn.getBall().getY());
			
			for(PlayerObject p:fn.getPlayers()){
				System.out.println(((Player) p).getX());
				System.out.println(p.getpID());
			}
			
			System.out.println(fn.getPlayers().size());
		}
	}
	
	public static void printPlayerInfo(PlayerInfo player){
		
		System.out.println("Player name: " + player.getPName());
		System.out.println("Player ID: " + player.getpID());
		System.out.println("Player jersey#: " + player.getjNum());
		System.out.println("Player position: " + player.getType());
	}

}
