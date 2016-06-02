package com.engine.gui;

import org.lwjgl.util.vector.Vector2f;

public class GUI {

	protected int textureID;
	protected Vector2f position;
	protected Vector2f scale;

	/**
	 * amount of gui to display
	 * */
	protected Vector2f percent = new Vector2f(1, 1);

	public GUI(int textureID, Vector2f position, Vector2f scale) {
		super();
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
	}

	/**
	 * @return the textureID
	 */
	public int getTextureID() {
		return textureID;
	}

	/**
	 * @param textureID the textureID to set
	 */
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	/**
	 * @return the position
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	/**
	 * @return the scale
	 */
	public Vector2f getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	/**
	 * @return the percent
	 */
	public Vector2f getPercent() {
		return percent;
	}

	/**
	 * @param percent the percent to set
	 */
	public void setPercent(Vector2f percent) {
		this.percent = percent;
	}

}
