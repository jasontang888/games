package com.sportsboards2d.db.parsing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;

import com.sportsboards2d.db.objects.FormationObject;
import com.sportsboards2d.db.objects.PlayerInfo;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class XMLAccess{
	
	public static void writeFormations(final Context context, List<FormationObject>forms, String path){
		
		XMLWriter writer = new XMLWriter();
		String output;
		FileOutputStream fOut;
		
		try {
			fOut = context.openFileOutput(path + "forms", Context.MODE_PRIVATE);
			output = writer.convertFormations(context, forms);
			fOut.write(output.getBytes());
			fOut.close();
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void writePlayers(final Context context, List<PlayerInfo>players, String path){
		
		XMLWriter writer = new XMLWriter();
		String output;
		FileOutputStream fOut;
		
		try {
			fOut = context.openFileOutput(path + "players", Context.MODE_PRIVATE);
			output = writer.convertPlayers(context, players);
			fOut.write(output.getBytes());
			fOut.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static List<FormationObject> loadFormations(final Context context, String path){
		
		List<FormationObject> formEntries = null;
		InputStream input = null;	
		XMLReader parser = new XMLReader();
		
		try{
			input = new FileInputStream(context.getFilesDir() + "/" + path + "forms");
			formEntries = parser.parseFormation(input);
			input.close();
			}
		catch(IOException oshit){
			oshit.printStackTrace();
			input = null;
		}
		if(input == null){
			
			try {
				
				input = context.getAssets().open("database/" + path + "/formations.xml");
				formEntries = parser.parseFormation(input);
				input.close();
				
				writeFormations(context, formEntries, path);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return formEntries;
	}
	
	public static List<PlayerInfo> loadPlayers(final Context context, String path){
		
		XMLReader reader = new XMLReader();
		InputStream input = null;
		List<PlayerInfo>players = null;
		
		try{
			input = new FileInputStream(context.getFilesDir() + "/" + path + "players");
			players = reader.parsePlayers(input);
			input.close();
		}
		catch(IOException oshit){
			oshit.printStackTrace();
			input = null;
		}
		
		if(input == null){
			
			try {
				input = context.getAssets().open("database/" + path + "/players.xml");
				players = reader.parsePlayers(input);
				input.close();
				writePlayers(context, players, path);
			}
			catch(IOException oshit){
				oshit.printStackTrace();
			}
		}
		return players;
	}
	
}