package de.hotware.uni.ai.connectfour;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * @author Martin Braun (1249080)
 */
public final class Constants {
	
	static final boolean DEBUG = true;

	private Constants() {
		throw new AssertionError("can't touch this!");
	}
	
	public static final int AMOUNT_TO_WIN = 4;
	public static final int SIZE_X = 5;
	public static final int SIZE_Y = 5;
	public static final char EMPTY = ' ';
	public static final char X = 'X';
	public static final char O = 'O';
	public static final Point2D DEFAULT_SIZE = new Point(SIZE_X, SIZE_Y);
	
	public static enum GameState {
		WIN(1),
		LOSE(-1),
		DRAW(0),
		RUNNING(42);
		
		protected final int mRepresentation;
		
		private GameState(int pRepresentation) {
			this.mRepresentation = pRepresentation;
		}
		
		public int getRepresentation() {
			return this.mRepresentation;
		}
		
		public GameState getGameStateFromInt(int pX) {
			GameState ret;
			switch(pX) {
				case 1: {
					ret = WIN;
					break;
				}
				case -1: {
					ret = LOSE;
					break;
				}
				case 0: {
					ret = DRAW;
					break;
				}
				case 42: {
					ret = RUNNING;
					break;
				}
				default: {
					throw new IllegalArgumentException("pX was invalid");
				}
			}
			return ret;
		}
		
	}

}
