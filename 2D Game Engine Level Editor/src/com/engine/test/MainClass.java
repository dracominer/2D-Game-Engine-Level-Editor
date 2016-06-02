package com.engine.test;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.engine.audio.AudioMaster;
import com.engine.gameState.GameStateManager;
import com.engine.render.DisplayManager;
import com.engine.toolbox.CleanupSquad;

public class MainClass {

	// Static

	public static MainClass instance;

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
	}

	private void render() {
		GameStateManager.render();
	}

	private void update() {
		if (Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GameStateManager.update();
	}

}
