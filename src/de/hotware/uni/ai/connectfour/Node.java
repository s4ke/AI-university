package de.hotware.uni.ai.connectfour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * quick implementation of a node (for a tree)
 * which works accordingly to the minimax
 * algorithm
 * @author Martin Braun (1249080)
 */
public class Node implements Comparable<Node> {
	
	/**
	 * prime numbers are cool
	 * PRIMES[i + 1] = nextPrime(PRIMES[i] * 2)
	 */
	private static final int[] PRIMES = {
		3, 7, 17, 37, 79
	};
	
	protected IBoard mBoard;
	protected List<Node> mChildren;
	protected int mValue;
	
	/**
	 * creates a new node with the given board
	 * and the given parent
	 */
	public Node(IBoard pBoard) {
		this.mBoard = pBoard;
		this.mChildren = new ArrayList<Node>();
	}
	
	/**
	 * adds a child to the nodes children list
	 * @param pNode the node to add
	 */
	public void addChild(Node pNode) {
		this.mChildren.add(pNode);
	}
	
	/**
	 * @return the value according to the minimax algorithm
	 */
	public int getValue() {
		return this.mValue;
	}
	
	public void initChildren(int pDepth, char pUserChar, Map<IBoard, Node> pMap, boolean pOwnTurn) {
		if(Constants.DEBUG) {
			System.out.println("Node.initChildren(): depth: " + pDepth + " userchar: " 
					+ pUserChar + " map: " + pMap + "ownturn: " + pOwnTurn);
		}
		//search all legitimate turns if the wanted depth has
		//not yet been reached
		if(pDepth > 0 && this.mBoard.gameOver() == Constants.GameState.RUNNING.mRepresentation) {
			int sizeX = this.mBoard.limitM();
			int sizeY = this.mBoard.limitN();
			for(int i = 0; i < sizeX; ++i) {
				for(int j = 0; j < sizeY; ++j) {
					if(this.mBoard.get(i, j) == Constants.EMPTY) {
						//board is free at (i, j), so create a new node
						BoardImpl board = new BoardImpl(pUserChar, this.mBoard);
						if(!board.set(i, j, pOwnTurn ? pUserChar : Util.getOther(pUserChar))) {
							throw new AssertionError("position was not empty");
						}
						//we cache all the generated nodes
						//so we don't have them two times
						//in our tree
						Node node = pMap.get(board);
						if(node == null) {
							node = new Node(board);
							pMap.put(board, node);
						}
						this.mChildren.add(node);
					}
				}
			}
			//init all children
			for(Node child : this.mChildren) {
				child.initChildren(pDepth - 1, pUserChar, pMap, !pOwnTurn);
			}
			//now that the tree has been built, we
			//have to initialize it in a bottom up fashion
			Node node;
			//shuffle the children to get different nodes for
			//the same value
			Collections.shuffle(this.mChildren);
			if(pOwnTurn) {
				node = Collections.max(this.mChildren);
			} else {
				node = Collections.min(this.mChildren);
			}
			if(Constants.DEBUG) {
				System.out.println("Node.initChildren(): got value from child: " + this.mValue);
			}
			this.mValue = node.mValue;
		} else {
			//maximum depth reached or someone has won, estimate value of this board
			this.mValue = calcCosts(this.mBoard, pUserChar);
			if(Constants.DEBUG) {
				System.out.println("Node.initChildren(): depth reached, value: " + this.mValue);
			}
		}
	}
	
	public Node getNextBestNode() {
		return Collections.max(this.mChildren);
	}
	
	public static int calcCosts(IBoard pBoard, char pUserChar) {
		//TODO: weighing for sequences longer than 5
		//we use primes to weigh the costs
		//five gets weighed also, because such a sequence is
		//the most awesome way to win!
		int one = (Util.getAmountOfSequences(pBoard, 1, pUserChar) 
				- Util.getAmountOfSequences(pBoard, 1, Util.getOther(pUserChar))) * PRIMES[0];
		int two = (Util.getAmountOfSequences(pBoard, 2, pUserChar) 
				- Util.getAmountOfSequences(pBoard, 2, Util.getOther(pUserChar))) * PRIMES[1];
		int three = (Util.getAmountOfSequences(pBoard, 3, pUserChar) 
				- Util.getAmountOfSequences(pBoard, 3, Util.getOther(pUserChar))) * PRIMES[2];
		int four = (Util.getAmountOfSequences(pBoard, 4, pUserChar) 
				- Util.getAmountOfSequences(pBoard, 4, Util.getOther(pUserChar))) * PRIMES[3];
		int five = (Util.getAmountOfSequences(pBoard, 5, pUserChar) 
				- Util.getAmountOfSequences(pBoard, 5, Util.getOther(pUserChar))) * PRIMES[4];
		return one + two + three + four + five;
	}

	@Override
	public int compareTo(Node pNode) {
		return this.mValue - pNode.mValue;
	}

}
