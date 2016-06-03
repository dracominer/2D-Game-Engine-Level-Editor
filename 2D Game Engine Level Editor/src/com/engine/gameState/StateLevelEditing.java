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

	public void onClosed() {}

}
