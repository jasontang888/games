package br.ita.tsp.util;

public class GameUtils {
	
	public static float calculateScore(long tempo_inicial, float tsp, 
			float distancia_total, int level){
		long tempoFinal = System.currentTimeMillis() ;
		long time = (tempoFinal - tempo_inicial)/1000;
		float erro = (Math.abs(tsp-distancia_total))/tsp;
		float score = ((1-erro) * (level + 1)*5000);
		if (time!=0)
			score = (score / new Long(time).floatValue());
		return score;
	}
	
		

}
