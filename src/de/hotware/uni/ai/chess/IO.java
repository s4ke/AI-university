package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;
import java.io.IOException;


public interface IO {
	
	public Board read(Board pBoard) throws IOException;

	public void write(Unit pUnit, Point2D pPosition);

}
