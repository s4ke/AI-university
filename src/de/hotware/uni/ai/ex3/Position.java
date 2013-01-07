package de.hotware.uni.ai.ex3;

public class Position {
	
	public int mX;
	public int mY;
	public Position.Type mType;
	
	public Position(int pX, int pY) {
		this.mX = pX;
		this.mY = pY;
	}
	
	public static enum Type {
		SOLID,
		EMPTY,
		PATH
	}

}
