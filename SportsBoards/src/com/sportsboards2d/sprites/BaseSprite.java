/**
 * 
 */
package com.sportsboards2d.sprites;

import java.util.List;

import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.SmartList;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public abstract class BaseSprite extends AnimatedSprite implements IOnMenuItemClickListener{
	
	private boolean mGrabbed;
	private float startX, startY;
	protected List<ChangeableText> displayInfo;
	
	public BaseSprite(float pX, float pY, TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		displayInfo = new SmartList<ChangeableText>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
		
		return false;		
	}

	public void setmGrabbed(boolean mGrabbed) {
		this.mGrabbed = mGrabbed;
	}

	public boolean ismGrabbed() {
		return mGrabbed;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getStartY() {
		return this.startY;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartX() {
		return this.startX;
	}

	public void addDisplayInfo(ChangeableText displayInfo) {
		this.displayInfo.add(displayInfo);
	}
	public ChangeableText getDisplayInfo(int index) {
		return displayInfo.get(index);
	}
}
