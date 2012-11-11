package de.hotware.uni.ai.ex3;

import java.util.ArrayList;


public final class DefaultQuadNodeMatrix<T> implements QuadNodeMatrix<T> {
	
	protected QuadNode<T>[][] mNodes;
	protected ArrayList<QuadNode<T>> mNorthNodes;
	protected ArrayList<QuadNode<T>> mEastNodes;
	protected ArrayList<QuadNode<T>> mSouthNodes;
	protected ArrayList<QuadNode<T>> mWestNodes;
	
	private DefaultQuadNodeMatrix() {
		this.mNorthNodes = new ArrayList<QuadNode<T>>();
		this.mEastNodes = new ArrayList<QuadNode<T>>();
		this.mSouthNodes = new ArrayList<QuadNode<T>>();
		this.mWestNodes = new ArrayList<QuadNode<T>>();
	}

	@Override
	public ArrayList<QuadNode<T>> getNorthNodes() {
		return this.mNorthNodes;
	}

	@Override
	public ArrayList<QuadNode<T>> getEastNodes() {
		return this.mEastNodes;
	}

	@Override
	public ArrayList<QuadNode<T>> getSouthNodes() {
		return this.mSouthNodes;
	}

	@Override
	public ArrayList<QuadNode<T>> getWestNodes() {
		return this.mWestNodes;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int sizeX = this.mNodes.length;
		int sizeY = this.mNodes[0].length;
		for(int i = 0; i < sizeY; ++i) {
			for(int j = 0; j < sizeX; ++j) {
				builder.append(this.mNodes[j][i]);
			}
			if(i < sizeY - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	public static <T> DefaultQuadNodeMatrix<T> create(QuadNode<T>[][] pNodes) {
		DefaultQuadNodeMatrix<T> ret = new DefaultQuadNodeMatrix<T>();
		ret.mNodes = pNodes;
		int sizeX = pNodes.length;
		int sizeY = pNodes[0].length;
		for(int i = 0; i < sizeX; ++i) {
			ret.mNorthNodes.add(pNodes[i][0]);
			ret.mSouthNodes.add(pNodes[i][sizeY - 1]);
		}
		for(int i = 0; i < sizeY; ++i) {
			ret.mEastNodes.add(pNodes[sizeX - 1][i]);
			ret.mWestNodes.add(pNodes[0][i]);
		}
		return ret;
	}

}
