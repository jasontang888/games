/**
 * 
 */
package com.sportsboards2d.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.sportsboards2d.R;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class SaveForm extends ListActivity{
 
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.saveformation);
		 String[]list = getIntent().getStringArrayExtra("list");
		 setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		 ListView lv = getListView();
		 lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				setResult(position, null);
				SaveForm.this.finish();
			}
		 });
	}
	
	public void saveClicked(View v){
		Intent intent = new Intent();
		EditText name = (EditText) findViewById(R.id.enter_form_name);
		intent.setType(name.getText().toString());
		setResult(1, intent);
		SaveForm.this.finish();
	}
}
