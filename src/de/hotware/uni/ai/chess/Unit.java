package de.hotware.uni.ai.chess;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public interface Unit {
	
	public Point2D getPosition();
	
	public void setPosition(Point2D pPosition);
	
	public Type getType();
	
	public boolean isOwnerWhite();
	
	public Unit copy();
	
	public enum Type {
		KING(1000) {

			@Override
			public List<Point2D> getReachedPositions(Point2D pPosition) {
				List<Point2D> ret = new ArrayList<>();
				List<Point2D> tmp = new ArrayList<>();
				int x = (int) pPosition.getX();
				int y = (int) pPosition.getY();
				Point pt = new Point(x + 1, y);
				tmp.add(pt);
				pt = new Point(x - 1, y);
				tmp.add(pt);
				pt = new Point(x + 1, y + 1);
				tmp.add(pt);
				pt = new Point(x, y + 1);
				tmp.add(pt);
				pt = new Point(x - 1, y + 1);
				tmp.add(pt);
				pt = new Point(x + 1, y - 1);
				tmp.add(pt);
				pt = new Point(x, y - 1);
				tmp.add(pt);
				pt = new Point(x - 1, y - 1);
				tmp.add(pt);
				for(Point2D point : tmp) {
					if(inBounds(point)) {
						ret.add(point);
					}
				}
				return ret;
			}
			
		},
		BISHOP(3.5) {

			@Override
			public List<Point2D> getReachedPositions(Point2D pPosition) {
				List<Point2D> ret = new ArrayList<>();
				int x = (int) pPosition.getX();
				int y = (int) pPosition.getY();
				for(int i = 0; i < Constants.SIZE; ++i) {
					Point pt = new Point(x + i, y + i);
					if(inBounds(pt)) {
						ret.add(pt);
					}
					pt = new Point(x - i, y - i);
					if(inBounds(pt)) {
						ret.add(pt);
					}
					pt = new Point(x - i, y + i);
					if(inBounds(pt)) {
						ret.add(pt);
					}
					pt = new Point(x + i, y - i);
					if(inBounds(pt)) {
						ret.add(pt);
					}
				}
				return ret;
			}
			
		},
		KNIGHT(3.2) {

			@Override
			public List<Point2D> getReachedPositions(Point2D pPosition) {
				List<Point2D> ret = new ArrayList<>();
				List<Point2D> tmp = new ArrayList<>();
				int x = (int) pPosition.getX();
				int y = (int) pPosition.getY();
				//top left ones:
				Point pt = new Point(x - 1, y + 2);
				tmp.add(pt);
				pt = new Point(x - 2, y + 1);
				tmp.add(pt);
				//top right ones:
				pt = new Point(x + 1, y + 2);
				tmp.add(pt);
				pt = new Point(x + 2, y + 1);
				tmp.add(pt);
				//bottom left ones:
				pt = new Point(x - 1, y - 2);
				tmp.add(pt);
				pt = new Point(x - 2, y - 1);
				tmp.add(pt);
				//bottom right ones:
				pt = new Point(x + 1, y - 2);
				tmp.add(pt);
				pt = new Point(x + 2, y - 1);
				tmp.add(pt);
				for(Point2D point : tmp) {
					if(inBounds(point)) {
						ret.add(point);
					}
				}
				return ret;
			}
			
			@Override
			public String getStringRepresentation() {
				return "N";
			}
			
		};
		
		protected static boolean inBounds(Point2D pPoint) {
			return pPoint.getX() < Constants.SIZE && pPoint.getY() < Constants.SIZE
					&& pPoint.getX() >= 0 && pPoint.getY() >= 0;
		}

		public abstract List<Point2D> getReachedPositions(Point2D pPosition);
		
		protected double mWeight;
		
		private Type(double pWeight) {
			this.mWeight = pWeight;
		}
		
		public double getWeight() {
			return this.mWeight;
		}
		
		public String getStringRepresentation() {
			return this.toString().substring(0, 1);
		}
		
	}

}
