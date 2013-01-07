package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class BoardImpl implements Board {
	
	protected List<Unit> mWhiteUnits;
	protected List<Unit> mBlackUnits;
	protected boolean mIsOwnerWhite;

	public BoardImpl(boolean pIsOwnerWhite, List<Unit> pWhiteUnits, List<Unit> pBlackUnits) {
		this.mWhiteUnits = new ArrayList<>();
		this.mBlackUnits = new ArrayList<>();
		//TODO: more input checking if needed
		this.mWhiteUnits.addAll(pWhiteUnits);
		this.mBlackUnits.addAll(pBlackUnits);
		this.mIsOwnerWhite = pIsOwnerWhite;
	}

	@Override
	public List<Unit> getWhiteUnits() {
		return this.mWhiteUnits;
	}

	@Override
	public List<Unit> getBlackUnits() {
		return this.mBlackUnits;
	}

	@Override
	public boolean isOwnerWhite() {
		return this.mIsOwnerWhite;
	}

	@Override
	public EndType end() {
		if(this.mBlackUnits.size() == 0) {
			return EndType.WHITE;
		} else if(this.mWhiteUnits.size() == 0) {
			return EndType.BLACK;
		}
		Unit king = this.mBlackUnits.get(0);
		List<Point2D> escapes = 
				king.getType().
					getReachedPositions(king.getPosition());
		if(escapes.size() == 0) {
			return EndType.DRAW;
		}
		return EndType.RUNNING;
	}
		
	@Override
	public boolean isPositionAllowed(Unit pUnit, Point2D pPosition) {
		boolean taken = false;
		List<Unit> ownUnits = pUnit.isOwnerWhite() ? this.mWhiteUnits : this.mBlackUnits;
		for(Unit unit : ownUnits) {
			if(unit != pUnit && unit.getPosition().equals(pPosition)) {
				taken = true;
			}
		}
		return !taken && pUnit.getType().
				getReachedPositions(pUnit.getPosition()).contains(pPosition);
	}

	@Override
	public Board move(Unit pUnit, Point2D pPosition) {
		//if(pUnit.isOwnerWhite() != this.mIsOwnerWhite) {
		//throw new IllegalArgumentException("you can't move units that dont belong to you");
		//}
		//firstly check if position isn't already in use
		if(!this.isPositionAllowed(pUnit, pPosition)) {
			throw new IllegalArgumentException("Position isn't allowed");
		}
		List<Unit> tmpBlack = new ArrayList<Unit>(this.mBlackUnits);
		List<Unit> tmpWhite = new ArrayList<Unit>(this.mWhiteUnits);
		List<Unit> enemyUnits = pUnit.isOwnerWhite() ? tmpBlack : tmpWhite;
		//check if there is a black unit there and delete it
		Unit rem = null;
		for(Unit unit : enemyUnits) {
			if(unit.getPosition().equals(pPosition)) {
				rem = unit;
				break;
			}
		}
		if(rem != null) {
			enemyUnits.remove(rem);
		}
		Unit clone = pUnit.copy();
		clone.setPosition(pPosition);
		if(pUnit.isOwnerWhite()) {
			tmpWhite.remove(pUnit);
			tmpWhite.add(clone);
		} else {
			tmpBlack.remove(pUnit);
			tmpBlack.add(clone);
		}
		BoardImpl ret = new BoardImpl(this.mIsOwnerWhite, tmpWhite, tmpBlack);
		return ret;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[ white: ")
				.append(this.mWhiteUnits).append(", black: ")
				.append(this.mBlackUnits).append(", isOwnerWhite: ")
				.append(this.mIsOwnerWhite).append("]").toString();
	}

}
