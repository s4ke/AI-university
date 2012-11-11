package de.hotware.uni.ai.ex3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
	
	public static QuadNodeMatrix<Position> parseMap(File pFile) throws FileNotFoundException, IOException {
		try(BufferedReader buf = new BufferedReader(new FileReader(pFile))) {
			int dimensionX = -1;
			int dimensionY = 0;
			
			List<String> lines = new ArrayList<String>();
			{
				String line;
				while((line = buf.readLine()) != null) {
					int length = line.length();
					if(dimensionX != -1 && length != dimensionX) {
						throw new IllegalArgumentException("the length of the lines does not match");
					}
					dimensionX = length;
					++dimensionY;
					lines.add(line);
				}
			}
			int x = 0;
			int y = 0;
			//generics gone rouge -.-
			@SuppressWarnings("unchecked")
			QuadNode<Position>[][] nodes =  new BasicQuadNode[dimensionX][dimensionY];
			for(String line : lines) {
				char[] array = line.toCharArray();
				for(char c : array) {
					Position pos = new Position(x, y);
					switch(c) {
						case '#': {
							pos.mType = Position.Type.SOLID;
							break;
						}
						case ' ': {
							pos.mType = Position.Type.EMPTY;
							break;
						}
						default: {
							throw new IllegalArgumentException("input file has other characters than # and ' '");
						}
					}
					QuadNode<Position> newNode = new BasicQuadNode<Position>(pos);
					if(y > 0 && y < dimensionY) {
						nodes[x][y - 1].setSouth(newNode);
						newNode.setNorth(nodes[x][y - 1]);
					}
					if(x > 0 && x < dimensionX) {
						nodes[x - 1][y].setEast(newNode);
						newNode.setWest(nodes[x - 1][y]);
					}
					nodes[x][y] = newNode;
					++x;
				}
				x = 0;
				++y;
			}
			return DefaultQuadNodeMatrix.create(nodes);
		}
	}

}
