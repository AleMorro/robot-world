package model;

import java.util.Random;

public class WashingMachine extends BreakableElement{
	
	private boolean broken;
	
	protected WashingMachine(int r, int c) {
		
		super(r, c);
		this.broken = false;
	}
	
	@Override
	protected int randomBroken(int status) {
		
		Random rand = new Random();
		if(this.broken == false) {
			if(rand.nextInt(100) < 5) {
	  			this.broken = true;
	  			// System.out.println("WM broken");
	  			return 2;
	  		}
		} 
		// return the previous status in case
		return status;
	}
	
	@Override
	protected void repair() {
		
		// System.out.println("The WM is repaired");
		this.broken = false;
	}
	
	@Override
	protected boolean checkBroken() {
		
		return this.broken;
	}
}
