package de.hotware.uni.ai.connectfour;

public interface IBoard {
	
	public char get(int i, int j);

	public boolean set(int i, int j, char value);

	public int limitN();

	public int limitM();

	/**
	 * @return 
	 * 		<ul>
	 * 				<li>1  == ich habe gewonnen </li>
	 *         		<li>-1 == ich habe verloren </li>
	 *         		<li>0  == untentschieden </li>
	 *        		<li>42 == Spiel laeuft noch </li>
	 *      </ul>
	 */
	public int gameOver();

}
