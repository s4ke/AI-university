package de.hotware.uni.ai.pacman;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maximilian Reischl
 */
public interface Pacman{

	/**
	 * Returns the actual map
	 * 
	 * @return int[][] returned
	 */
	int[][] getMap();
	
	/**
	 * Returns the actual points
	 * 
	 * @return int returned
	 */
	int getPoints();

    /**
	* Returns the lifetime for goldpieces for current iteration.
	* 0 if not gold, 255 for non-disappearing gold.
	*
	*/
    int[][] getGoldLifetime();
	
	/**
	 * Returns the remaining points
	 * 
	 * @return int returned
	 */
	int getRemainingPoints();
	
	/**
	 * Initializes Pacman and the level 0-3
	 * * @param int accepts 0-3 for levels
	 */
	void init(int level);
	
	/**
	 * Moves Pacman according to the direction indicated by the numpad, i.e. 8 for up and returns the next map
	 * 
	 * @param direction accepts 2,4,5,6,8
	 * @return int[][] returned
	 */
	int[][] move(int direction);
	
	/**
	 * Sets the minimum time move() takes to return, default is 100
	 * 
	 * @param time in ms
	 */
	void setSpeed(int time);
	
	/**
	 * Returns the Positions of Pacman[0,1], Blinky[2,3], Pinky[4,5], Inky[6,7] and Clyde[8,9]. -1 if not existent
	 * 
	 * @return int[10]
	 */
	int[] getPositions();
	
	/**
	 * Return the current gamestate
	 * 
	 * @return bool
	 */
	boolean gameLost();
	boolean gameWon();

	public PositionReturn getGhostPositions();
	
	public enum Ghost {
		Blinky,
		Pinky,
		Inky,
		Clyde;
	}
	
	public static class PositionReturn {
		
		public Map<Ghost, Node> mMap;
		
		public PositionReturn() {
			this.mMap = new HashMap<>();
		}
		
		@Override
		public String toString() {
			return this.mMap.toString();
		}
		
	}
		
}