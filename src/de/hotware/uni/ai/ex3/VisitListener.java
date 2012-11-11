package de.hotware.uni.ai.ex3;


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
