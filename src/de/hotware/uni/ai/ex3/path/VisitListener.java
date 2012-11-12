package de.hotware.uni.ai.ex3.path;


public interface VisitListener {
	
	public void onVisit(QuadNode<Position> pQuadNode);
	
	/**
	 * <code style="dirty">
	 */
	public void onGiveUp(RouteFindingAlgorithm pRouteFindingAlgorithm);
	/**
	 * </code>
	 */

}
