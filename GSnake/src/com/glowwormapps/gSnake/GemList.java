package com.glowwormapps.gSnake;

import java.util.ArrayList;

import com.glowwormapps.gSnake.GravitoSnakePlayActivity.Direction;

public class GemList {
    private ArrayList<Gem> mGems = new ArrayList<Gem>();
    int mTurn =0; 
    
    GemList() {
    }
    
    public void progress(boolean enabled, int frequency, int life,
    					 int width, int height, 
    					 Maze maze ,Snake snake, Fruit fruit){
    	if(enabled){
    		if( mTurn > frequency ){
    			mTurn = 0;
    			Gem newGem = new Gem(1,1,life );
    			newGem.randomise(width, height, snake, maze, fruit, this);
    			mGems.add(newGem);
    		}
    	}
    	for(int i=0;i< mGems.size();i++){
    		mGems.get(i).progress();
    		if(mGems.get(i).getLife()<0){
    			mGems.remove(i);
    			i--;
    		}
    	}
    	mTurn ++;
    	
    }
    public Direction doEvent(int width, int height, 
			             Maze maze ,Snake snake, Fruit fruit,
			             Direction dir){
    	for(int i=0;i< mGems.size();i++){

    		if(mGems.get(i).TestCollision(snake.getParticle(0))){
    			
    			snake.awardPoints(5);
    			
    			switch( mGems.get(i).getType() ){
    			case(0):
    			    resetSnake(snake);
    				break;
    			case(1):
    				dir = reverseSnake(snake, dir);
   			     	break;
    			case(2):
    				dir = reflectObjects( width, maze, snake, fruit, dir);	
    				break;
    			}
    			
    			mGems.remove(i);
    			i--;
    		}
    	}
    	return dir;
    }
    private void resetSnake(Snake snake){
    	snake.resetLength();
    }
    private Direction reverseSnake(Snake snake, Direction dir){
    	return snake.reverse(dir);
    }
    private Direction reflectObjects(int width, Maze maze ,Snake snake, Fruit fruit,
    							Direction dir){
    	reflect(width);
    	maze.reflect(width);
    	snake.reflect(width);
    	fruit.MoveParticle(width-1-fruit.GetX(), fruit.GetY());
    	// change the direction so the snake doesn't end out eating itself
    	if(dir==Direction.LEFT)
    		return Direction.RIGHT;
    	if(dir==Direction.RIGHT)
    		return Direction.LEFT;
    	return dir;
    	
    }
    public void reflect(int width) {
    	for(int i= 0;i<mGems.size();i++){
    		mGems.get(i).MoveParticle(
    						width -1 - mGems.get(i).GetX(),
    						mGems.get(i).GetY());
    	}
    }
    public void reset(){
    	mTurn =0;
    	mGems.clear();
    }
    public int getParticleCount() {
        return mGems.size();
    }

    public float getPosX(int i) {
        return mGems.get(i).mPosX;
    }

    public float getPosY(int i) {
        return mGems.get(i).mPosY;
    }
    public int getType(int i){
    	return mGems.get(i).getType();
    }
    public int getLife(int i){
    	return mGems.get(i).getLife();
    }
    public Particle getParticle(int i) {
        return mGems.get(i);
    }
}

