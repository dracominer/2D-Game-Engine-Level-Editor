package com.engine.test;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.engine.audio.AudioMaster;
import com.engine.gameState.GameStateManager;
import com.engine.level.LevelManager;
import com.engine.render.DisplayManager;
import com.engine.toolbox.CleanupSquad;

public class MainClass {

	// Static

	public static MainClass instance;

	private static List<Float> frameTimes = new ArrayList<Float>();

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		instance = new MainClass();
		instance.start();
		CleanupSquad.cleanup();
		DisplayManager.destroyDisplay();
		AudioMaster.cleanup();
	}

	// Localized

	public MainClass() {
		System.out.println("Starting game...");
		AudioMaster.init();
		AudioMaster.setListenerData();
		GameStateManager.init();
	}

	public void start() {
		while (!Display.isCloseRequested()) {
			update();
			render();
			DisplayManager.updateDisplay();
		}
		float total = 0;
		for (float f : frameTimes) {
			total += f;
		}
		LevelManager.save();
		float avg = total / frameTimes.size();
		System.out.println();
		System.out.println("Playtime data:");
		System.out.println();
		System.out.println("Average frame time (in miliseconds) : " + avg);
		System.out.println("Average frame time (in seconds) : " + (avg / 1000f));
		System.out.println("Average frames per second : " + (1f / (avg / 1000f)));
		System.out.println("Total time played : " + total + "(milliseconds)");
		System.out.println("Total time played : " + (total / 1000f) + "(seconds)");
	}

	private void render() {
		GameStateManager.render();
	}

	private void update() {
		if (Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		frameTimes.add(DisplayManager.getFrameTimeMilliseconds());
		GameStateManager.update();
	}

}
