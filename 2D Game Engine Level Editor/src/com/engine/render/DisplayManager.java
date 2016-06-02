package com.engine.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;

public class DisplayManager {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static final int FPS_CAP = 60;
	private static final String TITLE = "The Illustrious Game Engine of 2d Dungeon Crawling Madness!";

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay() {
		ContextAttribs attrib = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat().withDepthBits(24), attrib);
			Display.setTitle(TITLE);
			Display.setResizable(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static float getFrameTimeMilliseconds() {
		return delta * 1000f;
	}

	public static void destroyDisplay() {
		Display.destroy();
	}

	public static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static float getWidth() {
		return Display.getWidth();
	}

	public static float getHeight() {
		return Display.getHeight();
	}

	public static float getCenterX() {
		return getWidth() / 2f;
	}

	public static float getCenterY() {
		return getHeight() / 2f;
	}

	public static Vector2f getCenter() {
		return new Vector2f(getCenterX(), getCenterY());
	}

	public static int getWidthi() {
		return (int) getWidth();
	}

	public static int getHeighti() {
		return (int) getHeight();
	}

	public static String getTitle() {
		return TITLE;
	}

}
