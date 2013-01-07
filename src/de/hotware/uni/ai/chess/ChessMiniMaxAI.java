package de.hotware.uni.ai.chess;

import java.util.List;

import de.hotware.uni.ai.chess.Board.EndType;


public class ChessMiniMaxAI implements AI {
	
	protected boolean mIsOwnerWhite;
	
	public ChessMiniMaxAI(boolean pIsOwnerWhite) {
		this.mIsOwnerWhite = pIsOwnerWhite;
	}

	@Override
	public Move calculateNextMove(Board pBoard) {
		if(pBoard.end() != EndType.RUNNING) {
			throw new AssertionError("gamestate isn't equal to running");
		}
		//build the graph for minimax
		Node root = new Node(pBoard);
		root.initChildren(4, true, this.mIsOwnerWhite);
		
		//get the next best node
		Node bestChild = root.getNextBestNode();
		//get the difference between the root and the best child
		Move ret = new Move();
		boolean done = false;
		List<Unit> ownUnits = this.mIsOwnerWhite ? pBoard.getWhiteUnits() : pBoard.getBlackUnits();
		List<Unit> bestOwnUnits = this.mIsOwnerWhite ? bestChild.mBoard.getWhiteUnits() : bestChild.mBoard.getBlackUnits();
		for(Unit unit : bestOwnUnits) {
			if(!ownUnits.contains(unit)) {
				if(!done) {
					ret.mUnit = unit;
					ret.mPoint = unit.getPosition();
					done = true;
				} else {
					throw new AssertionError("boards differ in more than one Unit");
				}
			}
		}
		if(!done) {
			throw new AssertionError("boards dont differ");
		}
		return ret;
	}

}
