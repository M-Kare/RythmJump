package Application;

public enum Dimensions {
	Y(1), X(0);
	
	private int index;
	
	private Dimensions(int index){
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
