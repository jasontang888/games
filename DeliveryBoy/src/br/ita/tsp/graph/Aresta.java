package br.ita.tsp.graph;

public class Aresta {
	
	private Vertice origem;
	private Vertice destino;
	private double peso;
	/**
	 * @param origem
	 * @param destino
	 * @param peso
	 */
	public Aresta(Vertice origem, Vertice destino, double peso) {
		this.origem = origem;
		this.destino = destino;
		this.peso = peso;
	}
	/**
	 * @return the origem
	 */
	public Vertice getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}
	/**
	 * @return the destino
	 */
	public Vertice getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Vertice destino) {
		this.destino = destino;
	}
	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	
}