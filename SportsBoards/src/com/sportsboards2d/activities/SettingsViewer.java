package com.sportsboards2d.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.sportsboards2d.R;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */
public class SettingsViewer extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
	
	/*
	 * Commits changes when a change is detected
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		
		SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.settings), 0).edit();

		if(key.equals(getString(R.string.lineEnabled))){
			boolean result = sharedPreferences.getBoolean(key, false);
			editor.putBoolean(key, result);			
		}
		else if(key.equals(getString(R.string.rTeamLineColor))){
			int result = Integer.parseInt(sharedPreferences.getString(key, "0"));
			editor.putInt(key, result);
		}
		else if(key.equals(getString(R.string.bTeamLineColor))){
			int result = Integer.parseInt(sharedPreferences.getString(key, "0"));
			editor.putInt(key, result);
		}
		else if(key.equals(getString(R.string.player_info_display))){
			boolean result = sharedPreferences.getBoolean(key, false);
			editor.putBoolean(key, result);	
		}
		else if(key.equals(getString(R.string.display_what))){
			int result = Integer.parseInt(sharedPreferences.getString(key, "0"));
			editor.putInt(key, result);
		}
		else if(key.equals(getString(R.string.display_when))){
			int result = Integer.parseInt(sharedPreferences.getString(key, "0"));
			editor.putInt(key, result);
		}
		else if(key.equals(getString(R.string.menuTextColor))){
			int result = Integer.parseInt(sharedPreferences.getString(key, "0"));
			editor.putInt(key, result);
		}
		else if(key.equals(getString(R.string.largePlayers))){
			System.out.println("here");
			int result = Integer.parseInt(sharedPreferences.getString(key, "1"));
			editor.putInt(key, result);
		}
		editor.commit();
	}
	@Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.settings), 0);
        // Setup the initial values
        ListPreference list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.rTeamLineColor));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.rTeamLineColor), 0));
        list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.bTeamLineColor));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.bTeamLineColor), 0));
        list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.display_when));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.display_when), 0));
        list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.display_what));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.display_what), 0));
        list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.menuTextColor));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.menuTextColor), 0));
        list = (ListPreference)getPreferenceScreen().findPreference(getString(R.string.largePlayers));
        list.setValueIndex(sharedPreferences.getInt(getString(R.string.largePlayers), 1));

        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);   
    }

	@Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }
}
