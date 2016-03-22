package com.glowwormapps.gSnake;

import java.util.ArrayList;

import android.preference.PreferenceManager;
import android.util.Log;

import com.glowwormapps.gSnake.GravitoSnakePlayActivity.Direction;

class Snake {
    private int initial_particles = 6;
    static final int INTIAL_POSX = 6;
    static final int INTIAL_POSY = 6;
    
    private int score = 0;
    PreferenceManager mPrefs;
    private ArrayList<Particle> mBalls = new ArrayList<Particle>();

    Snake(int length) {
        /*
         * Initially our particles are all in the same position
         */
    	
    	initialise( length );
    	
    	
    }
    public void initialise(int length){
    	initial_particles = length;
        for (int i = 0; i <initial_particles; i++) {
            mBalls.add( new Particle(INTIAL_POSX,INTIAL_POSY) );
        }

    }
    public void reset(int length){
    	mBalls.clear();
    	initialise(length);
    	score = 0;
    }
    public void IncreaseLength(int amount){
    	
    	int n,x,y;
    	for(n=0 ; n < amount; n++){
    		x = mBalls.get(mBalls.size()-1).GetX();
    		y = mBalls.get(mBalls.size()-1).GetY();
    		mBalls.add(new Particle(x,y));
    	}
    	score++;
    }
    /*
     * 
     */
   public void SetPosition(int x, int y) {
    	int i = mBalls.size()-1;
    	for(; i>=0 ; i--){
    		mBalls.get(i).MoveParticle( x, y );
    	}
    }
    	
    public void updatePositions(Direction d, int xSize, int ySize) {
    	int i = mBalls.size()-1;
    	for(; i>0 ; i--){
    		mBalls.get(i).MoveParticle( mBalls.get(i-1).GetX() , mBalls.get(i-1).GetY() );
    	}
    	
    	Particle first = mBalls.get(0); 
    	
    	switch(d){
    	case UP:
    		first.MoveParticle( mBalls.get(0).GetX() , mBalls.get(0).GetY()+1 );
    		break;
    		
    	case DOWN:
    		first.MoveParticle( mBalls.get(0).GetX() , mBalls.get(0).GetY()-1 );
    		break;
    	case LEFT:
    		first.MoveParticle( mBalls.get(0).GetX()-1 , mBalls.get(0).GetY() );
    		break;
    	case RIGHT:
    	default:
    		first.MoveParticle( mBalls.get(0).GetX()+1 , mBalls.get(0).GetY() );
    		break;
    	}
    	
    	if(first.GetX() < 0){
    		first.MoveParticle(xSize-1, first.GetY());
    	}
    	if(first.GetX() >= xSize){
    		first.MoveParticle(0, first.GetY());
    	}
       	if(first.GetY() < 0){
    		first.MoveParticle(first.GetX(), ySize - 1);
    	}
       	if(first.GetY() >= ySize){
    		first.MoveParticle(first.GetX(), 0 );
    	}

    }
    public void resetLength() {
    	if(initial_particles<mBalls.size()){
    		mBalls.subList(initial_particles,mBalls.size()).clear();
    	}
    }
    public Direction reverse(Direction d) {
    Particle tmp;
    	for(int i= 0;i<mBalls.size()/2;i++){
    		tmp= mBalls.get(i);
    		mBalls.set(i, mBalls.get(mBalls.size()-1-i));
    		mBalls.set(mBalls.size()-1-i, tmp);
    	}
        for(int i= 1;i<mBalls.size();i++){

    		if(mBalls.get(0).GetX()<mBalls.get(i).GetX()){
    			Log.i("LEFT","LEFT");
    			return Direction.LEFT;
    		}
    		if(mBalls.get(0).GetX()>mBalls.get(i).GetX()){
    			Log.i("RIGHT","RIGHT");
    			return Direction.RIGHT;
    		}
    		if(mBalls.get(0).GetY()<mBalls.get(i).GetY()){
    			Log.i("DOWN","DOWN");
    			return Direction.DOWN;
    		}
    		if(mBalls.get(0).GetY()>mBalls.get(i).GetY()){
    			Log.i("UP","UP");
    			return Direction.UP;
    		}
    	}
    	return d;
    }
    public void reflect(int width) {
    	for(int i= 0;i<mBalls.size();i++){
    		mBalls.get(i).MoveParticle(
    						width -1 - mBalls.get(i).GetX(),
    						mBalls.get(i).GetY());
    	}
    }
    public int getParticleCount() {
        return mBalls.size();
    }
    public int getInitialParticleCount() {
    	return  initial_particles;
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
    public int getScore() {
        return score;
    }
    public void awardPoints(int p) {
        score+=p;
    }
    public Boolean TestCollision( Maze maze){
    	int i = 1;
    	
    	Particle first = mBalls.get(0); 
       	
       	if(maze.TestCollision(first)){
       		return true;
       	}

    	for( ; i<mBalls.size(); i++ ){
    		if(first.TestCollision(mBalls.get(i))){
    			return true;
    		}
    	}
    	return false;
    }
}
