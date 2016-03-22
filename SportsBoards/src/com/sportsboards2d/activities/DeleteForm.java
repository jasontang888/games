/**
 * 
 */
package com.sportsboards2d.activities;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.sportsboards2d.R;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class DeleteForm extends ListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		setResult(-1, null);
		String[] list;
		list = getIntent().getStringArrayExtra("list");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
	
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
				builder.setMessage(R.string.alert_dialog_confirm)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						setResult(position, null);
						DeleteForm.this.finish();
					}
				})
				.setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			});
		}
}
