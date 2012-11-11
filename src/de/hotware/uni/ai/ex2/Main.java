package de.hotware.uni.ai.ex2;

import java.util.Random;
import java.util.Set;

import de.hotware.uni.ai.ex2.Tree.TraversionListener;
import de.hotware.uni.ai.nQueens.BasicBoard;
import de.hotware.uni.ai.nQueens.Board;
import de.hotware.uni.ai.nQueens.Board.Position;
import de.hotware.util.Average;

public class Main {
	
	public static void main(String[] pArgs) {
		if(pArgs.length != 1) {
			System.out.println("first argument is N in NxN size of chess board");
			return;
		}
		//Using this to prematurely stop the calculation.
		//shouldn't be done like this, but time was running out :/
		//Better solution: a value with 3 possible types:
		// - found position, but not done, yet
		// - found position, done
		// - no position found
		// suggestion: Boolean (the class, with true, false, null) or
		// even better, an enum! (Boolean would be bad style imo)
		final int queens = Integer.parseInt(pArgs[0]);
		final Board[] hack = new Board[1];
		Tree<Board> tree = new BasicTree<Board>();
		Board board = new BasicBoard(queens);
		TraversionListener<Board> trav = new TraversionListener<Board>() {

			@Override
			public boolean onVisit(VisitEvent<Board> pEvent) {
				if(hack[0] != null) {
					return false;
				}
				Tree<Board> tree = pEvent.getSource();
				Node<Board> node = pEvent.getNode();
				Board board = node.get();
				int size = board.getSize();
				Position lastPosition = board.getLastChangedPosition();
				int lastX = 0;
				if(lastPosition != null) {
					lastX = lastPosition.getX();
				}
				if(board.getPlacedQueens() == queens) {
					hack[0] = board;
					return false;
				}
				boolean success = false;
				for(int i = lastX; i < size &&!success; ++i) {
					for(int j = 0; j < size; ++j) {
						if(board.check(i, j)) {
							success = true;
							tree.insert(node, new BasicNode<Board>(board.putQueen(i, j), node));
						}
					}
				}
				//if no queens could be placed and the wanted amount isn't reached
				//this part of the tree doesn't lead us anywhere
				return success;
			}
			
		};
		long before;
		long after;
		System.out.println("Testing with DFS");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
		Average average = new Average();
		for(int i = 0; i < 5; ++i) {
			before = System.nanoTime();
			tree.setRoot(new BasicNode<Board>(board, null));
			tree.depthFirstSearch(trav);
			after = System.nanoTime();
			average.add(after - before);
			System.out.println(hack[0] + "\n");
		}
		System.out.println("Median: " + average.mean() + " nanoseconds");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
		System.out.println("Testing with BFS");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
		average = new Average();
		for(int i = 0; i < 5; ++i) {
			before = System.nanoTime();
			tree.setRoot(new BasicNode<Board>(board, null));
			tree.breadthFirstSearch(trav);
			after = System.nanoTime();
			average.add(after - before);
			System.out.println(hack[0] + "\n");
		}
		System.out.println("Median: " + average.mean() + " nanoseconds");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
		System.out.println("Testing with Random");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
		average = new Average();
		for(int i = 0; i < 5; ++i) {
			before = System.nanoTime();
			hack[0] = solveRandomly(board);
			after = System.nanoTime();
			average.add(after - before);
			System.out.println(hack[0] + "\n");
		}
		System.out.println("Median: " + average.mean() + " nanoseconds");
		System.out.println("-.-.-.-.-.-.-.-.-.-.-.-");
	}
	
	public static Board solveRandomly(Board pBoard) {
		Random random = new Random();
		int size = pBoard.getSize();
		Board[] boards = new Board[5];
		for(int j = 0; j < 5; ++j) {
			Board board = null;
			while(board == null || (board.getPlacedQueens() != size)) {
				try {
					board = (Board) pBoard.clone();
				} catch (CloneNotSupportedException e) {
					throw new AssertionError("wtf?");
				}
				for(int i = 0; i < size; ++i) {
					Set<Position> set = board.getFreeSpots();
					int setSize = set.size();
					if(setSize > 0) {
						Position randomPos = (Position) set.toArray()[random.nextInt(setSize)];
						board = board.putQueen(randomPos.getX(), randomPos.getY());
					} else {
						break;
					}
				}
			}
			boards[j] = board;
		}
		int bestIndex = -1;
		int bestCost = -1;
		for(int i = 0; i < size; ++i) {
			int curCost = boards[i].calculateCosts();
			if(bestCost < curCost) {
				bestCost = curCost;
				bestIndex = i;
			}
		}
		if(bestIndex != -1) {
			return boards[bestIndex];
		} else {
			throw new AssertionError("wtf");
		}
	}

}
