package de.hotware.uni.ai.connectfour;

import java.awt.geom.Point2D;

public interface IAI {
	
	public void init(char whoami);
	public Point2D update(IBoard board);
	
}