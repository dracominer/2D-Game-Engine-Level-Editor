package com.engine.gameState;

import com.engine.level.Level;

public class StateLevelEditing extends GameState {

	private Level level;

	public StateLevelEditing(String name) {
		super(name);
		level = new Level(this);
	}

	@Override
	public void update() {
		level.update();
	}

	@Override
	public void render() {
		level.render();
	}

}
