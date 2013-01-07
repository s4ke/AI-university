package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class BoardImpl implements Board {
	
	protected List<Unit> mWhiteUnits;
	protected List<Unit> mBlackUnits;
	protected boolean mIsOwnerWhite;

	public BoardImpl(List<Unit> pWhiteUnits, List<Unit> pBlackUnits, boolean pIsOwnerWhite) {
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
	public double eval() {
		double ret = 0;
		//material evaluation function
		double ownValue = 0;
		List<Unit> own = this.mIsOwnerWhite ? this.mWhiteUnits : this.mBlackUnits;
		for(Unit unit : own) {
			ownValue += unit.getType().getWeight();
		}
		List<Unit> other = this.mIsOwnerWhite ? this.mBlackUnits : this.mWhiteUnits;
		double otherValue = 0;
		for(Unit unit : other) {
			otherValue = unit.getType().getWeight();
		}
		ret = ownValue - otherValue;
		//and now let the black king survive as long as possible
		//FIXME: hard coded for faster implementation
		Unit king = this.mBlackUnits.get(0);
		List<Point2D> escapes = 
				king.getType().
					getReachedPositions(king.getPosition());
		//find escapes that are not blocked
		for(Unit unit : this.mWhiteUnits) {
			for(Point2D pt : unit.getType().getReachedPositions(unit.getPosition())) {
				escapes.remove(pt);
			}
		}
		int size = escapes.size();
		if(size > 0) {
			if(this.mIsOwnerWhite) {
				ret -= size * 10;
			} else {
				ret -= size * 10;
			}
		} else {
			if(this.mIsOwnerWhite) {
				ret -= 1000;
			} else {
				ret += 1000;
			}
		}
		return ret;
	}
	
	@Override
	public boolean isPositionAllowed(Unit pUnit, Point2D pPosition) {
		boolean taken = false;
		List<Unit> ownUnits = this.mIsOwnerWhite ? this.mWhiteUnits : this.mBlackUnits;
		for(Unit unit : ownUnits) {
			if(unit.getPosition().equals(pPosition)) {
				taken = true;
			}
		}
		return !taken && pUnit.getType().
				getReachedPositions(pPosition).contains(pPosition);
	}

	@Override
	public Board move(Unit pUnit, Point2D pPosition) {
		//if(pUnit.isOwnerWhite() != this.mIsOwnerWhite) {
		//throw new IllegalArgumentException("you can't move units that dont belong to you");
		//}
		//firstly check if position isn't already in use
		if(!this.isPositionAllowed(pUnit, pPosition)) {
			throw new IllegalArgumentException("Position isn't free");
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
		BoardImpl ret = new BoardImpl(tmpWhite, tmpBlack, this.mIsOwnerWhite);
		return ret;
	}

}
