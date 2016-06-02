package com.engine.random;

import java.util.Random;

public class Dice {

	private static Random rand = new Random();

	public static void setSeed(long seed) {
		rand.setSeed(seed);
	}

	public static int rollD(int numberOfSides) {
		return rand.nextInt(numberOfSides) + 1;
	}

	public static int rollD20() {
		return rollD(20);
	}

}
