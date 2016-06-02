package com.engine.toolbox;

import org.lwjgl.input.Keyboard;

import com.engine.render.DisplayManager;

public class PausingHelper {

	private static float secondsSinceLastButton = 0;
	private static final float MIN_TIME_BETWEEN_BUTTONS = 0.25f;

	public static boolean paused = false;

	public static void update() {
		if (shouldPause()) {
			paused = !paused;
		}
	}

	public static boolean shouldPause() {
		secondsSinceLastButton += DisplayManager.getFrameTimeSeconds();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (secondsSinceLastButton > MIN_TIME_BETWEEN_BUTTONS) {
				secondsSinceLastButton = 0;
				return true;
			}
		}
		return false;
	}

}
