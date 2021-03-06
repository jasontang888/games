package com.emptyyourmind.entity;

import org.anddev.andengine.entity.layer.ILayer;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class JetsSprite extends Sprite
{
	private SpriteOnPosistionChangedActionsAggregator slAggregator;
	
	public JetsSprite(float pX, float pY, TextureRegion pTextureRegion, ILayer layer)
	{
		super(pX, pY, pTextureRegion);
		layer.addEntity(this);
	}

	@Override
	protected void onPositionChanged()
	{
		super.onPositionChanged();
		if(slAggregator != null)
		{
			slAggregator.onPositionChanged();
		}
	}
	
	public void setSlAggregator(SpriteOnPosistionChangedActionsAggregator slAggregator)
	{
		this.slAggregator = slAggregator;
	}

	public void setiShootable(IShootable iShootable)
	{
		iShootable.shoot();
	}

}
