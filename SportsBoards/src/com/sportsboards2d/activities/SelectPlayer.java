/**
 * 
 */
package com.sportsboards2d.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class SelectPlayer extends ListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[]list = getIntent().getStringArrayExtra("list");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));  
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			
		    @Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		    	setResult(position, null);
		    	SelectPlayer.this.finish();
		    }
		});
	}
}