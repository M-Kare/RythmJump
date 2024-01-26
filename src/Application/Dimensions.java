package Application;

public enum Dimensions {
	Y(0), X(1);
	
	private int index;
	
	private Dimensions(int index){
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
