package de.hotware.uni.ai.ex3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
	
	public static <T> QuadNodeMatrix<T> parseMap(File pFile) throws IOException {
		FileReader fr = new FileReader(pFile);
		BufferedReader buf = new BufferedReader(fr);
		int dimensionX = 0;
		int dimensionY = 0;
		int x = 0;
		int y = 0;
		
		List<String> lines = new ArrayList<String>();
		{
			String line;
			while((line = buf.readLine()) != null) {
				if(dimensionX != -1 && line.length() != dimensionX) {
					throw new IllegalArgumentException("the length of the lines does not match");
				}
				++dimensionX;
				++dimensionY;
				lines.add(line);
			}
		}
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
				nodes[x][y] = new BasicQuadNode<Position>(pos);
			}
			++x;
			++y;
		}
	}

}
