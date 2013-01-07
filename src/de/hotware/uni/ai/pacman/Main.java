package de.hotware.uni.ai.pacman;

/**
 * @author Martin Braun (1249080)
 */
public class Main {

	public static void main(String[] args) {
		PacmanImpl p = new PacmanImpl();
		p.init(4);
		p.setSpeed(100);
		PacmanIntelligence intel = new PacmanIntelligenceImpl();
		intel.init(p);
		for(int i = 0; i < 100000 && p.getRemainingPoints() > 0 && 
				!p.gameLost() && !p.gameWon(); ++i) {
			Direction dir = intel.getNextDirection();
			p.move(dir.getRepresentation());
		}
	}

}
