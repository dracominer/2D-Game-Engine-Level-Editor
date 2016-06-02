package com.engine.gameState;

public class StateLevelEditing extends GameState {

	public StateLevelEditing(String name) {
		super(name);
	}

	@Override
	public void update() {
		GameStateManager.currentLevel.update();
	}

	@Override
	public void render() {
		GameStateManager.currentLevel.render();
	}

	public void onOpened() {
		GameStateManager.currentLevel.init();
	}

	public void onClosed() {}

}
