package br.ita.tsp.db;

import java.util.Comparator;

public class PlayerComparator implements Comparator{

	@Override
	public int compare(Object obj1, Object obj2) {
		Player play1 = (Player)obj1;
		Player play2 = (Player)obj2;
		
		  if(play1.getRecord() == play2.getRecord()) {
	            return 0;    
	        } else if(play1.getRecord() < play2.getRecord()) {
	            return -1;
	        } else if( play1.getRecord() >play2.getRecord()) {
	            return 1;
	        }
		return 0;
	}

}
