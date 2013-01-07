package de.hotware.uni.ai.chess;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hotware.uni.ai.chess.AI.Move;
import de.hotware.uni.ai.chess.Board.EndType;


public class Main {
	
	public static void main(String[] pArgs) throws IOException {
		if(pArgs.length != 1) {
			System.out.println("first argument is 1 for white, 2 for black");
			System.exit(-1);
		}
		boolean white = pArgs[0].equals("1");
		AI ai = new ChessMiniMaxAI(white);
		IO io = new IOImpl(white, System.out, System.in);
		List<Unit> whiteList = new ArrayList<>();
		Unit unit = new UnitImpl(true, Unit.Type.KING, new Point(2, 0));
		whiteList.add(unit);
		unit = new UnitImpl(true, Unit.Type.KNIGHT, new Point(4, 0));
		whiteList.add(unit);
		unit = new UnitImpl(true, Unit.Type.BISHOP, new Point(6, 0));
		whiteList.add(unit);
		List<Unit> blackList = new ArrayList<>();
		unit = new UnitImpl(false, Unit.Type.KING, new Point(4, 7));
		blackList.add(unit);
		Board board = new BoardImpl(white, whiteList, blackList);
		int turn = white ? 1 : 2;
		while(board.end() == EndType.RUNNING) {
			if(turn % 2 == 1) {
				Move move = ai.calculateNextMove(board);
				board = board.move(move.mUnit, move.mPoint);
				io.write(move.mUnit, move.mPoint);
			} else {
				board = io.read(board);
			}
		}
		System.out.println(board.end());
	}

}
