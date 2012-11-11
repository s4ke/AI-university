package de.hotware.uni.ai.nQueens;

import java.util.Set;
import java.util.HashSet;

public class BasicBoard implements Board, Cloneable {

	protected Position[][] mPositions;
	protected Position mLastChangedPosition;
	protected Set<Position> mFreeSpots;
	protected int mPlacedQueens;

	public BasicBoard(int pSize) {
		this.mPositions = new Position[pSize][pSize];
		this.mFreeSpots = new HashSet<>();
		for(int i = 0; i < pSize; ++i) {
			for(int j = 0; j < pSize; ++j) {
				this.mFreeSpots.add(this.mPositions[j][i] = new BasicPosition(i, j, Position.Type.EMPTY));
			}
		}
		this.mLastChangedPosition = null;
		this.mPlacedQueens = 0;
	}

	@Override
	public boolean check(int pX, int pY) {
		if(!this.isInField(pX, pY)) {
			throw new IllegalArgumentException(
					"pX and pY exceeded the bounds of the Board");
		}
		return this.mPositions[pY][pX].isAllowed();
	}

	private boolean isInField(int pX, int pY) {
		int size = this.mPositions.length;
		return pX < size && pX >= 0 && pY < size && pY >= 0;
	}
	
	public Board putQueen(int pX, int pY) {
		if(!this.check(pX, pY)) {
			throw new IllegalArgumentException("(" + pX + "," + pY + ") is not free or is tagged");
		}
		BasicBoard board;
		try {
			board = (BasicBoard) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("couldn't clone the board. Out of mana!");
		}
		board.mFreeSpots.remove(board.mPositions[pY][pX]);
		board.mPositions[pY][pX]= board.mLastChangedPosition = BasicPosition.change(board.mPositions[pY][pX], Position.Type.QUEEN);
		++board.mPlacedQueens;
		board.tag(pX, pY);
		return board;
	}

	/**
	 * legacy method of the old practice
	 */
	@Deprecated
	@Override
	public Board putQueens() {
		int size = this.mPositions.length;
		Board ret = null;
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				if (this.check(i, j)) {
					ret = this.putQueen(i, j);
					break;
				}
			}
			if(ret != null) {
				break;
			}
		}
		return ret;
	}
	
	@Override
	public Position getLastChangedPosition() {
		return this.mLastChangedPosition;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		BasicBoard board = (BasicBoard) super.clone();
		int size = board.mPositions.length;
		board.mPositions = new BasicPosition[size][size];
		board.mFreeSpots = new HashSet<Position>();
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				Position original = this.mPositions[i][j];
				Position clone = board.mPositions[i][j] = (Position) this.mPositions[i][j].clone();
				if(this.mFreeSpots.contains(original)) {
					board.mFreeSpots.add(clone);
				}
			}
		}
		if(this.mLastChangedPosition != null) {
			board.mLastChangedPosition = (Position) this.mLastChangedPosition.clone();
		}
		return board;
	}
	
	@Override
	public int getSize() {
		return this.mPositions.length;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int size = this.mPositions.length;
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				builder.append('[').append(this.mPositions[i][j]).append(']');
			}
			if(i < size - 1) {
				builder.append('\n');
			}
		}
		return builder.toString();
	}
	
	@Override
	public int getPlacedQueens() {
		return this.mPlacedQueens;
	}
	
	@Override
	public int calculateCosts() {
		int ret = 0;
		int size = this.getSize();
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				ret += this.mPositions[i][j].getType().getCosts();
			}
		}
		return ret;
	}
	
	@Override
	public int getFreeSpotCount() {
		return this.mFreeSpots.size();
	}
	
	@Override
	public Set<Position> getFreeSpots() {
		return this.mFreeSpots;
	}
	
	private void tag(int pX, int pY) {
		int leftDiagStart = pY - pX;
		int rightDiagStart = pY + pX;
		int size = this.mPositions.length;
		for(int i = 0; i < size; ++i) {
			if(this.mPositions[i][pX].isAllowed()) {
				this.mFreeSpots.remove(this.mPositions[i][pX]);
				this.mPositions[i][pX] = BasicPosition.change(this.mPositions[i][pX], Position.Type.TAGGED);
			}
			if(this.mPositions[pY][i].isAllowed()) {
				this.mFreeSpots.remove(this.mPositions[pY][i]);
				this.mPositions[pY][i] = BasicPosition.change(this.mPositions[pY][i], Position.Type.TAGGED);
			}
			if(this.isInField(i, leftDiagStart + i) && this.mPositions[leftDiagStart + i][i].isAllowed()) {
				this.mFreeSpots.remove(this.mPositions[leftDiagStart + i][i]);
				this.mPositions[leftDiagStart + i][i] = BasicPosition.change(this.mPositions[leftDiagStart + i][i], Position.Type.TAGGED);
			}
			if(this.isInField(rightDiagStart - i, i) && this.mPositions[i][rightDiagStart - i].isAllowed()) {
				this.mFreeSpots.remove(this.mPositions[i][rightDiagStart - i]);
				this.mPositions[i][rightDiagStart - i] = BasicPosition.change(this.mPositions[i][rightDiagStart - i], Position.Type.TAGGED);
			}
		}
	}

	public static class BasicPosition implements Position {
		
		protected int mX;
		protected int mY;
		protected Position.Type mType;
		
		public BasicPosition(int pX, int pY, Position.Type pType) {
			this.mX = pX;
			this.mY = pY;
			this.mType = pType;
		}

		@Override
		public int getX() {
			return this.mX;
		}

		@Override
		public int getY() {
			return this.mY;
		}

		@Override
		public Type getType() {
			return this.mType;
		}
		
		@Override
		public boolean isAllowed() {
			return this.mType.isAllowed();
		}
		
		@Override
		public String toString() {
			return this.mType.toString();
		}
		
		@Override
		public Position clone() throws CloneNotSupportedException {
			return (Position) super.clone();
		}
		
		public static Position change(Position pPosition, Position.Type pNewType) {
			return new BasicPosition(pPosition.getX(), pPosition.getY(), pNewType);
		}
		
	}
	
}