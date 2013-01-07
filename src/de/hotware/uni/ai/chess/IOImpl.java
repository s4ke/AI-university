package de.hotware.uni.ai.chess;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

public class IOImpl implements IO {

	protected InputStream mInputStream;
	protected PrintStream mPrintStream;
	protected boolean mIsOwnerWhite;

	public IOImpl(boolean pIsOwnerWhite, PrintStream pPrintStream, InputStream pInputStream) {
		this.mPrintStream = pPrintStream;
		this.mIsOwnerWhite = pIsOwnerWhite;
	}

	@Override
	public Board read(Board pBoard) throws IOException {
		try(BufferedReader buf = new BufferedReader(
				new InputStreamReader(this.mInputStream))) {
			boolean done = false;
			List<Unit> units = this.mIsOwnerWhite ? pBoard.getWhiteUnits() : pBoard.getBlackUnits();
			Unit toMove = null;
			int x = 0;
			int y = 0;
			while(!done) {
				toMove = null;
				String read = Character.toString((char) buf.read());
				//check if the read char is one of the enemies units
				for(Unit unit : units) {
					if(unit.getType().getStringRepresentation().equals(read)) {
						toMove = unit;
					}
				}
				if(toMove != null) {
					char ch = (char) buf.read();
					if(ch >= 'a' && ch <= 'h') {
						x = ch - 'a';
						y = Integer.parseInt(Character.toString((char) buf.read()));
						if(y < 1 || y > Constants.SIZE) {
							x = -1;
							y = -1;
						} else {
							done = true;
						}
					}
				}
			}
			//y - 1 because of zero based internals
			return pBoard.move(toMove, new Point(x, y - 1));
		}
	}

	@Override
	public void write(Unit pUnit, Point2D pPosition) {
		this.mPrintStream.print(pUnit.getType().getStringRepresentation());
		this.mPrintStream.print((char)(pPosition.getX() + 'a'));
		this.mPrintStream.print((int)(pPosition.getY() + 1));
		this.mPrintStream.flush();
	}
	
	public static void main(String[] pArgs) {
		//Testing...
		Unit unit = new UnitImpl(true, Unit.Type.BISHOP, new Point(2,2));
		IO io = new IOImpl(true, System.out, System.in);
		io.write(unit, new Point(2,3));
	}

}
