package br.ita.tsp;


import java.util.StringTokenizer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Score extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private TextView tv, tv1, tv2, tv3, tv4, tv5, tv6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);

		tv = (TextView) findViewById(R.id.back);
		tv.setTypeface(Menu.tf);
		tv.setOnTouchListener(new CustomTouchListener());
		tv.setOnClickListener(this);

		tv1 = (TextView) findViewById(R.id.sc1);
		tv2 = (TextView) findViewById(R.id.sc2);
		tv3 = (TextView) findViewById(R.id.sc3);
		tv4 = (TextView) findViewById(R.id.sc4);
		tv5 = (TextView) findViewById(R.id.sc5);
		tv6 = (TextView) findViewById(R.id.sc6);

		tv1.setTypeface(Menu.tf);
		tv2.setTypeface(Menu.tf);
		tv3.setTypeface(Menu.tf);
		tv4.setTypeface(Menu.tf);
		tv5.setTypeface(Menu.tf);
		tv6.setTypeface(Menu.tf);

		getRecords();

	}

	private void getRecords() {
		// Colocar aqui os scores !!!
		tv1.setText("--------------------");
		tv2.setText("--------------------");
		tv3.setText("--------------------");
		tv4.setText("--------------------");
		tv5.setText("--------------------");
		tv6.setText("--------------------");

		SharedPreferences set = getSharedPreferences(Menu.PREFS_NAME, 0);
		
		String score= set.getString("score1", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv1.setText(scores[0]+ "           "+ scores[1]);
		}

		score= set.getString("score2", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv2.setText(scores[0]+ "           "+ scores[1]);
		}

		score= set.getString("score3", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv3.setText(scores[0]+ "           "+ scores[1]);
		}
		
		score= set.getString("score4", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv4.setText(scores[0]+ "           "+ scores[1]);
		}
		
		score= set.getString("score5", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv5.setText(scores[0]+ "           "+ scores[1]);
		}
		
		score= set.getString("score6", "--------------------");
		if (score.equals("--------------------")==false){
			String scores[]=parsePlayerString(score);
			tv6.setText(scores[0]+ "           "+ scores[1]);
		}
	}
	
	
	public static String[] parsePlayerString(String playerStr){
		StringTokenizer str = new StringTokenizer(playerStr,"++++");
		String rec []= new String[2];
		rec[0]= str.nextToken();
		rec[1]=str.nextToken();
		return rec;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}
}
