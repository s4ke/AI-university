package de.hotware.uni.ai.chess;

import java.awt.geom.Point2D;


public class UnitImpl implements Unit, Cloneable {
	
	protected Point2D mPosition;
	protected Type mType;
	protected boolean mIsOwnerWhite;
	
	public UnitImpl(boolean pIsOwnerWhite, Type pType, Point2D pPosition) {
		this.mType = pType;
		this.mPosition = pPosition;
		this.mIsOwnerWhite = pIsOwnerWhite;
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

	@Override
	public Unit copy() {
		try {
			return (Unit) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new AssertionError("wtf?");
		}
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[")
				.append(this.mType.getStringRepresentation())
				.append(", ").append(this.mPosition)
				.append("]").toString();
	}

}
