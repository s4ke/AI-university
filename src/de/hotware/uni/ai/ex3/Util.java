package de.hotware.uni.ai.ex3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class Util {
	
	public static Queue<QuadNode<Position>> buildPath(Map<QuadNode<Position>,
			QuadNode<Position>> pMap, QuadNode<Position> pEnd) {
		LinkedList<QuadNode<Position>> ret = null;
		QuadNode<Position> current = pEnd;
		ret = new LinkedList<QuadNode<Position>>();
		while((current = pMap.get(current)) != null) {
			System.out.println(current.get().getInfo());
			ret.add(0, current);
		}
		return ret;
	}

}
