package model;

public abstract class BreakableElement {
	
	protected int row;
	protected int col;
	
	protected BreakableElement(int row, int col) {
		
		this.row = row;
		this.col = col;
	}
	
	protected abstract int randomBroken(int currentStatus);
	protected abstract void repair();
	protected abstract boolean checkBroken();
}
