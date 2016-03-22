package com.sportsboards2d.db.objects;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

/*
 * This class stores data unique to the player
 */

public class PlayerInfo extends PlayerObject implements Parcelable{
	
	/*
	 * Variables + Getters/Setters
	 */
	
	private int jNum;
	private String type;
	private String pName;
	
	/*
	 * Constructors
	 */
	
	public PlayerInfo(int id, int num, String type, String name){
		
		super(id);
		this.setjNum(num);
		this.setType(type);
		this.pName = name;
	}
	
	public String getFirstName(){
		String[]result;
		result = pName.split("\\s+");
		return result[0];
	}
	
	public String getLastName(){
		String[]result;
		result = pName.split("\\s+");
		try{
			return result[1];
		}
		catch(ArrayIndexOutOfBoundsException oshit){
			return "";
		}
	}
	
	public String getInitials(){
		
		String result = "";
		
		for(int i = 0; i < this.pName.length()-1; i++){
			if(Character.isUpperCase(this.pName.charAt(i))){
				result += this.pName.charAt(i);
			}
		}
		return result;
	}
	
	public void setjNum(int jNum) {
		this.jNum = jNum;
	}
	public int getjNum() {
		return jNum;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public String getPName() {
		return pName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeInt(this.getpID());
		out.writeInt(jNum);
		out.writeString(type);
		out.writeString(pName);
		
	}
	public static final Parcelable.Creator<PlayerInfo> CREATOR = new Parcelable.Creator<PlayerInfo>() {
        @Override
		public PlayerInfo createFromParcel(Parcel in) {
            return new PlayerInfo(in);
        }

        @Override
		public PlayerInfo[] newArray(int size) {
            return new PlayerInfo[size];
        }
    };
    private PlayerInfo(Parcel in) {
        super(in.readInt());
        this.jNum = in.readInt();
        this.type = in.readString();
        this.pName = in.readString();
    }
}