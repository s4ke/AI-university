package de.hotware.uni.ai.pacman;

/**
 * @author Martin Braun (1249080)
 */
public enum Direction {
	NORTH(8),
	EAST(6),
	SOUTH(2),
	WEST(4),
	NONE(5);
	
	protected int mRepresentation;
	
	private Direction(int pRepresentation) {
		this.mRepresentation = pRepresentation;
	}
	
	public int getRepresentation() {
		return this.mRepresentation;
	}
	
}
