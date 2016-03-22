package com.sportsboards2d.launcher;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sportsboards2d.R;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public class Launcher extends ListActivity{
	
	private LauncherMenuAdapter mListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.launcher_menu);

		 mListAdapter = new LauncherMenuAdapter(this); 
		 this.setListAdapter(mListAdapter);
		 //requests an ad. This ad will be put at the bottom of the launcher screen
		 final TextView txtVersion = (TextView)findViewById(R.id.text_version);
		 txtVersion.setText(R.string.app_version);
		 //final AdView adView = (AdView)findViewById(R.id.adView);
		 //adView.loadAd(new AdRequest());
	}
	
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id){
		super.onListItemClick(lv, v, position, id);
		Activity act = (Activity) this.getListAdapter().getItem(position);
		this.startActivity(new Intent(this, act.Class));
		this.finish();
	}	
}