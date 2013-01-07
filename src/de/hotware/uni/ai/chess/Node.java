package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hotware.uni.ai.chess.Board.EndType;

/**
 * quick implementation of a node (for a tree)
 * which works accordingly to the minimax
 * algorithm
 * @author Martin Braun (1249080)
 */
public class Node implements Comparable<Node> {

	protected Board mBoard;
	protected List<Node> mChildren;
	protected double mValue;
	
	/**
	 * creates a new node with the given board
	 * and the given parent
	 */
	public Node(Board pBoard) {
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
	public double getValue() {
		return this.mValue;
	}
	
	public void initChildren(int pDepth, boolean pOwnTurn, boolean pIsOwnerWhite) {
		if(Constants.DEBUG) {
			System.out.println("Node.initChildren(): depth: " + pDepth + "ownturn: " + pOwnTurn);
		}
		//search all legitimate turns if the wanted depth has
		//not yet been reached
		if(pDepth > 0 && this.mBoard.end() == EndType.RUNNING) {
			List<Unit> units = null;
			if(pIsOwnerWhite) {
				if(pOwnTurn) {
					units = this.mBoard.getWhiteUnits();
				} else {
					units = this.mBoard.getBlackUnits();
				}
			} else {
				if(pOwnTurn) {
					units = this.mBoard.getBlackUnits();
				} else {
					units = this.mBoard.getWhiteUnits();
				}
			}
			for(Unit unit : units) {
				for(Point2D pt : unit.getType().getReachedPositions(unit.getPosition())) {
					if(this.mBoard.isPositionAllowed(unit, pt)) {
						Board board = this.mBoard.move(unit, pt);
						Node node = new Node(board);
						this.mChildren.add(node);
					}
				}
			}
			//init all children
			for(Node child : this.mChildren) {
				child.initChildren(pDepth - 1, !pOwnTurn, pIsOwnerWhite);
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
			this.mValue = calcCosts(this.mBoard, pIsOwnerWhite);
			if(Constants.DEBUG) {
				System.out.println("Node.initChildren(): depth reached, value: " + this.mValue);
			}
		}
	}

	public static double calcCosts(Board pBoard, boolean pIsOwnerWhite) {
		double ret = 0;
		//material evaluation function
		double ownValue = 0;
		List<Unit> own = pIsOwnerWhite ? pBoard.getWhiteUnits() : pBoard.getBlackUnits();
		for(Unit unit : own) {
			ownValue += unit.getType().getWeight();
		}
		List<Unit> other = pIsOwnerWhite ? pBoard.getBlackUnits() : pBoard.getWhiteUnits();
		double otherValue = 0;
		for(Unit unit : other) {
			otherValue = unit.getType().getWeight();
		}
		ret = ownValue - otherValue;
		//and now let the black king survive as long as possible
		//FIXME: hard coded for faster implementation
		if(pBoard.getBlackUnits().size() > 0) {
			Unit king = pBoard.getBlackUnits().get(0);
			List<Point2D> escapes = 
					king.getType().
						getReachedPositions(king.getPosition());
			//find escapes that are not blocked
			for(Unit unit : pBoard.getWhiteUnits()) {
				for(Point2D pt : unit.getType().getReachedPositions(unit.getPosition())) {
					escapes.remove(pt);
				}
			}
			int size = escapes.size();
			if(size > 0) {
				if(pIsOwnerWhite) {
					ret -= size * 10;
				} else {
					ret -= size * 10;
				}
			} else {
				if(pIsOwnerWhite) {
					ret -= 1000;
				} else {
					ret += 1000;
				}
			}
		}
		return ret;
	}
	
	public Node getNextBestNode() {
		return Collections.max(this.mChildren);
	}

	@Override
	public int compareTo(Node pNode) {
		return (int) (this.mValue - pNode.mValue);
	}

}