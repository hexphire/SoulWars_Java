package soulwars;

public class CoordinatePair<X, Y> {
	private final X xPos;
	private final Y	yPos;
	
	public CoordinatePair(X xPos, Y yPos) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public String toString() {
		return "(" + xPos + "," + yPos + ")";
	}
	
	public X getX() {
		return xPos;
	}
	
	public Y getY() {
		return yPos;
	}
}
