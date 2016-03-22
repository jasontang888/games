package com.sportsboards2d.boards;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import com.sportsboards2d.R;

import android.graphics.Color;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class SoccerBoard extends BaseBoard{
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public Engine onLoadEngine() {
		arrayID = R.array.soccer_positions;
		SPORT_NAME = getString(R.string.soccer_string);
		BALL_PATH_SMALL = "Soccer_Ball_32.png";
		return super.onLoadEngine();
	}
	
	@Override
	public void onLoadResources() {
		super.onLoadResources();	
		this.mBackGroundTextureRegion = TextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "soccer_field.jpg", 0, 0);
		this.mMenuFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuFont = FontFactory.createFromAsset(this.mMenuFontTexture, this, "VeraBd.ttf", 36, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTextures(this.mBackgroundTexture, this.mMenuFontTexture);
		this.mEngine.getFontManager().loadFont(this.mMenuFont);
	}
}
