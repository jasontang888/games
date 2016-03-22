package br.ita.tsp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.util.Log;
import br.ita.tsp.graph.Vertice;

public class SpriteVertice {
	private Set <Vertice> cities;
	
	public SpriteVertice(HashSet <Vertice> cities){
		this.cities=cities;
	}
	
	public Vertice getVertice(int code){
		for (Iterator<Vertice> it = cities.iterator(); it.hasNext(); ) {  
	        Vertice vert = it.next();
	        if (vert.getName()==code){
	        	Log.d("vertices_sprite", "retorna o vertice solicitado");
	        	return vert;
	        }
		}
    	Log.d("vertices_sprite", "retorna null");
		return null;
	}
	
	public int getNumeroVerticesNaoVisitados(){
		int nao_visitados = cities.size();
    	Log.d("vertices_sprite", "total de cidades nao visitadas eh "+nao_visitados);
		return nao_visitados;
	}
	
	/**retorna falso se o vertice nao existe ou se ja foi visitado*/
	public boolean visitaVertice(float x, float y){
		for (Iterator<Vertice> it = cities.iterator(); it.hasNext(); ) {  
	        Vertice vert = it.next();
	        float vert_x=vert.getPosX();
	        float vert_y=vert.getPosY();
	        
	        if((vert_x==x)&&(vert_y==y)){
	        	Log.d("vertices_sprite", "os pontos sao um vertice e nao foram visitados");
	        	it.remove();
	        	return true;
	        }
	    }
		
		Log.d("vertices_sprite", "os pontos ja foram visitados ou nao sao um vertice");
        return false;
	}
	
}
