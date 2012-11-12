package de.hotware.uni.ai.ex3.path;

import java.util.Queue;


public interface RouteFindingAlgorithm {
	
	public Queue<QuadNode<Position>> findRoute(QuadNode<Position> pStart, QuadNode<Position> pEnd, QuadNodeMatrix<Position> pQuadNodeMatrix, VisitListener pVisitListener);

}
