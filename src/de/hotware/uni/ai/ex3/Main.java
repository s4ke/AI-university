package de.hotware.uni.ai.ex3;

import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main(String[] pArgs) throws IOException {
		System.out.println(MapParser.parseMap(new File("test.maze")));
	}

}
