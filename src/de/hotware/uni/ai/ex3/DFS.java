package de.hotware.uni.ai.ex3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DFS implements RouteFindingAlgorithm {

	@Override
	public Queue<QuadNode<Position>> findRoute(QuadNode<Position> pStart,
			QuadNode<Position> pEnd,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			VisitListener pVisitListener) {
		if(!pStart.get().mType.isEmpty() || !pEnd.get().mType.isEmpty()) {
			throw new IllegalArgumentException("pStart or pEnd were not empty");
		}
		Queue<QuadNode<Position>> queue = this.findRoute(pStart, pEnd, pVisitListener, pQuadNodeMatrix, new HashSet<QuadNode<Position>>());
		if(queue == null) {
			pVisitListener.onGiveUp(this);
			return null;
		}
		Queue<QuadNode<Position>> ret = new LinkedList<>();
		ret.addAll(queue);
		return ret;
	}
	
	public LinkedList<QuadNode<Position>> findRoute(QuadNode<Position> pCurrent,
			QuadNode<Position> pEnd,
			VisitListener pVisitListener,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			Set<QuadNode<Position>> pVisited) {		
		pVisited.add(pCurrent);
		pCurrent.get().mType = Position.Type.PATH;
		pVisitListener.onVisit(pCurrent);
		
		if(pCurrent == pEnd) {
			LinkedList<QuadNode<Position>> ret = new LinkedList<>();
			ret.add(0, pCurrent);
			return ret;
		}
		
		for(QuadNode<Position> node : pCurrent.getNeighbours()) {
			if(node != null && node.get().mType.isEmpty() && !pVisited.contains(node)) {
				LinkedList<QuadNode<Position>> result = this.findRoute(node, pEnd, pVisitListener, pQuadNodeMatrix, pVisited);
				if(result != null) {
					result.add(0, pCurrent);
					return result;
				}
			}
		}
		pCurrent.get().mType = Position.Type.EMPTY;
		return null;
	}

}
