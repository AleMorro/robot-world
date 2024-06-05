package model;

import controller.RobotWorldObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RobotWorld {
	
	/*
  	 * IllegalMoveException used for all the illegalMove that the player can do
  	 */
    public class IllegalMoveException extends RuntimeException {
    	
		private static final long serialVersionUID = 1380109849435833414L;

		public IllegalMoveException(String s) {
            super(s);
        }
    }
	
	// matrix for the randomized map
	private int[][] map;
	// array for the possible orientation of the robot
	private static final String[] direction = {"up","right","down","left"};
	// var for number of action
	private static int numAction;
	// variables for the position of the player
	private int dir;
	private int RPos;
	private int CPos;
	// var for the washing machine
	private int[] WMRPos;
	private int[] WMCPos;
	private WashingMachine[] wm;
	// var for the sink
	private int[] RSink;
	private int[] CSink;
	private Sink[] sink;
	// var for the dog
	private int RDog;
	private int CDog;
	private Dog dog;
	// var for the observers
  	private List<RobotWorldObserver> observers = new ArrayList();
	
	public RobotWorld(int size) {
		
		this.map = new int[size][size];
		
		if(size == 10) {
			this.WMRPos = new int[1];
			this.WMCPos = new int[1];
			
			this.RSink = new int[2];
			this.CSink = new int[2];
			createRandomMap();
			
			RobotWorld.numAction = 0;
			
			wm = new WashingMachine[1];
			sink = new Sink[2];
			
			for(int i=0; i<wm.length; i++)
				wm[i] = new WashingMachine(WMRPos[i], WMCPos[i]);
			
			for(int i=0; i<sink.length; i++) 
				sink[i] = new Sink(RSink[i], CSink[i]);
			
			dog = new Dog(RDog, CDog);
		}
		if(size == 12) {
			this.WMRPos = new int[2];
			this.WMCPos = new int[2];
			
			this.RSink = new int[3];
			this.CSink = new int[3];
			createRandomMap();
			
			RobotWorld.numAction = 0;
			
			wm = new WashingMachine[2];
			sink = new Sink[3];
			
			for(int i=0; i<wm.length; i++)
				wm[i] = new WashingMachine(WMRPos[i], WMCPos[i]);
			
			for(int i=0; i<sink.length; i++) 
				sink[i] = new Sink(RSink[i], CSink[i]);
			
			dog = new Dog(RDog, CDog);
		}
		
	}
	
	/*
	 * Method that creates a randomized map with symbols 
	 * representing various elements
	 */
	private void createRandomMap() {
		
		/* Symbols in the map:
		 * 0 = wall;
		 * 1 = washing machine
		 * 2 = washing machine broken
		 * 3 = sink off
		 * 4 = sink on
		 * 5 = water
		 * 6 = stove off
		 * 7 = stove open
		 * 8 = dog
		 * 9 = player
		 * 10 = free space
		 */
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map.length; j++) {
				
				if(i == 0 || i == map.length-1 || j == 0 || j == map.length-1) this.map[i][j] = 0;
				else this.map[i][j] = 10;
			}
		}
		
		int n_washingMachine = 0;
		int n_sink = 0;
		int n_stove = 0;
		int n_dog = 0;
		// number of elements to put in the map
		switch(map.length) {
		case 10:
			n_washingMachine = 1;
			n_sink = 2;
			n_stove = 3;
			n_dog = 1;
			break;
		case 12:
			n_washingMachine = 2;
			n_sink = 3;
			n_stove = 4;
			n_dog = 1;
			break;
		}
			
		createElements(n_washingMachine, 1);
		createElements(n_sink, 3);
		createElements(n_stove, 6);
		createElements(n_dog, 8);
		// randomized the player
		createElements(1, 9);
		
		setStartedDirection();
	}
	
	/*
	 * Method that put in random position the specified element
	 * in the map
	 * @param numElements -> numbers of elements to put in the map
	 * @param symbol -> symbol that represent the element on the map
	 */
	private void createElements(int numElements, int symbol) {
		
		Random rand = new Random();
		
		int i = 0;
		int r, c;
		while(numElements != 0) {
			r = rand.nextInt(map.length - 2) + 1;
			c = rand.nextInt(map.length -2) + 1;
			// System.out.println("r: "+r+" c: "+c);
			if (this.map[r][c] == 10) {
				// save the player row and column
				if (symbol == 9) {
					RPos = r;
					CPos = c;
				} // save the washing-machine row and column
				else if(symbol == 1) {
					WMRPos[i] = r;
					WMCPos[i] = c;
					i++;
				}  // save the sinks row and column
				else if(symbol == 3) {
					RSink[i] = r;
					CSink[i] = c;
					i++;
				} // save the dog row and column
				else if(symbol == 8) {
					RDog = r;
					CDog = c;
				}
				this.map[r][c] = symbol;
				// System.out.println("Val: "+map[r][c]);
				numElements--;
			}
			// System.out.println("n_rest: "+numElements);
		}
	}
	
	/*
	 * Method to randomize the started direction of the robot
	 */
	private void setStartedDirection() {
		
		Random rand = new Random();
		this.dir = rand.nextInt(4);
		System.out.println("Started direction: "+direction[dir]);
	}
	
	/*
	 * Method that return the random map
	 * @return map -> map random generated
	 */
	public int[][] getMap() {
	
		return this.map;
	}
	
	/*
	 * Method to get the direction of the robot
	 * @return String -> indicates the orientation of the robot
	 */
	public String getDirection() {
		
		return direction[dir];
	}
	
	/*
	 * Method to get the row position of the player
	 * @return RPos
	 */
  	public int getPlayerRPos() {
  		return RPos;
  	}

  	/*
  	 * Method to get the column position of the player
  	 * @return CPos
  	 */
  	public int getPlayerCPos() {

  		return CPos;
  	}

  	/*
  	 * Method to return the elements in the map
  	 * @return int -> number that rapresent one of the various element
  	 */
  	public int getMapElement(int r, int c) {

  		return map[r][c];
  	}
  	
  	/**
  	 * Method that groups all the various routines that occur 
  	 * after each robot action
  	 */
  	private void routineAction() {
  		
  		Random rand = new Random();
  		
  		RobotWorld.numAction++;
  		int randomWm = rand.nextInt(wm.length);
  		map[wm[randomWm].row][wm[randomWm].col] = 
  				wm[randomWm].randomBroken(map[wm[randomWm].row][wm[randomWm].col]);
  		
  		int randomSink = rand.nextInt(sink.length);
  		map[sink[randomSink].row][sink[randomSink].col] = 
  				sink[randomSink].randomBroken(map[sink[randomSink].row][sink[randomSink].col]);
  		
  		dogRandomChange();
  		
  		spreadWater();
  	}

  	/*
  	 * Method that rotate the player on the left
  	 */
  	public void rotateLeft() {
  		
  		routineAction();
  		
  		if(dir == 0) {
  			this.dir = 3;
  		}
  		else this.dir--;
  		notifyObservers();
  	}

  	/*
  	 * Method that rotate the player on the right
  	 */
  	public void rotateRight() {
  		
  		routineAction();
  		
  		if(dir == 3) {
  			this.dir = 0;
  		}
  		else this.dir++;
  		notifyObservers();
  	}

  	/*
  	 * Method to move the player on the current direction
  	 */
  	public void move() {
  		
  		routineAction();
  		
  		// Check the direction of the player to see if 
  		// you can move in that direction
  		switch (dir) {
  		// up
  		case 0:
  			if(checkFreeSquareMove(RPos-1, CPos)) throw new IllegalMoveException("It is not a free space!");
  			this.map[RPos][CPos] = 10;
  			RPos--;
  			this.map[RPos][CPos] = 9;
  			notifyObservers();
  			break;
  		// right
  		case 1:
  			if(checkFreeSquareMove(RPos, CPos+1)) throw new IllegalMoveException("It is not a free space!");
  			this.map[RPos][CPos] = 10;
  			CPos++;
  			this.map[RPos][CPos] = 9;
  			notifyObservers();
  			break;
  		// down
  		case 2:
  			if(checkFreeSquareMove(RPos+1, CPos)) throw new IllegalMoveException("It is not a free space!");
  			this.map[RPos][CPos] = 10;
  			RPos++;
  			this.map[RPos][CPos] = 9;
  			notifyObservers();
  			break;
  		// left
  		case 3:
  			if(checkFreeSquareMove(RPos, CPos-1)) throw new IllegalMoveException("It is not a free space!");
  			this.map[RPos][CPos] = 10;
  			CPos--;
  			this.map[RPos][CPos] = 9;
  			notifyObservers();
  			break;
  		}
  	}
  	
  	private boolean checkFreeSquareMove(int row, int col) {
  		
  		if(map[row][col] == 5)
  			return false;
  		
  		if(map[row][col] != 10)
  			return true;
  		  		
  		return false;
  	}
  	
  	/*
  	 * Method to interact with the elements of the map by the player
  	 */
  	public void interact() {
  		
  		routineAction();
  		
  		switch (dir) {
  		// up
  		case 0:
  			if(map[RPos-1][CPos] == 10 || map[RPos-1][CPos] == 0) throw new IllegalMoveException("There is no element to interact with!");
  			changeElement(RPos-1, CPos);
  			notifyObservers();
  			break;
  		// right
  		case 1:
  			if(map[RPos][CPos+1] == 10 || map[RPos][CPos+1] == 0) throw new IllegalMoveException("There is no element to interact with!");
  			changeElement(RPos, CPos+1);
  			notifyObservers();
  			break;
  		// down
  		case 2:
  			if(map[RPos+1][CPos] == 10 || map[RPos+1][CPos] == 0) throw new IllegalMoveException("There is no element to interact with!");
  			changeElement(RPos+1, CPos);
  			notifyObservers();
  			break;
  		// left
  		case 3:
  			if(map[RPos][CPos-1] == 10 || map[RPos][CPos-1] == 0) throw new IllegalMoveException("There is no element to interact with!");
  			changeElement(RPos, CPos-1);
  			notifyObservers();
  			break;
  		}
  	}
  	
  	/*
  	 * Method that manages the various possible actions and their 
  	 * changes on the map following an interaction
  	 */
  	private void changeElement(int row, int col) {
  		
  		switch(map[row][col]) {
  		
  		// broken washing machine -> washing machine
  		case 2:
  			map[row][col] = 1;
  			int w = getCorrectWm(row, col);
  			wm[w].repair();
  			break;
  		// open sink -> closed sink
  		case 4:
  			map[row][col] = 3;
  			int k = getCorrectSink(row, col);
  			sink[k].repair();
  			break;
  		// stove off -> stove open
  		case 6:
  			map[row][col] = 7;
  			break;
  		// stove open -> stove off
  		case 7:
  			map[row][col] = 6;
  			break;
  		}
  	}
  	
  	private int getCorrectSink(int r, int c) {
  		
  		for(int i=0; i<sink.length; i++) {
  			
  			if(sink[i].row == r && sink[i].col == c)
  				return i;
  		}
  		return -1;
  	}
  	
  	private int getCorrectWm(int r, int c) {
  		
  		for(int i=0; i<wm.length; i++) {
  			
  			if(wm[i].row == r && wm[i].col == c)
  				return i;
  		}
  		return -1;
  	}
  	
  	/*
  	 * Method to change the dog position in the map
  	 */
  	private void dogRandomChange() {
  		
  		int pos = dog.randomChange();
  		if(pos != -1) {
  			switch(pos) {
  			// up
  			case 0:
  				if(map[dog.row-1][dog.col] == 10) {
  					map[dog.row][dog.col] = 10;
  					dog.row -= 1;
  					map[dog.row][dog.col] = 8;
  					break;
  				}
  			// right
  			case 1:
  				if(map[dog.row][dog.col+1] == 10) {
  					map[dog.row][dog.col] = 10;
  					dog.col += 1;
  					map[dog.row][dog.col] = 8;
  					break;
  				}
  			// down
  			case 2:
  				if(map[dog.row+1][dog.col] == 10) {
  					map[dog.row][dog.col] = 10;
  					dog.row += 1;
  					map[dog.row][dog.col] = 8;
  					break;
  				}
  			// left
  			case 3:
  				if(map[dog.row][dog.col-1] == 10) {
  					map[dog.row][dog.col] = 10;
  					dog.col -= 1;
  					map[dog.row][dog.col] = 8;
  					break;
  				}
  			}
  		}
  	}
  	
  	// Method to check whether water has spread or not
  	private void spreadWater() {
  		
  		// spread water for sink
  		if(numAction % 20 == 0) {
  			for(int i=0; i<sink.length; i++) {
  				if(sink[i].checkBroken()) {
  					chooseSideToSpread(sink[i].row, sink[i].col);
  				}
  			}
  		}
  		// spread water for washing machine
  		if(numAction % 10 == 0) {
  			for(int i=0; i<wm.length; i++) {
  				if(wm[i].checkBroken()) {
  	  				chooseSideToSpread(wm[i].row, wm[i].col);
  	  			}
  			}
  		}
  	}
  	
  	// Method that randomizes the direction in which water expands
  	private void chooseSideToSpread(int r, int c) {
  		
  		Random rand = new Random();
	  	int randNum = rand.nextInt(8);
  		
  		switch(randNum) {
			// up
			case 0:
				if(checkFreeSquareSpreading(r-1, c)) {
					map[r-1][c] = 5;
					break;
				}
			// right
			case 1:
				if(checkFreeSquareSpreading(r, c+1)) {
					map[r][c+1] = 5;
					break;
				}
			// down
			case 2:
				if(checkFreeSquareSpreading(r+1, c)) {
					map[r+1][c] = 5;
					break;
				}
			// left
			case 3:
				if(checkFreeSquareSpreading(r, c-1)) {
					map[r][c+1] = 5;
					break;
				}
			// up-right
			case 4:
				if(checkFreeSquareSpreading(r-1, c+1)) {
					map[r-1][c+1] = 5;
					break;
				}
			// down-right
			case 5:
				if(checkFreeSquareSpreading(r+1, c+1)) {
					map[r+1][c+1] = 5;
					break;
				}
			// down-left	
			case 6:
				if(checkFreeSquareSpreading(r+1, c-1)) {
					map[r+1][c-1] = 5;
					break;
				}
			// up-left	
			case 7:
				if(checkFreeSquareSpreading(r-1, c-1)) {
					map[r-1][c-1] = 5;
					break;
				}
			}
  	}
  	
  	// Method that tests whether water can expand in that direction
  	private boolean checkFreeSquareSpreading(int row, int col) {
  		
  		if(map[row][col] == 10)
  			return true;
  		
  		return false;
  	}
  	
  	// Method to add the observer
  	public void addObserver(RobotWorldObserver observer) {
  		observers.add(observer);
  	}
  	
  	// Method to remove the observer
  	public void removeObserver(RobotWorldObserver observer) {
        observers.remove(observer);
    }
  	 
  	// Method to notify the observer
  	private void notifyObservers() {
        for (RobotWorldObserver observer : observers) {
            observer.updateView();
        }
    }
}
