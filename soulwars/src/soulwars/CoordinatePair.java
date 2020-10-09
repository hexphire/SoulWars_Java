package soulwars;

public class CoordinatePair<X, Y> {
	private final X xPos;
	private final Y	yPos;
	private int tileX;
	private int tileY;
	
	public CoordinatePair(X xPos, Y yPos) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.tileX = 64;
		this.tileY = 64;
	}
	
	public String toString() {
		return "(" + xPos + "," + yPos + ")";
	}
	
	public float getPixelX() {
		float x = (float)xPos;
		return x * tileX; 
	}
	
	public float getPixelY() {
		float y = (float)yPos;
		return y * tileY;
	}
	public X getX() {
		return xPos;
	}
	
	public Y getY() {
		return yPos;
	}
}
