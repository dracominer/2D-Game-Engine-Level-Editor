package com.engine.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.engine.render.DisplayManager;

public abstract class GuiInteractable extends GuiUpdatable {

	protected boolean isMouseTouching = false;
	protected boolean wasMouseTouching = false;

	public static final int LEFT_MOUSE_BUTTON = 0;
	public static final int RIGHT_MOUSE_BUTTON = 1;
	public static final int MIDDLE_MOUSE_BUTTON = 2;
	public static final int NO_MOUSE_BUTTON = -1;

	public GuiInteractable(int textureID, Vector2f position, Vector2f scale) {
		super(textureID, position, scale);
	}

	public void update() {
		float mX = (Mouse.getX() / DisplayManager.getWidth()) * 2f - 1f;
		float mY = (Mouse.getY() / DisplayManager.getHeight()) * 2f - 1f;
		wasMouseTouching = isMouseTouching;
		isMouseTouching = checkX(mX) && checkY(mY);
		int mouseButton = -1;
		if (Mouse.isButtonDown(0)) mouseButton = 0;
		if (Mouse.isButtonDown(1)) mouseButton = 1;
		if (Mouse.isButtonDown(2)) mouseButton = 2;

		if (isMouseTouching) {
			if (isMouseTouching == wasMouseTouching) {
				onMouseTouching(mouseButton, new Vector2f(mX, mY));
			} else {
				onMouseEnter(mouseButton, new Vector2f(mX, mY));
			}
		} else {
			if (isMouseTouching == wasMouseTouching) {
				onMouseNotTouching(mouseButton, new Vector2f(mX, mY));
			} else {
				onMouseExit(mouseButton, new Vector2f(mX, mY));
			}
		}

	}

	protected abstract void onMouseTouching(int mouseButton, Vector2f position);

	protected abstract void onMouseEnter(int mouseButton, Vector2f position);

	protected abstract void onMouseNotTouching(int mouseButton, Vector2f position);

	protected abstract void onMouseExit(int mouseButton, Vector2f position);

	public boolean checkX(float mouseX) {
		return mouseX <= position.x + scale.x && mouseX >= position.x - scale.x;
	}

	public boolean checkY(float mouseY) {
		return mouseY <= position.y + scale.y && mouseY >= position.y - scale.y;
	}

}
