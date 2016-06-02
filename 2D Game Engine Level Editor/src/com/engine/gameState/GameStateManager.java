package com.engine.gameState;

public class GameStateManager {

	public static enum STATE {
		MAIN_MENU(), LEVEL_EDIT();
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
		STATE.MAIN_MENU.setState(new StateMenu("menu"));
		STATE.LEVEL_EDIT.setState(new StateLevelEditing("level edit"));
		currentGameState = STATE.MAIN_MENU;
		currentGameState.getState().onOpened();
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
