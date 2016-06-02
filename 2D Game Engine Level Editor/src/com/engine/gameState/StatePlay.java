package com.engine.gameState;

import com.engine.level.LevelManager;
import com.engine.toolbox.PausingHelper;

public class StatePlay extends GameState {

	public StatePlay(String name) {
		super(name);
		LevelManager.init();
	}

	@Override
	public void update() {
		PausingHelper.update();
		if (!PausingHelper.paused){
			LevelManager.update();
		}else{
			LevelManager.pausedUpdate();
		}
	}

	@Override
	public void render() {
		LevelManager.render();
	}

}
