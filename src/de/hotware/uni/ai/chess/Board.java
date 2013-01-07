package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;
import java.util.List;

public interface Board {
	
	public List<Unit> getWhiteUnits();
	
	public List<Unit> getBlackUnits();
	
	public boolean isOwnerWhite();
	
	public Board move(Unit pUnit, Point2D pPosition);

	boolean isPositionAllowed(Unit pUnit, Point2D pPosition);
	
	public EndType end();

	public static enum EndType {
		RUNNING,
		DRAW,
		WHITE,
		BLACK
	}
}
