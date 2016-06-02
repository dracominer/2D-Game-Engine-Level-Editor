package com.engine.toolbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.engine.render.DisplayManager;

public class InputManager {

	public static boolean renderInventory = false;
	public static boolean paused = false;
	private static final int COOL_DOWN_TIME = 20;
	private static int coolDown = 20;
	private static int centerX = -1;
	private static int centerY = -1;

	public static void update() {
		coolDown--;
		if (centerX < 0) centerX = (int) (DisplayManager.getWidth() / 2f);
		if (centerY < 0) centerY = (int) (DisplayManager.getHeight() / 2f);

		if (coolDown <= 0) {
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				renderInventory = !renderInventory;
				paused = renderInventory;
				end();
				return;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				renderInventory = false;
				paused = !paused;
				end();
				return;
			}
		}
	}

	private static void end() {
		coolDown = COOL_DOWN_TIME;
		Mouse.setCursorPosition(centerX, centerY);
		Mouse.setGrabbed(!paused);
	}

}
