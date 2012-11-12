package de.hotware.uni.ai.ex3;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class Main {
	
	public static void main(String[] pArgs) throws IOException {
		VisitListener listener =  new VisitListener() {

				@Override
				public void onVisit(QuadNode<Position> pQuadNode) {
					System.out.println("visited: " + pQuadNode.get().getInfo());
					
				}

				@Override
				public void onGiveUp(RouteFindingAlgorithm pRouteFindingAlgorithm) {
					System.out.println("failed: " + pRouteFindingAlgorithm);
				}
				
		};
		
		System.out.println("------------------------------------");
		System.out.println("testing with dfs: ");
		QuadNodeMatrix<Position> matrix = MapParser.parseMap(new File("test.maze"));
		System.out.println(matrix);
		RouteFindingAlgorithm algo = new DFS();
		printPath(algo.findRoute(matrix.getNorthNodes().get(13), matrix.getSouthNodes().get(7), matrix, listener));
		
		System.out.println("------------------------------------");
		System.out.println("Testing with greedy: ");
		matrix = MapParser.parseMap(new File("test.maze"));
		System.out.println(matrix);
		algo = new Greedy();
		printPath(algo.findRoute(matrix.getNorthNodes().get(13), matrix.getSouthNodes().get(7), matrix, listener));
		
		System.out.println("------------------------------------");
		System.out.println("testing with A*: ");
		matrix = MapParser.parseMap(new File("test.maze"));
		System.out.println(matrix);
		algo = new AStar();
		printPath(algo.findRoute(matrix.getNorthNodes().get(13), matrix.getSouthNodes().get(7), matrix, listener));
	}
	
	public static void printPath(Queue<QuadNode<Position>> pQueue) {
		if(pQueue != null) {
			for(QuadNode<Position> node : pQueue) {
				System.out.print(node.get().getInfo() + " -> ");
			}
		}
		System.out.println("done.");
	}

}
