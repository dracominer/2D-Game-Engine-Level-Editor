package com.engine.random;

import java.util.Random;

public class Rand {

	private static Random rand = new Random();

	public static void setSeed(long seed) {
		rand.setSeed(seed);
	}

	/**
	 * @return a value from 0 to 100 
	 * */
	public static int percentI() {
		return rand.nextInt(100);
	}

	public static boolean percentChance(int percent) {
		return percentI() < percent;
	}

	public static boolean percentChance(float percent) {
		return percentF() < percent;
	}

	/**
	 * essentially runs new Random().nextFloat();
	 * */
	public static float percentF() {
		return rand.nextFloat();
	}
}
