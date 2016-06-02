package com.engine.toolbox;

import java.util.ArrayList;
import java.util.List;

public class CleanupSquad {

	private static List<Cleanupable> queue = new ArrayList<Cleanupable>();

	public static void add(Cleanupable thing) {
		queue.add(thing);
	}

	public static void cleanup() {
		for (Cleanupable c : queue) {
			c.cleanup();
		}
	}

}
