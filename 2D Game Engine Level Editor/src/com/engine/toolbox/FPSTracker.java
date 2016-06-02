package com.engine.toolbox;

import com.engine.render.DisplayManager;

public class FPSTracker {

	private static float currentTime = 0;
	private static int frames = 0;
	private static int fps = 0;

	public static void update() {
		currentTime += DisplayManager.getFrameTimeSeconds();
		frames++;
		if (currentTime >= 1) {
			currentTime %= 1;
			fps = frames;
			frames = 0;
		}
	}

	public static int getFPS() {
		return fps;
	}

}
