package com.engine.gameState;

import com.engine.level.LevelManager;

public class GameStateManager {

	public static enum STATE {
		PLAY(), MAIN_MENU(), GAME_OVER();
		private GameState state;

		private STATE(GameState state) {
			this.state = state;
		}

		private STATE() {
			this.state = null;
		}

		public GameState getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(GameState state) {
			this.state = state;
		}

	}

	private static STATE currentGameState = null;

	public static void init() {
		loadGameStates();
	}

	private static void loadGameStates() {
		System.out.println("loading all states");
		STATE.PLAY.setState(new StatePlay("play"));
		STATE.MAIN_MENU.setState(new StateMenu("menu"));
		STATE.GAME_OVER.setState(new StateGameOver("game over"));
		currentGameState = STATE.MAIN_MENU;
		currentGameState.getState().onOpened();
		LevelManager.load();
		System.out.println("loaded all states");
	}

	public static void update() {
		currentGameState.getState().update();
	}

	public static void render() {
		currentGameState.getState().render();
	}

	public static void setState(STATE newState) {
		System.out.println("Closing game state : " + currentGameState.getState().getName());
		currentGameState.getState().onClosed();
		currentGameState = newState;
		currentGameState.getState().onOpened();
		System.out.println("Opening game state : " + currentGameState.getState().getName());
	}

}
