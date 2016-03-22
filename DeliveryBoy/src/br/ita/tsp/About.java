package br.ita.tsp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class About extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	
	private TextView tv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);        
        
        tv = (TextView) findViewById(R.id.back);
        tv.setTypeface(Menu.tf);
        tv.setOnTouchListener(new CustomTouchListener());
        tv.setOnClickListener(this);
    }
        
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.back:
				finish();
				break;
		}
	}
}