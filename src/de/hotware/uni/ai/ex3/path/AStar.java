package de.hotware.uni.ai.ex3.path;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Comparator;

public class AStar implements RouteFindingAlgorithm {

	@Override
	public Queue<QuadNode<Position>> findRoute(QuadNode<Position> pStart,
			QuadNode<Position> pEnd,
			QuadNodeMatrix<Position> pQuadNodeMatrix,
			VisitListener pVisitListener) {
		if(!pStart.get().mType.isEmpty() || !pEnd.get().mType.isEmpty()) {
			throw new IllegalArgumentException("pStart or pEnd were not empty");
		}
		PriorityQueue<NodeWrap> openSet = new PriorityQueue<NodeWrap>(1, new Comparator<NodeWrap>() {
			
			@Override
			public int compare(NodeWrap pFirst, NodeWrap pSecond) {
				double first = pFirst.mGScore + pSecond.mHScore;
				double second = pSecond.mGScore + pSecond.mHScore;
				return (int) ((first - second) * 1000);
			}
			
		});
		
		openSet.add(new NodeWrap(pStart, 0, this.distance(pStart, pEnd)));
		
		Map<QuadNode<Position>, QuadNode<Position>> cameFrom = new HashMap<>();
		Set<QuadNode<Position>> closedSet = new HashSet<>();
		
		while(!openSet.isEmpty()) {
			//get the next best node out of the set
			//it's always the first in the queue because we
			//are using a priorityqueue
			NodeWrap current = openSet.poll();
			pVisitListener.onVisit(current.mNode);
			if(current.mNode == pEnd) {
				return Util.buildPath(cameFrom, pEnd);
			}
			//we are finished with the current node
			closedSet.add(current.mNode);
			//navigate through all neighbours
			for(QuadNode<Position> neighbour : current.mNode.getNeighbours()) {
				
				//if the neighbour is null the maze ends, if the type isn't empty, there should be a wall
				//if the neighbour is finished
				if(neighbour == null || !neighbour.get().mType.isEmpty() || closedSet.contains(neighbour)) {
					continue;
				}
				
				//check whether we have already opened an old instance of this neighbour
				NodeWrap oldNode = null;
				for(NodeWrap node : openSet) {
					if(node.mNode == neighbour) {
						oldNode = node;
						break;
					}
				}
				
				//create the new node, g(x) is always + 1 as we are only going north, east, south or west
				//distance is our heuristic function
				NodeWrap toAdd = new NodeWrap(neighbour,
						current.mGScore + 1,
						distance(neighbour, pEnd));
				
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
	
	public static class NodeWrap {
		
		QuadNode<Position> mNode;
		double mGScore;
		double mHScore;
		
		public NodeWrap(QuadNode<Position> pNode, double pGScore, double pHScore) {
			this.mNode = pNode;
			this.mGScore = pGScore;
			this.mHScore = pHScore;
		}
		
	}

}
