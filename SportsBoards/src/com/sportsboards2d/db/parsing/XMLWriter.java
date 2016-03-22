package com.sportsboards2d.db.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.sportsboards2d.db.objects.FormationObject;
import com.sportsboards2d.db.objects.PlayerEntry;
import com.sportsboards2d.db.objects.PlayerInfo;
import com.sportsboards2d.db.objects.PlayerObject;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class XMLWriter{
	
	public XMLWriter(){}
		
	public String convertFormations(final Context context, List<FormationObject>forms){
		
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    String name = "";
	    
	    try {
			
	    	serializer.setOutput(writer);
	    	serializer.startDocument("UTF-8", true);
	    	serializer.startTag("", "forms");
	    	for(FormationObject fn: forms){
	    		name = fn.getfName();
		    	serializer.startTag("", "form");
		    	serializer.startTag("", "name");
		    	serializer.text(fn.getfName());
		    	
		    	serializer.endTag("", "name");
		    	serializer.startTag("", "ball");
		    	serializer.attribute("", "x", String.valueOf(fn.getBall().getX()));
		    	serializer.attribute("", "y", String.valueOf(fn.getBall().getY()));
		    	serializer.endTag("", "ball");
		    	for(PlayerObject pInfo:fn.getPlayers()){
		    		
		    		
			    	serializer.startTag("", "pEntry");
		    		serializer.startTag("", "pID");
		    		if(name.equalsIgnoreCase("Default")){
		    			serializer.attribute("", "id", "0");
		    		}
		    		else{
			    		serializer.attribute("", "id", String.valueOf(pInfo.getpID()));
		    		}
		    		serializer.endTag("", "pID");
		    		
		    		serializer.startTag("", "team");
		    		serializer.text(((PlayerEntry) pInfo).getpTeam());
		    		serializer.endTag("", "team");
		    		
		    		serializer.startTag("", "coords");
		    		serializer.attribute("", "x", String.valueOf(((PlayerEntry) pInfo).getX()));
		    		serializer.attribute("", "y", String.valueOf(((PlayerEntry) pInfo).getY()));
		    		serializer.endTag("", "coords");
		    		
		    		serializer.endTag("", "pEntry");
		    	}
		    	serializer.endTag("", "form");
	    	}
	    	serializer.endTag("", "forms");
	    	serializer.endDocument();
	    }
	    catch(IOException oshit){
	    	oshit.printStackTrace();
	    }
	    return writer.toString();   
	}
	
	public String convertPlayers(final Context context, List<PlayerInfo>players){
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    
	    try {
			
	    	serializer.setOutput(writer);
	    	serializer.startDocument("UTF-8", true);
	    	serializer.startTag("", "players");
	    	for(PlayerInfo p: players){
	
		    	serializer.startTag("", "player");
		    	
		    	serializer.startTag("", "pID");
		    	serializer.attribute("", "id", String.valueOf(p.getpID()));
		    	serializer.endTag("", "pID");
		    	
		    	serializer.startTag("", "jNum");
		    	serializer.attribute("", "num", String.valueOf(p.getjNum()));
		    	serializer.endTag("", "jNum");
		    	
		    	serializer.startTag("", "type");
		    	serializer.text(p.getType());
		    	serializer.endTag("", "type");
		    	
		    	serializer.startTag("", "pName");
		    	serializer.text(p.getPName());
		    	serializer.endTag("", "pName");
		    	
		    	serializer.endTag("", "player");
	    	}
	    	serializer.endTag("", "players");
	    	serializer.endDocument();
	    }
	    catch(IOException oshit){
	    	oshit.printStackTrace();
	    }
	    return writer.toString();   
	}
	
	public String convertStreamToString(InputStream input) throws IOException{
		
		if(input != null){
			Writer writer = new StringWriter();
			
			char[]buffer = new char[1024];
			
			try{
				Reader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				int n;
				while((n = reader.read(buffer)) != -1){
					writer.write(buffer, 0, n);
				}
			}finally{
				input.close();
			}
			return writer.toString();
		}
		else{
			return "";
		}
	}
	
}