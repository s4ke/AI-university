package de.hotware.uni.ai.pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import de.hotware.uni.ai.pacman.Node.PositionType;
import de.hotware.uni.ai.pacman.Pacman.Ghost;
import de.hotware.uni.ai.pacman.Pacman.PositionReturn;

/**
 * @author Martin Braun (1249080)
 */
public class PacmanIntelligenceImpl implements PacmanIntelligence {
	
	private static final boolean DEBUG = false;
	
	protected Pacman mPacman;
	protected List<Node> mCurrentPath;
	protected Gold mCurrentGold;
	
	public PacmanIntelligenceImpl() {
		this.mPacman = null;
		this.mCurrentPath = null;
	}

	@Override
	public void init(Pacman pPacman) {
		this.mPacman = pPacman;
	}

	@SuppressWarnings("unused")
	@Override
	public Direction getNextDirection() {
		Node ownPosition = null;
		int[][] map = this.mPacman.getMap();
		for(int i = 0; i < map.length; ++i) {
			for(int j = 0; j < map[0].length; ++j) {
				if(map[i][j] == 1) {
					ownPosition = new Node(i, j);
					break;
				}
			}
		}
		Queue<Gold> queue = this.calculateNextPath(ownPosition, map);
		if(this.mCurrentPath == null || this.mCurrentPath.size() <= 1 ||
				!isAdjacent(ownPosition, this.mCurrentPath.get(0)) || 
				this.mCurrentGold == null || !this.mCurrentGold.mReachable) {
			if(DEBUG && this.mCurrentGold != null) {
				System.out.println(this.mCurrentGold.mReachable);
			}
			if(DEBUG && this.mCurrentPath != null) {
				System.out.println("adjacent: " + !isAdjacent(ownPosition, this.mCurrentPath.get(0)));
			}
			this.mCurrentGold = queue.poll();
			this.mCurrentPath = this.mCurrentGold.mPath;
		}
		PositionReturn ghosts = this.mPacman.getGhostPositions();
		for(Entry<Ghost, Node> entry : ghosts.mMap.entrySet()) {
			List<Node> list = AStar.getShortestPath(ownPosition, entry.getValue(), map);
			int size = list.size();
			if(size <= 8 && Math.random() <= 0.2d || size <= 7 && Math.random() <= 0.3d
					|| size <= 6 && Math.random() <= 0.4d || size <= 5 && Math.random() <= 0.5d ||
					size <= 4 && Math.random() <= 0.6d || size <= 3) {
				if(DEBUG) {
					System.out.println("panic!");
				}
				//Panic!!!
				this.mCurrentGold = null;
				this.mCurrentPath = null;
				return this.fleeDirection(ownPosition, entry.getValue(), map);
			}
		}
		Direction ret = getNextDirection(this.mCurrentPath);
		if(DEBUG) {
			System.out.println(ret);
		}
		return ret;
	}
	
	private static boolean isAdjacent(Node pPosition, Node pCheck) {
		return AStar.calculateDistance(pPosition, pCheck) == 1;
	}
	
	private Direction fleeDirection(Node pOwnPosition, Node pGhostPosition, int[][] pMap) {
		System.out.println(pGhostPosition);
		List<Node> ret = new ArrayList<>();
		ret.add(pOwnPosition);
		int length = -1;
		Node node = null;
		for(int i = 0; i < pMap.length; ++i) {
			for(int j = 0; j < pMap[0].length; ++j) {
				PositionType type = Node.getPositionType(i, j, pMap);
				Node tmpNode = new Node(i, j);
				if(!type.isWall() && AStar.calculateDistance(pOwnPosition, tmpNode) == 1) {
					int tmp = -1;
					if((tmp = AStar.getShortestPath(pGhostPosition, tmpNode, pMap).size()) > length) {
						length = tmp;
						node = new Node(i, j);
					}
				}
			}
		}
		return getDirection(pOwnPosition, node);
	}
	
	private Queue<Gold> calculateNextPath(Node pOwnPosition, int[][] pMap) {
		int[][] gold = this.mPacman.getGoldLifetime();
		Queue<Gold> queue = new PriorityQueue<Gold>();
		for(int i = 0; i < gold.length; ++i) {
			for(int j = 0; j < gold[0].length; ++j) {
				int lifetime = gold[i][j];
				if(lifetime > 0) {
					List<Node> list = AStar.getShortestPath(pOwnPosition, new Node(i, j), pMap);
					if(lifetime >= list.size() - 1) {
						//only reachable golds get added to the queue
						queue.add(new Gold(i, j, lifetime, list, true));
						if(DEBUG) {
							System.out.println("found reachable: " + list);
						}
					} else {
						//it's not reachable but we may walk there
						queue.add(new Gold(i, j, Integer.MAX_VALUE, list, false));
						if(DEBUG) {
							System.out.println("found not reachable: " + list);
						}
					}
				}
			}
		}
		return queue;
	}

	private static Direction getNextDirection(List<Node> pPath) {
		if(pPath != null && pPath.size() > 0) {
			Node start = pPath.remove(0);
			if(pPath.size() > 0) {
				Node next = pPath.get(0);
				return getDirection(start, next);
			}
		}
		return Direction.NONE;
	}
	
	private static Direction getDirection(Node pStart, Node pNext) {
		if(!pStart.equals(pNext)) {
			if(pNext.mX > pStart.mX) {
				return Direction.SOUTH;
			} else if(pNext.mX < pStart.mX) {
				return Direction.NORTH;
			} else if(pNext.mY > pStart.mY) {
				return Direction.EAST;
			} else if(pNext.mY < pStart.mY) {
				return Direction.WEST;
			}
		}
		return Direction.NONE;
	}
	
	protected static class Gold implements Comparable<Gold> {
		
		protected int mX;
		protected int mY;
		protected int mLifetime;
		protected List<Node> mPath;
		protected boolean mReachable;
		
		public Gold(int pX, int pY, int pLifetime, List<Node> pPath, boolean pReachable) {
			this.mX = pX;
			this.mY = pY;
			this.mLifetime = pLifetime;
			this.mPath = pPath;
			this.mReachable = pReachable;
		}

		@Override
		public int compareTo(Gold pOther) {
			return this.mLifetime - pOther.mLifetime + 
				this.mPath.size() - pOther.mPath.size();
		}
		
	}

}
