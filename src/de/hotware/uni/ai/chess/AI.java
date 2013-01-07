package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;


public interface AI {
	
	public static class Move {
		
		public Unit mUnit;
		public Point2D mPoint;
		
	}
	
	public Move calculateNextMove(Board pBoard);

}
