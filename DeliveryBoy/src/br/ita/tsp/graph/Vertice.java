package br.ita.tsp.graph;

public class Vertice {
	
	private float posX;
	private float posY;
	private int city_position;
	private boolean visitited;
	
	/**
	 * @param posX
	 * @param posY
	 * @param visitited
	 */
	public Vertice(float posX, float posY, int city_position, boolean visitited) {
		this.posX = posX;
		this.posY = posY;
		this.city_position= city_position;
		this.visitited = visitited;
	}
	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(float posY) {
		this.posY = posY;
	}
	/**
	 * @return the name
	 */
	public int getName() {
		return city_position;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(int  city_position) {
		this.city_position= city_position;
	}
	/**
	 * @return the visitited
	 */
	public boolean isVisitited() {
		return visitited;
	}
	/**
	 * @param visitited the visitited to set
	 */
	public void setVisitited(boolean visitited) {
		this.visitited = visitited;
	}
	

}