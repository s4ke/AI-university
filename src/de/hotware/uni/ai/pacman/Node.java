package de.hotware.uni.ai.pacman;

/**
 * @author Martin Braun (1249080)
 */
public class Node implements Comparable<Node> {

	protected int mX;
	protected int mY;
	protected int mCoveredWay;
	protected int mEstimation;
	
	public Node(int pX, int pY) {
		this(pX, pY, 0, 0);
	}
	
	public Node(int pX, int pY, int pCovered, int pEstimation) {
		this.mCoveredWay = 0;
		this.mEstimation = 0;
		this.mX = pX;
		this.mY = pY;
	}
	
	@Override
	public boolean equals(Object pOther) {
		Node otherNode = (Node) pOther;
		return this.mX == otherNode.mX && this.mY == otherNode.mY;
	}
	
	@Override
	public int hashCode() {
		//as 42 is no prime number, we use his brother :)
		final int prime = 43;
		int result = 1;
		result = prime * result + this.mX;
		result = prime * result + this.mY;
		return result;
	}
	
	@Override
	public int compareTo(Node pOther) {
		return this.mEstimation - pOther.mEstimation;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("(").append(this.mX).append(",").append(this.mY).append(")").toString();
	}
	
	public static PositionType getPositionType(int pX, int pY, int[][] pLevel) {
		return PositionType.getPositionTypeFromInteger(pLevel[pX][pY]);
	}
	
	public static enum PositionType {
		EMPTY(0),
		OWN(1),
		GOLD(6),
		WALL(9);
		
		protected int mRepresentation;
		
		private PositionType(int pRepresentation) {
			this.mRepresentation = pRepresentation;
		}
		
		public boolean isWall() {
			return this == WALL;
		}
		
		public static PositionType getPositionTypeFromInteger(int pRepresentation) {
			switch(pRepresentation) {
				case 0: {
					return EMPTY;
				}
				case 1: {
					return OWN;
				}
				case 6: {
					return GOLD;
				}
				case 9: {
					return WALL;
				}
				default: {
					throw new IllegalArgumentException("pRepresentation is no" +
							" representation of a PositionType");
				}
			}
		}
		
	}
	
}
