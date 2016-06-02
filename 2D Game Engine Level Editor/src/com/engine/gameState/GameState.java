package com.engine.gameState;

public abstract class GameState {

	protected final String name;

	public GameState(String name) {
		this.name = name;
		System.out.println("Initializing game state: " + name);
	}

	public abstract void update();

	public abstract void render();

	public void onOpened() {}

	public void onClosed() {}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
