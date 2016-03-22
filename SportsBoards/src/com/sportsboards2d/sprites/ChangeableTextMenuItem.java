package com.sportsboards2d.sprites;

import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class ChangeableTextMenuItem extends ChangeableText implements IMenuItem{
	
	private final int mID;
	
	public ChangeableTextMenuItem(final int pID, final Font pFont, final String pText) {
		super(0, 0, pFont, pText);
		this.mID = pID;
	}
	public ChangeableTextMenuItem(final int pID, final Font pFont, final String pText, final int pMaxLenght) {
		super(0, 0, pFont, pText, pMaxLenght);
		this.mID = pID;
	}
	@Override
	public void setText(String pText){
		super.setText(pText);
	}
	@Override
	public int getID(){
		return this.mID;
	}
	@Override
	public void onSelected(){}
	@Override
	public void onUnselected(){}
}

	