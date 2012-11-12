package de.hotware.uni.ai.ex3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Greedy implements RouteFindingAlgorithm {

	@Override
	public Queue<QuadNode<Position>> findRoute(QuadNode<Position> pStart,
			final QuadNode<Position> pEnd,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			VisitListener pVisitListener) {
		if(!pStart.get().mType.isEmpty() || !pEnd.get().mType.isEmpty()) {
				throw new IllegalArgumentException("pStart or pEnd were not empty");
		}
		PriorityQueue<QuadNode<Position>> queue = new PriorityQueue<QuadNode<Position>>(1, new Comparator<QuadNode<Position>>() {

			@Override
			public int compare(QuadNode<Position> pFirst, QuadNode<Position> pSecond) {
				return Greedy.this.calcCosts(pFirst, pEnd) - Greedy.this.calcCosts(pSecond, pEnd);
			}
			
		});
		queue.add(pStart);		
		Map<QuadNode<Position>, QuadNode<Position>> from = new HashMap<>();
		Set<QuadNode<Position>> visited = new HashSet<>();
		
		while(!queue.isEmpty()) {			
			QuadNode<Position> current = queue.poll();
			pVisitListener.onVisit(current);
			if(current == pEnd) {
				return Util.buildPath(from, pEnd);
			}
			visited.add(current);
			for(QuadNode<Position> neighbour : current.getNeighbours()) {
				if(neighbour == null || !neighbour.get().mType.isEmpty() || visited.contains(neighbour))	{
					continue;
				}
				if(!queue.contains(neighbour)) {
					from.put(neighbour, current);
					queue.add(neighbour);
				}
			}
			
		}
		pVisitListener.onGiveUp(this);
		return null;
	}
	
	/**
	 * manhattan cost function
	 */
	public int calcCosts(QuadNode<Position> pFrom, QuadNode<Position> pTo) {
		Position from = pFrom.get();
		Position to = pTo.get();
		int dX = Math.abs(to.mX - from.mX);
		int dY = Math.abs(to.mY - from.mY);
		return dX + dY;
	}

}
