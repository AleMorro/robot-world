package model;

import java.util.Random;

public class Dog{
	
	protected int row;
	protected int col;
	
	public Dog(int r, int c) {
		
		this.row = r;
		this.col = c;
	}
	
	protected int randomChange() {
		
		Random rand = new Random();
		
		int randomNum = rand.nextInt(8);
		if(randomNum < 4) {
			return randomNum;
		}
		return -1;
	}
}
