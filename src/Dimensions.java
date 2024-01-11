
public enum Dimensions {
	HEIGHT(1), WIDTH(0);
	
	private int index;
	
	private Dimensions(int index){
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
