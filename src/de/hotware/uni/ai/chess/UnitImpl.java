package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;


public class UnitImpl implements Unit {
	
	protected Point2D mPosition;
	protected Type mType;
	protected boolean mIsOwnerWhite;
	
	public UnitImpl(Type pType, Point2D pPosition, boolean pIsOwnerWhite) {
		this.mType = pType;
		this.mPosition = pPosition;
	}
	
	@Override
	public Point2D getPosition() {
		return this.mPosition;
	}
	
	@Override
	public void setPosition(Point2D pPosition) {
		this.mPosition = pPosition;
	}

	@Override
	public Type getType() {
		return this.mType;
	}

	@Override
	public boolean isOwnerWhite() {
		return this.mIsOwnerWhite;
	}

}
