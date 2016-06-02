package com.engine.gui;

import org.lwjgl.util.vector.Vector2f;

import com.engine.audio.AudioMaster;
import com.engine.audio.Sound;
import com.engine.audio.Source;
import com.engine.gameState.GameStateManager;
import com.engine.gameState.GameStateManager.STATE;

public class GuiStateChangeButton extends GuiInteractable {

	private Sound sound;

	protected String newState = STATE.MAIN_MENU.toString();

	public GuiStateChangeButton(int textureID, Vector2f position, Vector2f scale) {
		super(textureID, position, scale);
		sound = new Sound(new Source().setGain(0.9f).setPitch(0.75f), AudioMaster.loadSound("audio/bg.wav"));
	}

	@Override
	protected void onMouseTouching(int mouseButton, Vector2f position) {
		if (mouseButton != -1) {
			GameStateManager.setState(STATE.valueOf(newState));
			sound.stop();
		}
	}

	@Override
	protected void onMouseEnter(int mouseButton, Vector2f position) {
		sound.play();
	}

	@Override
	protected void onMouseNotTouching(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseExit(int mouseButton, Vector2f position) {
		sound.pause();
	}

	/**
	 * @return the newState
	 */
	public String getNewState() {
		return newState;
	}

	/**
	 * @param newState the newState to set
	 */
	public void setNewState(String newState) {
		this.newState = newState;
	}

}
