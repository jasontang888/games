package com.glowwormapps.gSnake;


class Fruit extends Particle{
	
	private int fruitType;
	
	//Not "static final" so it can be modified in subclasses
	// please treat like it is
	// is it obvious I'm not really a Java programmer
	protected /*static final*/ int NUM_FRUIT=6;
	
	java.util.Random rand = new java.util.Random();
	
	//default constructor
	Fruit(int x, int y) {				
		super(x, y);
	}
	public void randomise( int width, int height, 
			               Snake snake, Maze maze, Fruit fruit, GemList gems){
		
		final Boolean grid[][]= new Boolean[width][height];
		int x,y,n;
		int count = 0;
		
		//randomly select a new type of fruit
		fruitType = (int)abs(rand.nextInt() % NUM_FRUIT);
		
		
		//for all the points in the grid
		//set a Boolean to be true if they are occupied
		//false otherwise
		for(x=0; x<width;x++){
			for(y=0;y<height;y++){
				grid[x][y]=false;
			}
		}
		if(snake != null){
			for(n=0;n < snake.getParticleCount();n++ ){
				grid[((int) snake.getPosX(n))%width]
					[((int) snake.getPosY(n))%height]=true;
			}
		}
		if(maze != null)
		for(n=0;n < maze.getParticleCount();n++ ){
			grid[((int) maze.getPosX(n))%width]
				[((int) maze.getPosY(n))%height]=true;
		}
		if(fruit != null){
			grid[((int) fruit.GetX())%width]
					[((int) fruit.GetY())%height]=true;
		}
		if(gems != null){
			for(n=0;n < gems.getParticleCount();n++ ){
				grid[((int) gems.getPosX(n))%width]
					[((int) gems.getPosY(n))%height]=true;
			}
		}
		//count the number of empty positions
		for(x=0; x<width;x++){
			for(y=0;y<height;y++){
				if(grid[x][y]==false){
					count++;
				}
			}
		}
		//if there are no empty positions place the fruit off the edge 
		//if the screen, this is a dirty hack and will almost certainly 
		//result in bugs some day
		//consider recursing the call but with an empty snake
		if(count==0){
			//HACK
			x=width+2;
			y=height+2;
			return;
		}
		//set count to be a random integer less than it's current value
		count = (int)abs(rand.nextInt() % count);

		//find the "count"th empty space and set it to be the current
		//position of the fruit
		for(x=0; x<width;x++){
			for(y=0;y<height;y++){
				if(grid[x][y]==false){
					if(count == 0 ){
						mPosX = x;
						mPosY = y;		
						return;
					}
					count--;
				}
			}
		}		
	}
	private int abs(int i) {
		if(i>=0)
			return i;
		return -i;
	}
	public int getType(){
		return fruitType;
	}
	
}
