package br.ita.tsp.graph;

import java.util.HashSet;

import android.content.SharedPreferences;
import br.ita.tsp.Game;
import br.ita.tsp.Menu;
import br.ita.tsp.util.Constants;


public class Grafo {
	
	private HashSet <Vertice> cities;
	private float tsp;
	private int total_cities;
	private int fase;
	private int user_difficult;
	private float combustivel;
	private static Grafo instance=null;
	
	
	public static Grafo getInstance(int userDifficult){
		if(instance==null)
			instance=new Grafo(userDifficult);
		return instance;
	}
	
	private Grafo(int userDifficult){
		tsp=0;
		total_cities=0;
		fase=0;
		combustivel=0;
		user_difficult=userDifficult;
	}
	
	public void initGrafo(boolean win){
		if (win==true){
			fase=fase+1;
		}
		
		if(fase==1){
			this.generateGrafo_1();	
		}
		else if(fase==2){
			this.generateGrafo_2();
		}
		
		else if(fase==3){
			this.generateGrafo_3();
		}
		
		else if(fase==4){
			this.generateGrafo_4();
		}
		
		else {
			this.generateGrafo_0();
		}
	}
	
	
	public int getFase(){
		return fase;
	}
	
	public int getNumeroCidades(){
		return this.total_cities;
	}
	
	public float getCombustivel(){
		return this.combustivel;
	}
	
	public float getTSP(){
		return tsp;
	}
	
	public HashSet <Vertice> getCidades(){
		return this.cities;
	}
	
	
	
	private float calculateCombustivel(){
		float comb =0f;
		if (user_difficult==0){
			//FACIL
			if(fase==0)
				comb = tsp+(1.4f*tsp);
			else if (fase==1)
				comb = 2*tsp;
			else if(fase==2)
				comb = tsp+(0.9f*tsp);
			else if (fase==3)
				comb = tsp+(0.8f*tsp);
			else if (fase==4)
				comb=tsp+(0.7f*tsp);
			else 
				comb = tsp+(0.2f*tsp);
		}
		
		else {
			if(fase==0)
				comb = tsp+(0.9f*tsp);
			else if (fase==1)
				comb = tsp+(0.7f*tsp);
			else if(fase==2)
				comb = tsp+(0.5f*tsp);
			else if (fase==3)
				comb = tsp+(0.2f*tsp);
			else if (fase==4)
				comb=tsp+(0.1f*tsp);
			else 
				comb = tsp;
		}
		
		return comb;
	}
	
	
	private void generateGrafo_0(){
		fase=0;
		cities = new HashSet<Vertice>();
		cities.add(new Vertice(5.0f*Constants.GRAFO_CONST, 2.5f*Constants.GRAFO_CONST, 1, false));
		cities.add(new Vertice(6.3f*Constants.GRAFO_CONST, 5.5f*Constants.GRAFO_CONST, 2, false));
		cities.add(new Vertice(7.5f*Constants.GRAFO_CONST, 7.0f*Constants.GRAFO_CONST, 3, false));
		cities.add(new Vertice(8.8f*Constants.GRAFO_CONST, 4.0f*Constants.GRAFO_CONST, 4, false));
		cities.add(new Vertice(10.0f*Constants.GRAFO_CONST, 1.0f*Constants.GRAFO_CONST, 5, false));
		total_cities=5;
		tsp = 22.0f*Constants.GRAFO_CONST;
		combustivel=calculateCombustivel();
	}
	
	private void generateGrafo_1(){
		cities = new HashSet<Vertice>();
		cities.add(new Vertice(5.0f*Constants.GRAFO_CONST, 2.5f*Constants.GRAFO_CONST, 1, false));
		cities.add(new Vertice(5.8f*Constants.GRAFO_CONST, 5.0f*Constants.GRAFO_CONST, 2, false));
		cities.add(new Vertice(6.7f*Constants.GRAFO_CONST, 7.0f*Constants.GRAFO_CONST, 3, false));
		cities.add(new Vertice(7.5f*Constants.GRAFO_CONST, 6.0f*Constants.GRAFO_CONST, 4, false));
		cities.add(new Vertice(8.3f*Constants.GRAFO_CONST, 1.0f*Constants.GRAFO_CONST, 5, false));
		cities.add(new Vertice(8.6f*Constants.GRAFO_CONST, 3.0f*Constants.GRAFO_CONST, 6, false));
		cities.add(new Vertice(10.0f*Constants.GRAFO_CONST, 4.0f*Constants.GRAFO_CONST, 7, false));
		total_cities=7;
		tsp = 22.0f*Constants.GRAFO_CONST;
		combustivel=calculateCombustivel();
	}
	
	private void generateGrafo_2(){
		cities = new HashSet<Vertice>();
		cities.add(new Vertice(5.0f*Constants.GRAFO_CONST, 1.8f*Constants.GRAFO_CONST, 1, false));
		cities.add(new Vertice(5.6f*Constants.GRAFO_CONST, 2.5f*Constants.GRAFO_CONST, 2, false));
		cities.add(new Vertice(6.3f*Constants.GRAFO_CONST, 7.8f*Constants.GRAFO_CONST, 3, false));
		cities.add(new Vertice(6.9f*Constants.GRAFO_CONST, 5.5f*Constants.GRAFO_CONST, 4, false));
		cities.add(new Vertice(7.5f*Constants.GRAFO_CONST, 4.0f*Constants.GRAFO_CONST, 5, false));
		cities.add(new Vertice(8.1f*Constants.GRAFO_CONST, 7.0f*Constants.GRAFO_CONST, 6, false));
		cities.add(new Vertice(8.8f*Constants.GRAFO_CONST, 4.8f*Constants.GRAFO_CONST, 7, false));
		cities.add(new Vertice(9.4f*Constants.GRAFO_CONST, 8.5f*Constants.GRAFO_CONST, 8, false));
		cities.add(new Vertice(10.0f*Constants.GRAFO_CONST, 1.0f*Constants.GRAFO_CONST, 9, false));
		total_cities=9;
		tsp = 30.0f*Constants.GRAFO_CONST;
		combustivel=calculateCombustivel();
	}
	
	
	private void generateGrafo_3(){
		cities = new HashSet<Vertice>();
		cities.add(new Vertice(5.0f*Constants.GRAFO_CONST, 1.7f*Constants.GRAFO_CONST, 1, false));
		cities.add(new Vertice(5.6f*Constants.GRAFO_CONST, 1.0f*Constants.GRAFO_CONST,2, false));
		cities.add(new Vertice(6.1f*Constants.GRAFO_CONST, 7.0f*Constants.GRAFO_CONST, 3, false));
		cities.add(new Vertice(6.7f*Constants.GRAFO_CONST, 3.7f*Constants.GRAFO_CONST, 4, false));
		cities.add(new Vertice(7.2f*Constants.GRAFO_CONST, 4.3f*Constants.GRAFO_CONST, 5, false));
		cities.add(new Vertice(7.8f*Constants.GRAFO_CONST, 6.3f*Constants.GRAFO_CONST, 6, false));
		cities.add(new Vertice(8.3f*Constants.GRAFO_CONST, 5.0f*Constants.GRAFO_CONST, 7, false));
		cities.add(new Vertice(8.9f*Constants.GRAFO_CONST, 7.7f*Constants.GRAFO_CONST, 8, false));
		cities.add(new Vertice(9.4f*Constants.GRAFO_CONST, 3.0f*Constants.GRAFO_CONST, 9, false));
		cities.add(new Vertice(10.0f*Constants.GRAFO_CONST, 8.3f*Constants.GRAFO_CONST, 10, false));
		total_cities=10;
		tsp = 29.0f*Constants.GRAFO_CONST;
		combustivel = calculateCombustivel();
	}
	
	private void generateGrafo_4(){
		cities = new HashSet<Vertice>();
		cities.add(new Vertice(5.0f*Constants.GRAFO_CONST, 1.7f*Constants.GRAFO_CONST, 1, false));
		cities.add(new Vertice(5.6f*Constants.GRAFO_CONST, 2.3f*Constants.GRAFO_CONST, 2, false));
		cities.add(new Vertice(6.1f*Constants.GRAFO_CONST, 4.3f*Constants.GRAFO_CONST, 3, false));
		cities.add(new Vertice(6.7f*Constants.GRAFO_CONST, 7.7f*Constants.GRAFO_CONST, 4, false));
		cities.add(new Vertice(7.2f*Constants.GRAFO_CONST, 3.0f*Constants.GRAFO_CONST, 5, false));
		cities.add(new Vertice(7.8f*Constants.GRAFO_CONST, 1.0f*Constants.GRAFO_CONST, 6, false));
		cities.add(new Vertice(8.3f*Constants.GRAFO_CONST, 9.0f*Constants.GRAFO_CONST, 7, false));
		cities.add(new Vertice(8.9f*Constants.GRAFO_CONST, 3.7f*Constants.GRAFO_CONST, 8, false));
		cities.add(new Vertice(9.4f*Constants.GRAFO_CONST, 7.0f*Constants.GRAFO_CONST, 9, false));
		cities.add(new Vertice(10.0f*Constants.GRAFO_CONST, 5.0f*Constants.GRAFO_CONST, 10, false));
		total_cities=10;
		tsp = 27.2f*Constants.GRAFO_CONST;
		combustivel = calculateCombustivel();
	}


	
}
