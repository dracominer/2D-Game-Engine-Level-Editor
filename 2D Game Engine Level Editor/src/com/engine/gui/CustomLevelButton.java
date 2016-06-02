package com.engine.gui;

import org.lwjgl.util.vector.Vector2f;

public class CustomLevelButton extends GuiInteractable {

	public CustomLevelButton(int textureID, Vector2f position, Vector2f scale) {
		super(textureID, position, scale);
	}

	@Override
	protected void onMouseTouching(int mouseButton, Vector2f position) {
		if (mouseButton != -1) {
			//TODO make the dialogue box give the data over to the level to process.
		}
	}

	@Override
	protected void onMouseEnter(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseNotTouching(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseExit(int mouseButton, Vector2f position) {}

}
