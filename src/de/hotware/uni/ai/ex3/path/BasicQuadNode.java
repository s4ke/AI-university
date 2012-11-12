package de.hotware.uni.ai.ex3.path;

public class BasicQuadNode<T> implements QuadNode<T> {
	
	protected T mValue;
	protected QuadNode<T>[] mNeighbours;

	@SuppressWarnings("unchecked")
	public BasicQuadNode(T pValue) {
		this.mValue = pValue;
		this.mNeighbours = new QuadNode[4];
	}

	@Override
	public T get() {
		return this.mValue;
	}

	@Override
	public void setNorth(QuadNode<T> pNode) {
		this.mNeighbours[0] = pNode;
	}

	@Override
	public QuadNode<T> getNorth() {
		return this.mNeighbours[0];
	}

	@Override
	public void setEast(QuadNode<T> pNode) {
		this.mNeighbours[1] = pNode;
	}

	@Override
	public QuadNode<T> getEast() {
		return this.mNeighbours[1];
	}

	@Override
	public void setSouth(QuadNode<T> pNode) {
		this.mNeighbours[2] = pNode;
	}

	@Override
	public QuadNode<T> getSouth() {
		return this.mNeighbours[2];
	}

	@Override
	public void setWest(QuadNode<T> pNode) {
		this.mNeighbours[3] = pNode;
	}

	@Override
	public QuadNode<T> getWest() {
		return this.mNeighbours[3];
	}
	
	@Override
	public String toString() {
		return this.mValue.toString();
	}

	@Override
	public QuadNode<T>[] getNeighbours() {
		return this.mNeighbours;
	}

}
