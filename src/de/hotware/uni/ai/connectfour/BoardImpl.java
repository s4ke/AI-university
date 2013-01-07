package de.hotware.uni.ai.connectfour;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Braun (1249080)
 */
public class BoardImpl implements IBoard {
	
	protected char[][] mBoard;
	protected char mOwnerChar;
	protected List<List<Point2D>> mWinningSequences;
	
	/**
	 * creates a new BoardImpl with the given ownerchar
	 * and the default size
	 */
	public BoardImpl(char pOwnerChar) {
		this(pOwnerChar, Constants.SIZE_X, Constants.SIZE_Y);
	}
	
	/**
	 * creates a new BoardImpl with the given ownerchar
	 * and the given board. this will be deep-copied
	 */
	public BoardImpl(char pOwnerChar, IBoard pBoard) {
		this(pOwnerChar, pBoard.limitM(), pBoard.limitN());
		int x = pBoard.limitM();
		int y = pBoard.limitN();
		for(int i = 0; i < x; ++i) {
			for(int j = 0; j < y; ++j) {
				this.mBoard[i][j] = pBoard.get(i, j);
			}
		}
	}
	
	/**
	 * creates a new BoardImpl with the given ownerChar
	 * and the given size
	 */
	public BoardImpl(char pOwnerChar, int pSizeX, int pSizeY) {
		if(pSizeX <= 0 || pSizeY <= 0) {
			throw new IllegalArgumentException("illegal pSizeX or pSizeY");
		}
		this.mOwnerChar = pOwnerChar;
		this.mBoard = new char[pSizeX][pSizeY];
		for(int i = 0; i < pSizeX; ++i) {
			for(int j = 0; j < pSizeY; ++j) {
				this.mBoard[i][j] = ' ';
			}
		}
		this.mWinningSequences = Util.initializeAndGetWinningSequences(Constants.DEFAULT_SIZE);
	}

	@Override
	public char get(int i, int j) {
		return this.mBoard[i][j];
	}

	@Override
	public boolean set(int i, int j, char value) {
		boolean ret = false;
		if(this.mBoard[i][j] == ' ') {
			this.mBoard[i][j] = value;
			ret = true;
		}
		return ret;
	}
		
	@Override
	public int limitN() {
		return this.mBoard[0].length;
	}

	@Override
	public int limitM() {
		return this.mBoard.length;
	}

	@Override
	public int gameOver() {
		int ret = 0;
		//empty slots are sequences of length 0 of any kind
		boolean empty = Util.getAmountOfSequences(this, 0, Constants.X) > 0;
		boolean winX = Util.getAmountOfSequences(this, Constants.AMOUNT_TO_WIN, Constants.X) > 0;
		boolean winO = Util.getAmountOfSequences(this, Constants.AMOUNT_TO_WIN, Constants.O) > 0;
		if(winO && winX) {
			throw new AssertionError("only one person can win!");
		} else if(winX || winO) {
			if(hasWon(this.mOwnerChar, winX, winO)) {
				ret = Constants.GameState.WIN.mRepresentation;
			} else {
				ret = Constants.GameState.LOSE.mRepresentation;
			}
		} else if(empty) {
			ret = Constants.GameState.RUNNING.mRepresentation;
		} else {
			ret = Constants.GameState.DRAW.mRepresentation;
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object pOther) {
		BoardImpl board;
		if (pOther instanceof BoardImpl){
			board = (BoardImpl) pOther;
		} else { 
			return false;
		}
		if(this.mOwnerChar != board.mOwnerChar || 
				this.limitM() != board.limitM() || 
				this.limitN() != board.limitN()) {
			return false;
		}
		//the sizes match and the owner char is the same
		//now check the board if it's the same
		int sizeX = this.limitM();
		int sizeY = this.limitN();
		for(int i = 0; i < sizeX; ++i) {
			for(int j = 0; j < sizeY; ++j) {
				if(this.mBoard[i][j] != board.mBoard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * we need this hashing function so
	 * we don't pollute our heap with unneeded
	 * data
	 */
	@Override
	public int hashCode() {
		//as 42 is no prime number, we use his brother :)
		final int prime = 43;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(this.mBoard);
		return result;
	}
	
	/**
	 * the ugly toString() method
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int x = this.limitM();
		int y = this.limitN();
		for(int j = 0; j < y; ++j) {
			if(j != 0) {
				builder.append("\n");
			}
			builder.append("+");
			for(int i = 0; i < x; ++i) {
				builder.append("++");
			}
			builder.append("\n|");
			for(int i = 0; i < x; ++i) {
				builder.append(this.mBoard[i][j]);
				builder.append("|");
			}
		}
		builder.append("\n+");
		for(int i = 0; i < x; ++i) {
			builder.append("++");
		}
		return builder.toString();
	}
	
	/**
	 * internal method to determine a winner
	 */
	private static boolean hasWon(char pUserChar, boolean pWinX, boolean pWinO) {
		if(pWinO && pWinX) {
			throw new AssertionError("only one person can win!");
		} else if(pWinX) {
			return pUserChar == Constants.X;
		} else {
			return pUserChar == Constants.O;
		}
	}

}
