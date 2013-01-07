package de.hotware.uni.ai.pacman;

import java.lang.reflect.InvocationTargetException;

/**
 * Wrapper class for easy invocation of all
 * the Pacman stuff in this exercise.
 * Idea by: David Bauske and 
 * http://stackoverflow.com/questions/283816/how-to-access-java-classes-in-the-default-package
 * @author Martin Braun
 */
public class PacmanImpl implements Pacman {
	
	private static final Class<?>[] INTEGER_PARAM = new Class<?>[]{int.class};
	private static final boolean DEBUG = false;
	
	protected Object mPacman;
	
	public PacmanImpl() {
		try {
			Class<?> pacmanClass = Class.forName("Pacman");
			this.mPacman = pacmanClass.newInstance();
		} catch(ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int[][] getMap() {
		return (int[][]) callOnPacman(this.mPacman, "getMap", null);
	}

	@Override
	public int getPoints() {
		return (int) callOnPacman(this.mPacman, "getPoints", null);
	}

	@Override
	public int[][] getGoldLifetime() {
		return (int[][]) callOnPacman(this.mPacman, "getGoldLifetime", null);
	}

	@Override
	public int getRemainingPoints() {
		return (int) callOnPacman(this.mPacman, "getRemainingPoints", null);
	}

	@Override
	public void init(int level) {
		callOnPacman(this.mPacman, "init", INTEGER_PARAM, level);
	}

	@Override
	public int[][] move(int direction) {
		return (int[][]) callOnPacman(this.mPacman, "move", INTEGER_PARAM, direction);
	}

	@Override
	public void setSpeed(int time) {
		callOnPacman(this.mPacman, "setSpeed", INTEGER_PARAM, time);
	}
	
	@Override
	public int[] getPositions() {
		return (int[]) callOnPacman(this.mPacman, "getPositions", null);
	}
	
	@Override
	public PositionReturn getGhostPositions() {
		PositionReturn ret = new PositionReturn();
		int[] positions = this.getPositions();
		Ghost[] ghosts = Ghost.values();
		for(int i = 2; i < positions.length / 2 && i < 5; ++i) {
			int x = positions[i];
			int y = positions[i + 1];
			if(x == -1 || y == -1) {
				continue;
			}
			ret.mMap.put(ghosts[i - 1], new Node(positions[i], positions[i + 1]));
		}
		if(DEBUG) {
			System.out.println(ret.mMap);
		}
		return ret;
	}

	@Override
	public boolean gameLost() {
		return (Boolean) callOnPacman(this.mPacman, "gameLost", null);
	}

	@Override
	public boolean gameWon() {
		return (Boolean) callOnPacman(this.mPacman, "gameWon", null);
	}
	
	private static Object callOnPacman(Object pPacman, String pMethodName, Class<?>[] pParamTypes, Object... pArgs) {
		try {
			return pPacman.getClass().getMethod(pMethodName, pParamTypes).invoke(pPacman, pArgs);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
