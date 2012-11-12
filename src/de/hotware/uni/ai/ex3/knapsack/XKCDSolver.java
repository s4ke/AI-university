package de.hotware.uni.ai.ex3.knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class XKCDSolver {
	
	private static final int[] PRICES = {
		75, 140, 150, 233, 250, 270, 303, 500, 711, 812, 1106, 1525
	};
	
	private static final int HAS_TO_BE = 1574;
	
	public static void main(String[] pArgs) {
		Random random = new Random();
		int sum = 0;
		int length = PRICES.length;
		List<Integer> prices = new ArrayList<Integer>();
		while(sum != HAS_TO_BE) {
			int add = PRICES[random.nextInt(length)];
			sum += add;
			prices.add(add);
			if(sum > HAS_TO_BE) {
				sum = 0;
				prices = new ArrayList<Integer>();
			}
		}
		System.out.println("Solution: " + prices);
	}

}
