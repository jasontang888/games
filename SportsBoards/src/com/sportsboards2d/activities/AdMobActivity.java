/**
 * 
 */
package com.sportsboards2d.activities;

import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.sportsboards2d.R;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public abstract class AdMobActivity extends BaseGameActivity{
	
	protected boolean showInCenter=true;
	protected boolean testMode=false;
	
	@Override 
	protected void onSetContentView() {
	
		if(testMode){

			//AdManager.setTestDevices( new String[] {

				//	AdManager.TEST_EMULATOR,

//					"FDF5AA30500D9CBDE7CC910D1A529F77",

	//		} );

		}

		final RelativeLayout relativeLayout = new RelativeLayout(this);

		final FrameLayout.LayoutParams relativeLayoutLayoutParams =

			new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
										 LayoutParams.FILL_PARENT);


		final AdView adView = (AdView)this.findViewById(R.id.adlayout);

		adView.refreshDrawableState();

		adView.setVisibility(View.VISIBLE);
		
		AdRequest request = new AdRequest();

		adView.loadAd(request);

		this.mRenderSurfaceView = new RenderSurfaceView(this);

		mRenderSurfaceView.setRenderer(mEngine);



		final android.widget.RelativeLayout.LayoutParams surfaceViewLayoutParams =

			new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());

		surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);



		final android.widget.RelativeLayout.LayoutParams adViewLayoutParams=

			new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());

		if(showInCenter){
			adViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		}
		adViewLayoutParams.height=LayoutParams.WRAP_CONTENT;
		adViewLayoutParams.width=LayoutParams.FILL_PARENT;
		relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
		relativeLayout.addView(adView, adViewLayoutParams);
		this.setContentView(relativeLayout, relativeLayoutLayoutParams);       
	}
	protected boolean isShowInCenter() {
		return showInCenter;
	}
	protected void setShowInCenter(boolean showInCenter) {
		this.showInCenter = showInCenter;
	}
	protected boolean isTestMode() {

		return testMode;

	}

	protected void setTestMode(boolean testMode) {

		this.testMode = testMode;

	}
}
