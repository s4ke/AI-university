package de.hotware.uni.ai.ex3;

public class BasicQuadNode<T> implements QuadNode<T> {
	
	protected T mValue;
	protected QuadNode<T> mNorth;
	protected QuadNode<T> mEast;
	protected QuadNode<T> mSouth;
	protected QuadNode<T> mWest;

	public BasicQuadNode(T pValue) {
		this.mValue = pValue;
	}

	@Override
	public T get() {
		return this.mValue;
	}

	@Override
	public void setNorth(QuadNode<T> pNode) {
		this.mNorth = pNode;
	}

	@Override
	public QuadNode<T> getNorth() {
		return this.mNorth;
	}

	@Override
	public void setEast(QuadNode<T> pNode) {
		this.mEast = pNode;
	}

	@Override
	public QuadNode<T> getEast() {
		return this.mEast;
	}

	@Override
	public void setSouth(QuadNode<T> pNode) {
		this.mSouth = pNode;
	}

	@Override
	public QuadNode<T> getSouth() {
		return this.mSouth;
	}

	@Override
	public void setWest(QuadNode<T> pNode) {
		this.mWest = pNode;
	}

	@Override
	public QuadNode<T> getWest() {
		return this.mWest;
	}
	
	@Override
	public String toString() {
		return this.mValue.toString();
	}

}
