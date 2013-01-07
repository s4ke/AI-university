package de.hotware.uni.ai.connectfour;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for ConnectFour
 * @author Martin Braun (1249080)
 */
public final class Util {
	
	private Util() {
		throw new AssertionError("can't touch this!");
	}
	
	public static final Map<Point2D, List<List<Point2D>>> sAllWinningSequences;
	static {
		sAllWinningSequences = new HashMap<Point2D,List<List<Point2D>>>();
	}
	
	public synchronized static List<List<Point2D>> initializeAndGetWinningSequences(Point2D pPoint2D) {
		if(!sAllWinningSequences.containsKey(pPoint2D)) {
			if(Constants.DEBUG) { 
				System.out.println("creating new winning sequences collection");
			}
			//for this Point2D there is no precalculation done, yet.
			List<List<Point2D>> list = new ArrayList<List<Point2D>>();
			sAllWinningSequences.put(pPoint2D, list);
			int x = (int) pPoint2D.getX();
			int y = (int) pPoint2D.getY();
			
			//initialize all rows
			for(int j = 0; j < y; ++j) {
				for(int col = 0; col <= x - Constants.AMOUNT_TO_WIN; ++col) {
					List<Point2D> cur = new ArrayList<Point2D>();
					for(int i = 0; i < Constants.AMOUNT_TO_WIN; ++i) {
						cur.add(new Point(col + i, j));
					}
					if(Constants.DEBUG) {
						System.out.println("adding row list: " + cur);
					}
					list.add(cur);
				}
			}
			
			//initialize all columns
			for(int i = 0; i < x; ++i) {
				for(int row = 0; row <= y - Constants.AMOUNT_TO_WIN; ++row) {
					List<Point2D> cur = new ArrayList<Point2D>();
					for(int j = 0; j < Constants.AMOUNT_TO_WIN; ++j) {
						cur.add(new Point(i, row + j));
					}
					if(Constants.DEBUG) {
						System.out.println("adding column list: " + cur);
					}
					list.add(cur);
				}
			}
			
			double min = Math.min(x, y);
			
			//initialize all the left hand side diagonals
			for(int curX = 0; curX < x && curX <= min - Constants.AMOUNT_TO_WIN; ++curX) {
				for(int curY = 0; curY < y && curY <= min - Constants.AMOUNT_TO_WIN; ++curY) {
					List<Point2D> curList = new ArrayList<Point2D>();
					for(int i = 0; i < Constants.AMOUNT_TO_WIN; ++i) {
						curList.add(new Point(curX + i, curY + i));
					}
					if(Constants.DEBUG) {
						System.out.println("adding left hand side list: " + curList);
					}
					list.add(curList);
				}
			}
			
			//initialize all the right hand side diagonals
			for(int curX = x; curX - Constants.AMOUNT_TO_WIN >= 0; --curX) {
				for(int curY = 0; curY < y && curY <= min - Constants.AMOUNT_TO_WIN; ++curY) {
					List<Point2D> curList = new ArrayList<Point2D>();
					for(int i = 1; i <= Constants.AMOUNT_TO_WIN; ++i) {
						curList.add(new Point(curX - i, curY + i - 1));
					}
					if(Constants.DEBUG) {
						System.out.println("adding right hand side list: " + curList);
					}
					list.add(curList);
				}
			}
			if(Constants.DEBUG) {
				System.out.println("done creating winning sequences for Dimension: " + pPoint2D);
			}
			sAllWinningSequences.put(pPoint2D, list);
		}
		return sAllWinningSequences.get(pPoint2D);
	}
	
	public static int getAmountOfSequences(IBoard pBoard, int pLength, char pUserChar) {
		int ret = 0;
		int sizeX = pBoard.limitM();
		int sizeY = pBoard.limitN();
		if(pLength != 0) {
			//check all the winning solutions if there is a winner
			for(List<Point2D> list : initializeAndGetWinningSequences(new Point(sizeX, sizeY))) {
				int count = 0;
				for(Point2D point : list) {
					int x = (int) point.getX();
					int y = (int) point.getY();
					char found = pBoard.get(x, y);
					if(found != pUserChar) {
						if(count > 0) {
							if(Constants.DEBUG) {
								System.out.println("broken sequence: length: " + count + ", userChar: " + pUserChar);
							}
							if(count == pLength) {
								//we have found a sequence of the passed length
								++ret;
								if(Constants.DEBUG) {
									System.out.println("amount of found sequences: " + ret);
								}
							}
							//the sequence was broken
							//reset the sequence
							count = 0;
						}
					} else {
						//sequence continues!
						++count;
						if(Constants.DEBUG) {
							System.out.println("sequence starts/continues: length: " + count + ", userChar: " + pUserChar);
						}
					}
				}
				if(count == pLength) {
					//we have found a sequence of the passed length
					++ret;
					if(Constants.DEBUG) {
						System.out.println("amount of found sequences: " + ret);
					}
				}
				//reset the sequence
				count = 0;
			}
		} else {
			for(int i = 0; i < sizeX; ++i) {
				for(int j = 0; j < sizeY; ++j) {
					if(pBoard.get(i, j) == Constants.EMPTY) {
						++ret;
					}
				}
			}
		}
		return ret;
	}
	
	public static List<Point2D> getEmptySpots(IBoard pBoard) {
		List<Point2D> ret = new ArrayList<Point2D>();
		int x = pBoard.limitM();
		int y = pBoard.limitN();
		for(int i = 0; i < x; ++i) {
			for(int j = 0; j < y; ++j) {
				if(pBoard.get(i, j) == Constants.EMPTY) {
					ret.add(new Point(i, j));
				}
			}
		}
		return ret;
	}
	
	public static char getOther(char pChar) {
		if(pChar == Constants.O) {
			return Constants.X;
		} else if(pChar == Constants.X) {
			return Constants.O;
		} else {
			throw new IllegalArgumentException("pChar was't equal to Constants.X/O");
		}
	}

}
