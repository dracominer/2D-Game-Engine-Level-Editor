package com.engine.toolbox;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.engine.render.DisplayManager;

public class ClickManager {

	private static Vector2f lastClickPosition = new Vector2f(0, 0);
	private static float timeSinceClick = 100000;

	public static void update() {
		timeSinceClick += DisplayManager.getFrameTimeSeconds();
		if (Mouse.isButtonDown(2)) {
			timeSinceClick = 0;
			float x = ((float) Mouse.getX());
			float y = ((float) Mouse.getY());
			lastClickPosition = new Vector2f(x, y);
		}
	}

	/**
	 * @return the timeSinceClick
	 */
	public static float getTimeSinceClick() {
		return timeSinceClick;
	}

	/**
	 * @param timeSinceClick the timeSinceClick to set
	 */
	public static void setTimeSinceClick(float timeSinceClick) {
		ClickManager.timeSinceClick = timeSinceClick;
	}

	/**
	 * @param lastClickPosition the lastClickPosition to set
	 */
	public static void setLastClickPosition(Vector2f lastClickPosition) {
		ClickManager.lastClickPosition = lastClickPosition;
	}

	/**
	 * @return the lastClickPosition
	 */
	public static Vector2f getLastClickPosition() {
		return lastClickPosition;
	}

}
