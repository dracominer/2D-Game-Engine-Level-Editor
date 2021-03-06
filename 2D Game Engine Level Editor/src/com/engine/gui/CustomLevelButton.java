package com.engine.gui;

import org.lwjgl.util.vector.Vector2f;

import com.engine.gui.window.CustomLevelDialogue;

public class CustomLevelButton extends GuiInteractable {

	public CustomLevelButton(int textureID, Vector2f position, Vector2f scale) {
		super(textureID, position, scale);
	}

	@Override
	protected void onMouseTouching(int mouseButton, Vector2f position) {
		if (mouseButton != -1) {
			new CustomLevelDialogue();
		}
	}

	@Override
	protected void onMouseEnter(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseNotTouching(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseExit(int mouseButton, Vector2f position) {}

}
