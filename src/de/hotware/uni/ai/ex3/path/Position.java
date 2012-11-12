package de.hotware.uni.ai.ex3.path;

public class Position {
	
	public int mX;
	public int mY;
	public Position.Type mType;
	
	public Position(int pX, int pY) {
		this.mX = pX;
		this.mY = pY;
	}
	
	@Override
	public String toString() {
		return this.mType.toString();
	}
	
	public String getInfo() {
		return "(" + this.mX + "," + this.mY + ")" + "(Type: " + this.mType.toString() + ")"; 
	}
	
	public static enum Type {
		SOLID("#"),
		EMPTY(" "),
		PATH("."),
		START("S"),
		END("E");
		
		private Type(String pRepresentation) {
			this.mRepresentation = pRepresentation;
		}
		
		protected String mRepresentation;
		
		public boolean isEmpty() {
			return this == EMPTY;
		}
		
		@Override
		public String toString() {
			return this.mRepresentation;
		}
		
	}

}
