package de.hotware.uni.ai.pacman;

/**
 * @author Martin Braun (1249080)
 */
public interface PacmanIntelligence {
	
	public void init(Pacman pPacman);
	
	public Direction getNextDirection();

}
