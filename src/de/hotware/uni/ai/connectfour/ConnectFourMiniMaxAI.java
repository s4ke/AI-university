package de.hotware.uni.ai.connectfour;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * a minimax AI for ConnectFour on a 5x5 board
 * @author Martin Braun (1249080)
 */
public class ConnectFourMiniMaxAI implements IAI {
	
	protected char mToken;
	
	public ConnectFourMiniMaxAI() {
		this.mToken = 0;
	}

	@Override
	public void init(char pToken) {
		this.mToken = pToken;
	}

	@Override
	public Point2D update(IBoard pBoard) {
		if(this.mToken == 0) {
			throw new IllegalStateException("token has not been set, yet");
		}
		if(pBoard.gameOver() != Constants.GameState.RUNNING.mRepresentation) {
			throw new AssertionError("gamestate isn't equal to running");
		}
		//build the graph for minimax
		Node root = new Node(pBoard);
		root.initChildren(5, this.mToken, new HashMap<IBoard, Node>(), true);
		
		//get the next best node
		Node bestChild = root.getNextBestNode();
		//get the difference between the root and the best child
		int sizeX = pBoard.limitM();
		int sizeY = pBoard.limitN();
		boolean found = false;
		int x = -1;
		int y = -1;
		for(int i = 0; i < sizeX; ++i) {
			for(int j = 0; j < sizeY; ++j) {
				if(root.mBoard.get(i, j) != bestChild.mBoard.get(i, j)) {
					if(found) {
						throw new AssertionError("boards differ in two locations!");
					}
					x = i;
					y = j;
					found = true;
				}
			}
		}
		if(x == -1 || y == -1) {
			throw new AssertionError("boards don't differ");
		}
		return new Point(x, y);
	}

}
