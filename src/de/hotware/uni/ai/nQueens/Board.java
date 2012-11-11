package de.hotware.uni.ai.nQueens;

import java.util.Set;

public interface Board extends Cloneable {
	
	public boolean check(int pX, int pY);
	
	public Board putQueens();
	
	public Board putQueen(int pX, int pY);
	
	public Position getLastChangedPosition();
	
	public int getSize();
	
	public Set<Position> getFreeSpots();
	
	public int getFreeSpotCount();
	
	public int calculateCosts();
	
	public int getPlacedQueens();
	
	public Object clone() throws CloneNotSupportedException;
	
	public interface Position extends Cloneable {
		
		public int getX();
		public int getY();
		public Position.Type getType();
		public boolean isAllowed();
		public Object clone() throws CloneNotSupportedException;
		
		public enum Type {
			EMPTY("E", 0),
			QUEEN("Q", 1),
			TAGGED("T", 1);
			
			protected String mStringRepresentation;
			protected int mCosts;

			private Type(String pStringRepresentation, int pCosts) {
				this.mStringRepresentation = pStringRepresentation;
				this.mCosts = pCosts;
			}

			public boolean isAllowed() {
				return this == EMPTY;
			}
			
			@Override
			public String toString() {
				return this.mStringRepresentation;
			}
			
			public int getCosts() {
				return this.mCosts;
			}

		}
		
	}

}
