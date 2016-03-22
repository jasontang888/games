package com.glowwormapps.gSnake;

import java.util.ArrayList;

class Maze {

	private int mWidth = 0;
	private int mHeight = 0;

    private ArrayList<Particle> mBalls = new ArrayList<Particle>();

    Maze(int width, int height, String type) {
    	reset(width, height, type);
    }
    
    //this depends on strings where it should use an ENUM
    //but sometimes life's to short so you just write 
    //HACK
    public void reset(int width, int height, String type){
        mWidth  =  width;
        mHeight  = height;
        
        mBalls.clear();
    	
        if(type.contentEquals("Full Box")){
    		full_box();
    	}
        if(type.contentEquals("Four Doors")){
    		four_doors();
    	}
        if(type.contentEquals("Snakes & Ladders")){
    		snakes_n_ladders();
    	}
    }
    private void full_box(){
    	int n,m;
    	//draw a box  
    	//at the edge of the grid as a maze
    	for(n= 0;n<=1;n++){
    		for( m=0 ; m<mHeight ; m++ ){
    		 mBalls.add( new Particle(n*(mWidth-1), m));
    		}
    	}
    	for(n= 0;n<=1;n++){
    		for( m=1 ; m<mWidth-1 ; m++ ){
    		 mBalls.add( new Particle(m, n*(mHeight-1)));
    		}
    	}
    }
    public void reflect(int width) {
    	for(int i= 0;i<mBalls.size();i++){
    		mBalls.get(i).MoveParticle(
    						width -1 - mBalls.get(i).GetX(),
    						mBalls.get(i).GetY());
    	}
    }
    private void four_doors(){
    	int n, m;
    	for(n= 0;n<=1;n++){
    		for( m=0 ; ((float)m)<(float)mHeight/3.0 ; m++ ){
    		 mBalls.add( new Particle(n*(mWidth-1), m));
    		}
    		for( m=(2*mHeight)/3 ; m<mHeight ; m++ ){
       		 mBalls.add( new Particle(n*(mWidth-1), m));
       		}
    	}
    	for(n= 0;n<=1;n++){
    		for( m=1 ; (float)m<(float)mWidth/3.0 ; m++ ){
    		 mBalls.add( new Particle(m, n*(mHeight-1)));
    		}
    		for( m=(2*mWidth)/3 ; m<mWidth-1 ; m++ ){
       		 mBalls.add( new Particle(m, n*(mHeight-1)));
       		}
    	}
    	
    }
    private void snakes_n_ladders(){
    	int n, m;
    	int hnum ,wstep;
    	if(mWidth<10){
    		hnum=3;
    		wstep = mWidth/3+1;
    	}else{
    		hnum=5;
    		wstep= mWidth/5+1;
    	}
    	//draw two vertical walls
    	for(n= 0;n<=1;n++){
    		for( m=0 ; m<mHeight ; m++ ){
    		mBalls.add( new Particle(n*(mWidth-1), m));
    		}
    	}
    	//and then the spokes of the ladder
    	for(n= 0; n<hnum; n++){
			for( m=wstep*(n%2) ; m<mWidth-wstep*((n+1)%2) ; m++ ){
   		 		mBalls.add( new Particle(m, (n*mHeight)/hnum));
			}
    	}
    }
    
    public int getParticleCount() {
        return mBalls.size();
    }

    public float getPosX(int i) {
        return mBalls.get(i).mPosX;
    }

    public float getPosY(int i) {
        return mBalls.get(i).mPosY;
    }
    public Particle getParticle(int i) {
        return mBalls.get(i);
    }
    public Boolean TestCollision(Particle p){
    	int i = mBalls.size()-1;
    	for(; i>=0 ; i--){
    		if (mBalls.get(i).TestCollision(p)){
    			return true;
    		}
    	}
    	return false;
    }
}

