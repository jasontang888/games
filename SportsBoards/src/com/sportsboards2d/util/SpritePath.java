package com.sportsboards2d.util;

import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class SpritePath {
	
	private AnimatedSprite sprite;
	private PathModifier.Path path;
	
	public SpritePath(AnimatedSprite sprite, PathModifier.Path path1){
		this.setSprite(sprite);
		this.setPath(path1);
	}

	public void setSprite(AnimatedSprite sprite) {
		this.sprite = sprite;
	}

	public AnimatedSprite getSprite() {
		return sprite;
	}

	public void setPath(PathModifier.Path path) {
		this.path = path;
	}

	public PathModifier.Path getPath() {
		return path;
	}

}
