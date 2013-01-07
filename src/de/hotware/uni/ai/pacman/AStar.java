package de.hotware.uni.ai.pacman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.hotware.uni.ai.pacman.Node.PositionType;

/**
 * @author Martin Braun (1249080)
 */
public final class AStar {
	
	public static List<Node> getShortestPath(Node pFrom, Node pTo, int[][] pLevel) {
		List<Node> ret = new ArrayList<>();
		Queue<Node> open = new PriorityQueue<Node>();
		Set<Node> visited = new HashSet<Node>();
		Map<Node, Node> cameFrom = new HashMap<>();
		
		int distance = calculateDistance(pFrom, pTo);
		pFrom.mEstimation = distance;
	    open.add(pFrom);
	    
	    while(!open.isEmpty()) {	    	
	    	Node cur = open.poll();
	    	int x = cur.mX;
	    	int y = cur.mY;
	    	
	    	if(cur.equals(pTo)) {
	    		break;
	    	}

	    	if(visited.contains(cur)) {
	    		continue;
	    	}
	    	
	    	visited.add(cur);
	    	
        	expand(x - 1, y, pLevel, cur, pTo, open, cameFrom, visited);
        	expand(x + 1, y, pLevel, cur, pTo, open, cameFrom, visited);
        	expand(x, y - 1, pLevel, cur, pTo, open, cameFrom, visited);
        	expand(x, y + 1, pLevel, cur, pTo, open, cameFrom, visited);
	    }
	    
	    //reconstruct the path
	    Node cur = cameFrom.get(pTo);
	    if(cur == null) {
	    	return ret;
	    }
	    ret.add(0, pTo);
	    ret.add(0, cur);
	    while(!cur.equals(pFrom)) {
		    cur = cameFrom.get(cur);
		    ret.add(0, cur);
	    }
		return ret;
	}
	
	private static void expand(int pX, int pY, int[][] pLevel, Node pCurrent, Node pTo, Queue<Node> pOpenQueue, Map<Node, Node> pCameFrom, Set<Node> pVisited) {
		if(Node.getPositionType(pX, pY, pLevel) != PositionType.WALL){    
    		Node insert = new Node(pX, pY);
    		if(!pVisited.contains(insert)) {
				insert.mCoveredWay = pCurrent.mCoveredWay + 1;
				insert.mEstimation = calculateDistance(insert, pTo) + insert.mCoveredWay;
				pCameFrom.put(insert, pCurrent);
				
				Node existing = null;
				for(Node node : pOpenQueue)	{
					if(node.equals(insert)) {
						existing = node;
						break;
					}
				}
				
				if(existing == null || insert.mCoveredWay <= existing.mCoveredWay) {
					pOpenQueue.remove(existing);
				}
				pOpenQueue.add(insert);
    		}
    	}
	}
	
	public static int calculateDistance(Node pFrom, Node pTo){
    	return Math.abs(pTo.mX-pFrom.mX)+ Math.abs(pTo.mY-pFrom.mY);
    }
	
}
