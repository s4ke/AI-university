package de.hotware.uni.ai.ex3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStar implements RouteFindingAlgorithm {

	@Override
	public Queue<QuadNode<Position>> findRoute(QuadNode<Position> pStart,
			QuadNode<Position> pEnd,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			VisitListener pVisitListener) {
		if(!pStart.get().mType.isEmpty() || !pEnd.get().mType.isEmpty()) {
			throw new IllegalArgumentException("pStart or pEnd were not empty");
		}
		Map<QuadNode<Position>, QuadNode<Position>> val = this.star(pStart, pEnd, pQuadNodeMatrix, pVisitListener);
		LinkedList<QuadNode<Position>> ret = null;
		if(val != null) {
			//we found some stuff
			QuadNode<Position> current = pEnd;
			ret = new LinkedList<QuadNode<Position>>();
			while((current = val.get(current)) != pStart) {
				ret.add(0, current);
			}
		}
		return ret;
	}
	
	public Map<QuadNode<Position>, QuadNode<Position>> star(QuadNode<Position> pStart,
			QuadNode<Position> pEnd,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			VisitListener pVisitListener) {
		PriorityQueue<NodeWrap> openSet = new PriorityQueue<NodeWrap>();
		openSet.add(new NodeWrap(pStart, 0, this.distance(pStart, pEnd)));
		
		Map<QuadNode<Position>, QuadNode<Position>> cameFrom = new HashMap<QuadNode<Position>, QuadNode<Position>>();
		Set<NodeWrap> closedSet = new HashSet<NodeWrap>();
		
		while(!openSet.isEmpty()) {
			//get the next best node out of the set
			//it's always the first in the queue because we
			//are using a priorityqueue
			NodeWrap current = openSet.poll();
			pVisitListener.onVisit(current.mNode);
			if(current.mNode == pEnd) {
				return cameFrom;
			}
			closedSet.add(current);
			
			//navigate through all neighbours
			for(QuadNode<Position> neighbour : current.mNode.getNeighbours()) {
				
				//if the neighbour is null the maze ends, if the type isn't empty, there should be a wall
				//if the neighbour is finished
				if( neighbour == null || !neighbour.get().mType.isEmpty() || closedSet.contains(neighbour) ) {
					continue;
				}
				
				NodeWrap oldNode = null;
				for(NodeWrap node : openSet) {
					if(node.mNode == neighbour) {
						oldNode = node;
						break;
					}
				}
				
				//create the new node, g(x) is always + 1 as we are only going north, east, south or west
				//distance is our heuristic function
				NodeWrap toAdd = new NodeWrap(neighbour, current.mGScore + 1, distance(current.mNode, neighbour));
				
				if(oldNode == null || toAdd.mGScore <= oldNode.mGScore) {
					cameFrom.put(neighbour, current.mNode);
					if(oldNode != null) {
						//the old node has a worse f(x) and therefore has to be removed
						openSet.remove(oldNode);
					}
					openSet.add(toAdd);
				}
			}
		}
		pVisitListener.onGiveUp(this);
		return null;
	}
	
	public double distance(QuadNode<Position> pFrom, QuadNode<Position> pTo) {
		Position from = pFrom.get();
		Position to = pTo.get();
		double dx = to.mX - from.mX;
		double dy = to.mY - from.mY;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static class NodeWrap implements Comparable<NodeWrap> {
		
		QuadNode<Position> mNode;
		double mGScore;
		double mHScore;
		
		public NodeWrap(QuadNode<Position> pNode, double pGScore, double pHScore) {
			this.mNode = pNode;
			this.mGScore = pGScore;
			this.mHScore = pHScore;
		}

		@Override
		public int compareTo(NodeWrap pOther) {
			double own = this.mGScore + this.mHScore;
			double other = pOther.mGScore + pOther.mHScore;
			return (int) ((own - other) * 1000);
		}
		
	}

}
