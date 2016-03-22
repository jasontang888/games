/*
 *  G - Snake
 *
 *  Copyright (C) 2012 Glow Worm Applications
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Street #330, Boston, MA 02111-1307, USA.
 */

package com.glowwormapps.gSnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SpeedButtonsPreference extends Preference {

 
 public static int slow    = 200;
 public static int medium    = 400;
 public static int fast   = 600;
 
 
 
 public SpeedButtonsPreference(Context context) {
  super(context);
 }
 
 public SpeedButtonsPreference(Context context, AttributeSet attrs) {
  super(context, attrs);
 }
 
 public SpeedButtonsPreference(Context context, AttributeSet attrs, int defStyle) {
  super(context, attrs, defStyle);
 }
   
 @Override
 protected View onCreateView(ViewGroup parent){

  final float width = parent.getWidth();

	 
	 
   LinearLayout layout = new LinearLayout(getContext());
    
   
   layout.setPadding(5, 5, 5 , 5);
   layout.setOrientation(LinearLayout.HORIZONTAL);
   
   
   Button bSlow = new Button(getContext());
   bSlow.setText("Slow");
   bSlow.setWidth((int) (width/3));
   bSlow.setPadding(0, 0, 5, 0);
   bSlow.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
    	   updatePreference(slow);
       }
   });
   
   Button bMed = new Button(getContext());
   bMed.setText("Medium");
   bMed.setWidth((int) (width/3));
   bMed.setPadding(5, 0, 5, 0);
   bMed.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
    	   updatePreference(medium);
       }
   });

   Button bFast = new Button(getContext());
   bFast.setText("Fast");
   bFast.setWidth((int) (width/3));
   bFast.setPadding(5, 0, 0, 0);
   bFast.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
    	   updatePreference(fast);
       }
   });
   
   layout.addView(bSlow);
   layout.addView(bMed);
   layout.addView(bFast);
   
   layout.setId(android.R.id.widget_frame);
   
   
   return layout; 
 }
 

 @Override 
 protected Object onGetDefaultValue(TypedArray ta,int index){
  
	 int dValue = (int)ta.getInt(index,50);
    
	 return dValue;
 }
 

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
     
    	int temp = restoreValue ? getPersistedInt(50) : (Integer)defaultValue;
     
    	if(!restoreValue)
        persistInt(temp);
    }
 
     
private void updatePreference(int newValue){
  
	 	SharedPreferences.Editor editor =  getEditor();
  		editor.putInt(getKey(), newValue);
  		editor.commit();
 	}
 
}

