package de.hotware.uni.ai.ex3;

import java.util.ArrayList;

public interface QuadNodeMatrix<T> {
	
	public ArrayList<QuadNode<T>> getNorthNodes();
	public ArrayList<QuadNode<T>> getEastNodes();
	public ArrayList<QuadNode<T>> getSouthNodes();
	public ArrayList<QuadNode<T>> getWestNodes();

}
